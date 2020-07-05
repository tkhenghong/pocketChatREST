package com.pocketchat.services.user_authentication;

import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.db.repo_services.user.UserRepoService;
import com.pocketchat.db.repo_services.user_authentication.UserAuthenticationRepoService;
import com.pocketchat.db.repo_services.user_role.UserRoleRepoService;
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
import com.pocketchat.utils.email.EmailUtil;
import com.pocketchat.utils.jwt.JwtUtil;
import com.pocketchat.utils.phone.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserAuthenticationRepoService userAuthenticationRepoService;

    private final UserRoleRepoService userRoleRepoService;

    private final UserRepoService userRepoService;

    private final SMSService smsService;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;

    private final OTPService otpService;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final PhoneUtil phoneUtil;

    private final EmailUtil emailUtil;

    @Value("${server.otp.length}")
    private int otpLength;

    @Value("${server.otp.alive.minutes}")
    private int otpAliveMinutes;

    @Autowired
    UserAuthenticationServiceImpl(
            UserAuthenticationRepoService userAuthenticationRepoService,
            UserRoleRepoService userRoleRepoService,
            UserRepoService userRepoService,
            SMSService smsService,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            OTPService otpService,
            JwtUtil jwtUtil,
            PhoneUtil phoneUtil,
            EmailUtil emailUtil,
            PasswordEncoder passwordEncoder) {
        this.userAuthenticationRepoService = userAuthenticationRepoService;
        this.userRoleRepoService = userRoleRepoService;
        this.userRepoService = userRepoService;
        this.smsService = smsService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.otpService = otpService;
        this.jwtUtil = jwtUtil;
        this.phoneUtil = phoneUtil;
        this.emailUtil = emailUtil;
        this.passwordEncoder = passwordEncoder;
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
        Optional<User> user = userRepoService.findByMobileNo(mobileNoUserAuthenticationRequest.getMobileNo());

        if (user.isEmpty()) {
            throw new UserNotFoundException("User is not found by using mobile number: " + mobileNoUserAuthenticationRequest.getMobileNo());
        }

        OTP otp = otpService.generateOtpNumber(
                GenerateOTPRequest.builder()
                        .userId(user.get().getId())
                        .otpLength(otpLength)
                        .otpAliveMinutes(otpAliveMinutes)
                        .build()
        );
        otpService.saveOTPNumber(otp);

        String messageTitle = "PocketChat Verification Code: " + otp.getOtp();

        String messageContent = "Your verification number is: " + otp.getOtp() + " that is valid for "
                + otpAliveMinutes + " minutes. It will expire in " + otp.getOtpExpirationDateTime()
                + ". Do not share this OTP to anybody.";

        if (StringUtils.hasText(user.get().getMobileNo())) {
            smsService.sendSMS(SendSMSRequest.builder().mobileNumber(user.get().getMobileNo()).message(messageContent).build());
        }

        List<String> receiverEmailAddresses = new ArrayList<>();
        receiverEmailAddresses.add(user.get().getEmailAddress());

        if (StringUtils.hasText(user.get().getEmailAddress())) {
            emailService.sendEmail(SendEmailRequest.builder()
                    .receiverList(receiverEmailAddresses)
                    .emailSubject(messageTitle)
                    .emailContent(messageContent)
                    .build());
        }

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
        Optional<User> user = userRepoService.findByEmailAddress(mobileNoAuthenticationRequest.getEmailAddress());

        if (user.isEmpty()) {
            throw new UserNotFoundException("User is not found by using Email address: " + mobileNoAuthenticationRequest.getEmailAddress());
        }

        OTP otp = otpService.generateOtpNumber(
                GenerateOTPRequest.builder()
                        .userId(user.get().getId())
                        .otpLength(otpLength)
                        .otpAliveMinutes(otpAliveMinutes)
                        .build()
        );
        otpService.saveOTPNumber(otp);

        String messageTitle = "PocketChat Verification Code: " + otp.getOtp();

        String messageContent = "Your verification number is: " + otp.getOtp() + " that is valid for "
                + otpAliveMinutes + " minutes. It will expire in " + otp.getOtpExpirationDateTime()
                + ". Do not share this OTP to anybody.";

        List<String> receiverEmailAddresses = new ArrayList<>();
        receiverEmailAddresses.add(user.get().getEmailAddress());

        if (StringUtils.hasText(user.get().getEmailAddress())) {
            emailService.sendEmail(SendEmailRequest.builder()
                    .receiverList(receiverEmailAddresses)
                    .emailSubject(messageTitle)
                    .emailContent(messageContent)
                    .build());
        }

        if (StringUtils.hasText(user.get().getMobileNo())) {
            smsService.sendSMS(SendSMSRequest.builder().mobileNumber(user.get().getMobileNo()).message(messageContent).build());
        }

        return otpResponseMapper(otp);
    }

    // For Testing only
    // Register
    @Deprecated
    @Override
    @Transactional
    public UserAuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        // TODO: Decrypt the password from frontend
        String encodedPassword = passwordEncoder.encode(usernamePasswordUserAuthenticationRequest.getPassword());
        String assignedUserRole = "ROLE_USER";
        Optional<UserRole> userRoleOptional = userRoleRepoService.findByName(assignedUserRole);
        if (userRoleOptional.isEmpty()) {
            throw new UserRoleNotFoundException("Unable to find such user role during registration" +
                    " of the new User using UsernamePasswordAuthenticationRequest. User Role name: " + assignedUserRole);
        }

        UserAuthentication userAuthentication = userAuthenticationRepoService.save(UserAuthentication.builder()
                .username(usernamePasswordUserAuthenticationRequest.getUsername())
                .password(encodedPassword)
                .userRoles(Arrays.asList(userRoleOptional.get()))
                .build());

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userAuthentication.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return UserAuthenticationResponse.builder().jwt(jwt).build();
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

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(usernamePasswordUserAuthenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return UserAuthenticationResponse.builder().jwt(jwt).build();
    }

    // Used for OTP on mobile phone (Step 1)
    @Override
    public PreVerifyMobileNumberOTPResponse preVerifyMobileNumber(PreVerifyMobileNumberOTPRequest preVerifyMobileNumberOTPRequest) {

        Optional<User> userOptional = userRepoService.findByMobileNo(preVerifyMobileNumberOTPRequest.getMobileNumber());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Unable to find User using mobile number: " + preVerifyMobileNumberOTPRequest.getMobileNumber());
        }

        User user = userOptional.get();

        OTP otp = otpService.generateOtpNumber(
                GenerateOTPRequest.builder()
                        .userId(user.getId())
                        .otpLength(otpLength)
                        .otpAliveMinutes(otpAliveMinutes)
                        .build()
        );
        otpService.saveOTPNumber(otp);

        String messageBody = "Here's your OTP number: " + otp.getOtp().toString()
                + ". Your keyword is: " + otp.getKeyword()
                + ". This OTP will expire in " + otpAliveMinutes + " minutes.";

        emailService.sendEmail(SendEmailRequest.builder()
                .receiverList(Arrays.asList(user.getEmailAddress()))
                .emailSubject("PocketChat OTP Verification")
                .emailContent(messageBody)
                .build());
        smsService.sendSMS(SendSMSRequest.builder()
                .mobileNumber(user.getMobileNo())
                .message(messageBody)
                .build());

        return PreVerifyMobileNumberOTPResponse.builder()
                .tokenExpiryTime(otp.getOtpExpirationDateTime())
                .emailAddress(emailUtil.maskEmail(user.getEmailAddress()))
                .mobileNumber(phoneUtil.maskPhoneNumber(user.getMobileNo()))
                .build();
    }

    // Used for OTP on mobile phone (Step 2)
    // TODO: Encrypt secureKeyword and OTP number from frontend
    @Override
    public UserAuthenticationResponse verifyMobileNumberOTP(VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        // Go to OTP service to verify
        // If verified,
        Optional<User> userOptional = userRepoService.findByMobileNo(verifyMobileNumberOTPRequest.getMobileNo());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Unable to find the user during verifyMobileNumberOTP. Mobile Number: " + verifyMobileNumberOTPRequest.getMobileNo());
        }

        VerifyOTPNumberResponse verifyOTPNumberResponse = otpService.verifyOTPNumber(userOptional.get().getId(), verifyMobileNumberOTPRequest.getOtpNumber(), verifyMobileNumberOTPRequest.getSecureKeyword());

        if (!verifyOTPNumberResponse.isCorrect() && verifyOTPNumberResponse.isHasError()) {
            // Incorrect, has error (unable to find that OTP)
            throw new OTPNotFoundException("Unable to find that OTP using this user ID: " + userOptional.get().getId() + ".High possibility user didn't request for the OTP number.");
        } else if (!verifyOTPNumberResponse.isCorrect() && !verifyOTPNumberResponse.isHasError()) {
            // Incorrect, no error (Wrong OTP/Secure keyword)
            throw new WrongOTPException("Wrong OTP/Secure Keyword. Please try again. Mobile Number: " + verifyMobileNumberOTPRequest.getMobileNo());
        } else if (!verifyOTPNumberResponse.isCorrect() && !verifyOTPNumberResponse.isHasError() && verifyOTPNumberResponse.getLimitRemaining() == -1) {
            // Reached maximum OTP verify attempt
            throw new MaximumOTPVerificationAttemptReachedException("Maximum attempt OTP verification reached. Please request new OTP again. Mobile number: " + verifyMobileNumberOTPRequest.getMobileNo());
        }

        // Correct, no error
        Optional<UserAuthentication> userAuthenticationOptional = userAuthenticationRepoService.findByUserId(userOptional.get().getId());

        if (userAuthenticationOptional.isEmpty()) {
            throw new UsernameNotFoundException("Unable to find the User's Authentication using userId: " + userOptional.get().getId());
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userAuthenticationOptional.get().getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return UserAuthenticationResponse.builder().jwt(jwt).build();
    }

    @Override
    public VerifyEmailAddressResponse requestVerifyEmailAddress(VerifyEmailAddressRequest verifyEmailADdressRequest) {
        // TODO: Generate a token that represents the user and concatenate it with the web address of the email verification API
        // TODO: Create EmailVerificationToken DB, RepoService with CRD
        // TODO: Create Email Controller with an API with @PathVariable of the token to GET.
        // TODO: When Should open a page from here in the backend, respond "Email Address Verified Successful" HTML page.

//        SendEmailResponse sendEmailResponse =
        emailService.sendEmail(SendEmailRequest.builder()
                .receiverList(Arrays.asList(verifyEmailADdressRequest.getEmailAddress()))
                .emailSubject("")
                .emailContent("")
                .build());

        return null;
    }

    @Override
    public OTPResponse otpResponseMapper(OTP otp) {
        return OTPResponse.builder().otpExpirationDateTime(otp.getOtpExpirationDateTime()).build();
    }
}
