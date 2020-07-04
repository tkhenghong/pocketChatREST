package com.pocketchat.services.authentication;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repo_services.authentication.AuthenticationRepoService;
import com.pocketchat.db.repo_services.user.UserRepoService;
import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.server.configurations.security.service.MyUserDetailsService;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.services.sms.SMSService;
import com.pocketchat.utils.jwt.JwtUtil;
import com.pocketchat.utils.otp.OTPNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final AuthenticationRepoService authenticationRepoService;

    private final UserRepoService userRepoService;

    private final SMSService smsService;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;

    private final OTPNumberGenerator otpNumberGenerator;

    private final JwtUtil jwtUtil;

    @Value("${server.otp.length}")
    private int otpLength;

    @Value("${server.otp.alive.minutes}")
    private int otpAliveMinutes;

    @Autowired
    UserAuthenticationServiceImpl(
            AuthenticationRepoService authenticationRepoService,
            UserRepoService userRepoService,
            SMSService smsService,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            OTPNumberGenerator otpNumberGenerator,
            JwtUtil jwtUtil) {
        this.authenticationRepoService = authenticationRepoService;
        this.userRepoService = userRepoService;
        this.smsService = smsService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.otpNumberGenerator = otpNumberGenerator;
        this.jwtUtil = jwtUtil;
    }

    // 1. Find User in DB.
    // 2. Generate OTP number.
    // 3. Prepare message title and content.
    // 4. Send SMS.
    // 5. Send Email.
    @Override
    public OTPResponse requestToAuthenticateWithMobileNo(MobileNoUserAuthenticationRequest mobileNoUserAuthenticationRequest) {
        Optional<User> user = userRepoService.findByMobileNo(mobileNoUserAuthenticationRequest.getMobileNo());

        if (!user.isPresent()) {
            throw new UserNotFoundException("User is not found by using mobile number: " + mobileNoUserAuthenticationRequest.getMobileNo());
        }

        OTP otp = otpNumberGenerator.generateOtpNumber(
                GenerateOTPRequest.builder()
                        .userId(user.get().getId())
                        .otpLength(otpLength)
                        .otpAliveMinutes(otpAliveMinutes)
                        .build()
        );

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

    // 1. Find User in DB.
    // 2. Generate OTP number.
    // 3. Prepare message title and content.
    // 4. Send Email.
    // 5. Send SMS.
    @Override
    public OTPResponse requestToAuthenticateWithEmailAddress(EmailAddressUserAuthenticationRequest mobileNoAuthenticationRequest) {
        Optional<User> user = userRepoService.findByEmailAddress(mobileNoAuthenticationRequest.getEmailAddress());

        if (!user.isPresent()) {
            throw new UserNotFoundException("User is not found by using Email address: " + mobileNoAuthenticationRequest.getEmailAddress());
        }

        OTP otp = otpNumberGenerator.generateOtpNumber(
                GenerateOTPRequest.builder()
                        .userId(user.get().getId())
                        .otpLength(otpLength)
                        .otpAliveMinutes(otpAliveMinutes)
                        .build()
        );

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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(usernamePasswordUserAuthenticationRequest.getPassword());

        UserAuthentication userAuthentication = authenticationRepoService.save(UserAuthentication.builder()
                .username(usernamePasswordUserAuthenticationRequest.getUsername())
                .password(encodedPassword)
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

    @Override
    public UserAuthenticationResponse verifyEmailAddressOTP(EmailOTPVerificationRequest emailOTPVerificationRequest) {
        return null;
    }

    @Override
    public UserAuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest) {
        return null;
    }

    @Override
    public OTPResponse otpResponseMapper(OTP otp) {
        return OTPResponse.builder().otpExpirationDateTime(otp.getOtpExpirationDateTime()).build();
    }
}
