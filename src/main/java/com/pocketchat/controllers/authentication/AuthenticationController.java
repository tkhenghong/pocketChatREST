package com.pocketchat.controllers.authentication;

import com.pocketchat.models.controllers.request.authentication.*;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.controllers.response.authentication.OTPResponse;
import com.pocketchat.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @Autowired
    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // For testing only
    // Registration
    @PostMapping("")
    public AuthenticationResponse addUsernamePasswordAuthenticationRequest(@RequestBody UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        return authenticationService.addUsernamePasswordAuthenticationRequest(usernamePasswordAuthenticationRequest);
    }

    @PostMapping("/request/mobileNumber")
    public OTPResponse requestToAuthenticateWithMobileNo(@RequestBody MobileNoAuthenticationRequest mobileNoAuthenticationRequest) {
        return authenticationService.requestToAuthenticateWithMobileNo(mobileNoAuthenticationRequest);
    }

    @PostMapping("/request/emailAddress")
    public OTPResponse requestToAuthenticateWithEmailAddress(@RequestBody EmailAddressAuthenticationRequest emailAddressAuthenticationRequest) {
        return authenticationService.requestToAuthenticateWithEmailAddress(emailAddressAuthenticationRequest);
    }

    // For testing only
    // Login
    @PostMapping("/usernamePassword")
    public AuthenticationResponse usernamePasswordAuthentication(@RequestBody UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        return authenticationService.authenticateUsingUsernamePassword(usernamePasswordAuthenticationRequest);
    }

    @PostMapping("/emailAddress/authenticate")
    public AuthenticationResponse emailAuthentication(@RequestBody EmailOTPVerificationRequest emailOTPVerificationRequest) {
        return authenticationService.verifyEmailAddressOTP(emailOTPVerificationRequest);
    }

    @PostMapping("/mobileNumber/authenticate")
    public AuthenticationResponse mobileNumberAuthentication(@RequestBody MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest) {
        return authenticationService.verifyMobileNumberOTP(mobileNumberOTPVerificationRequest);
    }
}
