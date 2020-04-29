package com.pocketchat.controllers.authentication;

import com.pocketchat.models.controllers.request.authentication.MobileNumberOTPVerificationRequest;
import com.pocketchat.models.controllers.request.authentication.MobileNumberVerificationRequest;
import com.pocketchat.models.controllers.request.authentication.UsernamePasswordAuthenticationRequest;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.controllers.response.authentication.MobileNumberVerificationResponse;
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

    @PostMapping("")
    public AuthenticationResponse addUsernamePasswordAuthenticationRequest(@RequestBody UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        return authenticationService.addUsernamePasswordAuthenticationRequest(usernamePasswordAuthenticationRequest);
    }

    @PostMapping("/usernamePassword")
    public AuthenticationResponse usernamePasswordAuthentication(@RequestBody UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest) {
        return authenticationService.authenticateUsingUsernamePassword(usernamePasswordAuthenticationRequest);
    }

    @PostMapping("/mobileNumber/verify")
    public MobileNumberVerificationResponse mobileNumberVerification(@RequestBody MobileNumberVerificationRequest mobileNumberVerificationRequest) {
        return authenticationService.verifyUsingMobileNumber(mobileNumberVerificationRequest);
    }

    @PostMapping("/mobileNumber/authenticate")
    public AuthenticationResponse mobileNumberAuthentication(@RequestBody MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest) {
        return authenticationService.verifyMobileNumberOTP(mobileNumberOTPVerificationRequest);
    }
}
