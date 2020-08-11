package com.pocketchat.services.user_authentication;

import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.db.repo_services.user_authentication.UserAuthenticationRepoService;
import com.pocketchat.db.repo_services.user_role.UserRoleRepoService;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.PreVerifyMobileNumberOTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.VerifyEmailAddressResponse;
import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import com.pocketchat.models.otp.VerifyOTPNumberResponse;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.server.configurations.security.service.MyUserDetailsService;
import com.pocketchat.server.exceptions.otp.MaximumOTPVerificationAttemptReachedException;
import com.pocketchat.server.exceptions.otp.OTPNotFoundException;
import com.pocketchat.server.exceptions.otp.WrongOTPException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.server.exceptions.user_role.UserRoleNotFoundException;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.services.otp.OTPService;
import com.pocketchat.services.sms.SMSService;
import com.pocketchat.services.user.UserService;
import com.pocketchat.utils.country_code.CountryCodeUtil;
import com.pocketchat.utils.email.EmailUtil;
import com.pocketchat.utils.jwt.JwtUtil;
import com.pocketchat.utils.password.PasswordUtil;
import com.pocketchat.utils.phone.PhoneUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserAuthenticationRepoService userAuthenticationRepoService;

    private final UserRoleRepoService userRoleRepoService;

    private final UserService userService;

    private final SMSService smsService;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;

    private final OTPService otpService;

    private final JwtUtil jwtUtil;

    private final PhoneUtil phoneUtil;

    private final EmailUtil emailUtil;

    private final CountryCodeUtil countryCodeUtil;

    private final PasswordUtil passwordUtil;


    @Value("${server.otp.length}")
    private int otpLength;

    @Value("${server.otp.maximumAliveMinutes}")
    private int otpAliveMinutes;

    @Autowired
    UserAuthenticationServiceImpl(
            UserAuthenticationRepoService userAuthenticationRepoService,
            UserRoleRepoService userRoleRepoService,
            @Lazy UserService userService,
            SMSService smsService,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            OTPService otpService,
            JwtUtil jwtUtil,
            PhoneUtil phoneUtil,
            EmailUtil emailUtil,
            CountryCodeUtil countryCodeUtil,
            PasswordUtil passwordUtil) {
        this.userAuthenticationRepoService = userAuthenticationRepoService;
        this.userRoleRepoService = userRoleRepoService;
        this.userService = userService;
        this.smsService = smsService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
        this.phoneUtil = phoneUtil;
        this.emailUtil = emailUtil;
        this.countryCodeUtil = countryCodeUtil;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public PreVerifyMobileNumberOTPResponse registerMobileNumber(RegisterUsingMobileNumberRequest registerUsingMobileNumberRequest) {
        User user = initializeUser(registerUsingMobileNumberRequest.getMobileNo(), registerUsingMobileNumberRequest.getCountryCode());

        OTP otp = generateOTP(user.getId());

        String messageContent = generateOTPMessageContent(otp);

        if (StringUtils.hasText(user.getMobileNo())) {
            smsService.sendSMS(SendSMSRequest.builder().mobileNumber(user.getMobileNo()).message(messageContent).build());
        }

        return PreVerifyMobileNumberOTPResponse.builder()
                .tokenExpiryTime(otp.getOtpExpirationDateTime())
                .maskedMobileNumber(phoneUtil.maskPhoneNumber(user.getMobileNo()))
                .secureKeyword(otp.getKeyword())
                .build();
    }


    @Override
    public PreVerifyMobileNumberOTPResponse loginMobileNumber(PreVerifyMobileNumberOTPRequest preVerifyMobileNumberOTPRequest) {

        User user = userService.getUserByMobileNo(preVerifyMobileNumberOTPRequest.getMobileNumber());

        OTP otp = generateOTP(user.getId());

        String messageTitle = "PocketChat OTP Verification";

        String messageContent = generateOTPMessageContent(otp);

        sendSMSAndEmail(messageTitle, messageContent, user.getMobileNo(), user.getEmailAddress());

        return PreVerifyMobileNumberOTPResponse.builder()
                .tokenExpiryTime(otp.getOtpExpirationDateTime())
                .maskedEmailAddress(emailUtil.maskEmail(user.getEmailAddress()))
                .maskedMobileNumber(phoneUtil.maskPhoneNumber(user.getMobileNo()))
                .secureKeyword(otp.getKeyword())
                .build();
    }

    // TODO: Encrypt secureKeyword and OTP number from frontend
    @Override
    public UserAuthenticationResponse registerMobileNumberOTPVerification(VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        User user = userService.getUserByMobileNo(verifyMobileNumberOTPRequest.getMobileNo());

        checkOTP(user.getId(), verifyMobileNumberOTPRequest.getMobileNo(), verifyMobileNumberOTPRequest.getOtpNumber(), verifyMobileNumberOTPRequest.getSecureKeyword());

        String securePassword = passwordUtil.generateRandomSecurePassword();

        UserAuthentication userAuthentication = registerUserAuthentication("ROLE_USER", user.getId(), verifyMobileNumberOTPRequest.getMobileNo(), securePassword);

        return allowAuthentication(userAuthentication.getUsername());
    }

    // TODO: Encrypt secureKeyword and OTP number from frontend
    @Override
    public UserAuthenticationResponse loginMobileNumberOTPVerification(VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        User user = userService.getUserByMobileNo(verifyMobileNumberOTPRequest.getMobileNo());

        checkOTP(user.getId(), verifyMobileNumberOTPRequest.getMobileNo(), verifyMobileNumberOTPRequest.getOtpNumber(), verifyMobileNumberOTPRequest.getSecureKeyword());

        Optional<UserAuthentication> userAuthenticationOptional = userAuthenticationRepoService.findByUserId(user.getId());

        if (userAuthenticationOptional.isEmpty()) {
            throw new UsernameNotFoundException("Unable to find the User's Authentication using userId: " + user.getId());
        }

        return allowAuthentication(userAuthenticationOptional.get().getUsername());
    }

    // NOT RELATED TO POCKETCHAT
    // This is used for user's that has mobile number ONLY, when secure authentication action is needed, they'll have to trigger this API.
    // 1. Find User in DB.
    // 2. Generate OTP number.
    // 3. Prepare message title and content.
    // 4. Send SMS First.
    // 5. Send Email.
    @Override
    public OTPResponse requestToAuthenticateWithMobileNo(MobileNoUserAuthenticationRequest mobileNoUserAuthenticationRequest) {
        User user = userService.getUserByMobileNo(mobileNoUserAuthenticationRequest.getMobileNo());

        OTP otp = generateOTP(user.getId());

        String messageTitle = "PocketChat Verification Code: " + otp.getOtp();

        String messageContent = generateOTPMessageContent(otp);

        sendSMSAndEmail(messageTitle, messageContent, user.getMobileNo(), user.getEmailAddress());

        return otpResponseMapper(otp);
    }

    // NOT RELATED TO POCKETCHAT
    // This is used for user's that has email address ONLY, when secure authentication action is needed, they'll have to trigger this API.
    // 1. Find User in DB.
    // 2. Generate OTP number.
    // 3. Prepare message title and content.
    // 4. Send Email First.
    // 5. Send SMS.
    @Override
    public OTPResponse requestToAuthenticateWithEmailAddress(EmailAddressUserAuthenticationRequest mobileNoAuthenticationRequest) {
        User user = userService.getUserByEmailAddress(mobileNoAuthenticationRequest.getEmailAddress());

        OTP otp = generateOTP(user.getId());

        String messageTitle = "PocketChat Verification Code: " + otp.getOtp();

        String messageContent = generateOTPMessageContent(otp);

        sendSMSAndEmail(messageTitle, messageContent, user.getMobileNo(), user.getEmailAddress());

        return otpResponseMapper(otp);
    }

    // For Testing only
    // Register
    @Deprecated
    @Override
    @Transactional
    public UserAuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        // TODO: Decrypt the password from frontend

        passwordUtil.validatePasswordStrength(usernamePasswordUserAuthenticationRequest.getPassword(), usernamePasswordUserAuthenticationRequest.getUsername());

        String encodedPassword = passwordUtil.encodePassword(usernamePasswordUserAuthenticationRequest.getPassword());

        // User ID is null to indicate this user is not User from Mobile app as it can't be registered without mobile number
        registerUserAuthentication("ROLE_USER", null, usernamePasswordUserAuthenticationRequest.getUsername(), encodedPassword);

        return allowAuthentication(usernamePasswordUserAuthenticationRequest.getUsername());
    }

    // For testing only
    // Login
    @Deprecated
    @Override
    public UserAuthenticationResponse authenticateUsingUsernamePassword(UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePasswordUserAuthenticationRequest.getUsername(), usernamePasswordUserAuthenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        return allowAuthentication(usernamePasswordUserAuthenticationRequest.getUsername());
    }

    @Override
    public VerifyEmailAddressResponse requestVerifyEmailAddress(VerifyEmailAddressRequest verifyEmailADdressRequest) {
        // TODO: Generate a token that represents the user and concatenate it with the web address of the email verification API
        // TODO: Create EmailVerificationToken DB, RepoService with CRD
        // TODO: Create Email Controller with an API with @PathVariable of the token to GET.
        // TODO: When Should open a page from here in the backend, respond "Email Address Verified Successful" HTML page.

        sendSMSAndEmail("", "", null, verifyEmailADdressRequest.getEmailAddress());

        return null;
    }

    @Override
    public UserAuthentication findByUsername(String username) {
        Optional<UserAuthentication> authenticationOptional = userAuthenticationRepoService.findFirstByUsername(username);

        if (authenticationOptional.isEmpty()) {
            throw new UsernameNotFoundException("Unable to find the User's Authentication using username: " + username);
        }

        return authenticationOptional.get();
    }

    private User initializeUser(String mobileNo, String countryCode) {
        try {
            return userService.getUserByMobileNo(mobileNo);
        } catch (UserNotFoundException userNotFoundException) {
            Locale countryCodeLocale = countryCodeUtil.getLocaleByISOCountryCode(countryCode);

            return userService.addUser(CreateUserRequest.builder()
                    .countryCode(countryCodeLocale.getCountry())
                    .displayName(mobileNo)
                    .realName(mobileNo)
                    .mobileNo(mobileNo)
                    .build());
        }
    }

    private UserAuthentication registerUserAuthentication(String userRole, String userId, String username, String password) {
        return userAuthenticationRepoService.save(UserAuthentication.builder()
                .username(username)
                .password(password)
                .userRoles(Collections.singletonList(assignUserRole(userRole)))
                .userId(userId)
                .build());
    }

    // Load User in Spring Security and generate JWT
    private UserAuthenticationResponse allowAuthentication(String username) {
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

        final String jwt = jwtUtil.generateToken(userDetails);

        final Date otpExpirationTime = jwtUtil.extractExpiration(jwt);

        return UserAuthenticationResponse.builder()
                .jwt(jwt)
                .username(username) // Mobile number user's username will be mobile number with country code
                .otpExpirationTime(otpExpirationTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }

    private UserRole assignUserRole(String userRole) {
        Optional<UserRole> userRoleOptional = userRoleRepoService.findByName(userRole);
        if (userRoleOptional.isEmpty()) {
            throw new UserRoleNotFoundException("Unable to find such user role during registration" +
                    " of the new User using UsernamePasswordAuthenticationRequest. User Role name: " + userRole);
        }

        return userRoleOptional.get();
    }

    private void sendSMSAndEmail(String messageTitle, String messageContent, String mobileNo, String emailAddress) {
        if (StringUtils.hasText(mobileNo)) {
            smsService.sendSMS(SendSMSRequest.builder().mobileNumber(mobileNo).message(messageContent).build());
        }

        if (StringUtils.hasText(emailAddress)) {
            List<String> receiverEmailAddresses = new ArrayList<>();
            receiverEmailAddresses.add(emailAddress);

            emailService.sendEmail(SendEmailRequest.builder()
                    .receiverList(receiverEmailAddresses)
                    .emailSubject(messageTitle)
                    .emailContent(messageContent)
                    .build());
        }
    }

    private OTP generateOTP(String userId) {
        OTP otp = otpService.generateOtpNumber(
                GenerateOTPRequest.builder()
                        .userId(userId)
                        .otpLength(otpLength)
                        .otpAliveMinutes(otpAliveMinutes)
                        .build()
        );
        otpService.saveOTPNumber(otp);

        return otp;
    }

    private String generateOTPMessageContent(OTP otp) {
        return "Your verification number is: " + otp.getOtp() + " that is valid for "
                + otpAliveMinutes + " minutes. It will expire in " + otp.getOtpExpirationDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + ". The secure keyword is " + otp.getKeyword()
                + ". Do not share this OTP to anybody.";
    }

    private void checkOTP(String userId, String mobileNo, String otpNumber, String secureKeyword) {
        VerifyOTPNumberResponse verifyOTPNumberResponse = otpService.verifyOTPNumber(userId, otpNumber, secureKeyword);

        if (!verifyOTPNumberResponse.isCorrect() && verifyOTPNumberResponse.isHasError()) {
            // Incorrect, has error (unable to find that OTP)
            throw new OTPNotFoundException("Unable to find that OTP using this user ID: " + userId + ".High possibility user didn't request for the OTP number.");
        } else if (!verifyOTPNumberResponse.isCorrect() && !verifyOTPNumberResponse.isHasError()) {
            // Incorrect, no error (Wrong OTP/Secure keyword)
            throw new WrongOTPException("Wrong OTP/Secure Keyword. Please try again. Mobile Number: " + mobileNo);
        } else if (!verifyOTPNumberResponse.isCorrect() && !verifyOTPNumberResponse.isHasError() && verifyOTPNumberResponse.getLimitRemaining() == -1) {
            // Reached maximum OTP verify attempt
            throw new MaximumOTPVerificationAttemptReachedException("Maximum attempt OTP verification reached. Please request new OTP again. Mobile number: " + mobileNo);
        }
    }

    @Override
    public OTPResponse otpResponseMapper(OTP otp) {
        return OTPResponse.builder().otpExpirationDateTime(otp.getOtpExpirationDateTime()).build();
    }
}
