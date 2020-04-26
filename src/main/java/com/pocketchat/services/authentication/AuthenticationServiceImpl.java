package com.pocketchat.services.authentication;

import com.pocketchat.db.models.authentication.Authentication;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.authentication.AuthenticationRepoService;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.models.controllers.request.authentication.MobileNumberOTPVerificationRequest;
import com.pocketchat.models.controllers.request.authentication.MobileNumberVerificationRequest;
import com.pocketchat.models.controllers.request.authentication.UsernamePasswordAuthenticationRequest;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.controllers.response.authentication.MobileNumberVerificationResponse;
import com.pocketchat.models.sms.SendSMSRequest;
import com.pocketchat.server.configurations.security.service.MyUserDetailsService;
import com.pocketchat.server.exceptions.mobileNumber.MobileNumberNotFoundException;
import com.pocketchat.services.email.EmailService;
import com.pocketchat.services.sms.SMSService;
import com.pocketchat.utils.jwt.JwtUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationRepoService authenticationRepoService;

    private final UserRepoService userRepoService;

    private final SMSService smsService;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;

    private final JwtUtil jwtUtil;

    @Autowired
    AuthenticationServiceImpl(
            AuthenticationRepoService authenticationRepoService,
            UserRepoService userRepoService,
            SMSService smsService,
            EmailService emailService,
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationRepoService = authenticationRepoService;
        this.userRepoService = userRepoService;
        this.smsService = smsService;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public AuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(usernamePasswordAuthenticationRequest.getPassword());
        Authentication authentication = authenticationRepoService.save(Authentication.builder()
                .username(usernamePasswordAuthenticationRequest.getUsername())
                .password(encodedPassword)
                .build());

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authentication.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

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

    // 1. Get mobile number from DB
    // 2. send SMS
    // 3. Return token expiration time
    @Override
    public MobileNumberVerificationResponse verifyUsingMobileNumber(MobileNumberVerificationRequest mobileNumberVerificationRequest) {
        Optional<User> userOptional = userRepoService.findByMobileNo(mobileNumberVerificationRequest.getMobileNumber());

        if (!userOptional.isPresent()) {
            throw new MobileNumberNotFoundException("Mobile number not found by using mobileNo: " + mobileNumberVerificationRequest.getMobileNumber());
        }

        smsService.sendSMS(SendSMSRequest.builder()
                .mobileNumber(mobileNumberVerificationRequest.getMobileNumber())
                .message("Test message sent!")
                .build());

        return MobileNumberVerificationResponse.builder()
                .mobileNo(mobileNumberVerificationRequest.getMobileNumber())
                .otpExpirationTime(new DateTime())
                .build();
    }

    @Override
    public AuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest) {
        return null;
    }
}
