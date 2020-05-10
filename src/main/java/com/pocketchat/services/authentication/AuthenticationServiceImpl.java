package com.pocketchat.services.authentication;

import com.pocketchat.db.models.authentication.Authentication;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repo_services.authentication.AuthenticationRepoService;
import com.pocketchat.db.repo_services.user.UserRepoService;
import com.pocketchat.models.controllers.request.authentication.*;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.email.SendEmailRequest;
import com.pocketchat.models.otp.GenerateOTPRequest;
import com.pocketchat.models.otp.OTP;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.server.configurations.security.service.MyUserDetailsService;
import com.pocketchat.server.exceptions.authentication.UsernameExistException;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

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
    AuthenticationServiceImpl(
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
    public OTP requestToAuthenticateWithMobileNo(MobileNoAuthenticationRequest mobileNoAuthenticationRequest) {
        Optional<User> user = userRepoService.findByMobileNo(mobileNoAuthenticationRequest.getMobileNo());

        if (!user.isPresent()) {
            throw new UserNotFoundException("User is not found by using mobile number: " + mobileNoAuthenticationRequest.getMobileNo());
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

        return otp;
    }

    // 1. Find User in DB.
    // 2. Generate OTP number.
    // 3. Prepare message title and content.
    // 4. Send Email.
    // 5. Send SMS.
    @Override
    public OTP requestToAuthenticateWithEmailAddress(EmailAddressAuthenticationRequest mobileNoAuthenticationRequest) {
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

        return otp;
    }

    @Override
    public AuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        // TODO: Decrypt the password from frontend
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(usernamePasswordAuthenticationRequest.getPassword());

        Optional<Authentication> authenticationOptional = authenticationRepoService.findFirstByUsername(usernamePasswordAuthenticationRequest.getUsername());

        if (authenticationOptional.isPresent()) {
            throw new UsernameExistException("Username has already exist for username: " + usernamePasswordAuthenticationRequest.getUsername());
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(usernamePasswordAuthenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        authenticationRepoService.save(Authentication.builder()
                .username(usernamePasswordAuthenticationRequest.getUsername())
                .password(encodedPassword)
                .build());

        return AuthenticationResponse.builder().jwt(jwt).build();
    }

    @Override
    public AuthenticationResponse authenticateUsingUsernamePassword(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePasswordAuthenticationRequest.getUsername(), usernamePasswordAuthenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(usernamePasswordAuthenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return AuthenticationResponse.builder().jwt(jwt).build();
    }

    @Override
    public AuthenticationResponse verifyEmailAddressOTP(EmailOTPVerificationRequest emailOTPVerificationRequest) {
        return null;
    }

    @Override
    public AuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest) {
        return null;
    }
}
