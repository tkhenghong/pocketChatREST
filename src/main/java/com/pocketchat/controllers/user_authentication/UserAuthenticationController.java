package com.pocketchat.controllers.user_authentication;

import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    // For testing only
    // Registration
    @PostMapping("")
    public UserAuthenticationResponse addUsernamePasswordAuthenticationRequest(@RequestBody UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        return userAuthenticationService.addUsernamePasswordAuthenticationRequest(usernamePasswordUserAuthenticationRequest);
    }

    @PostMapping("/request/mobileNumber")
    public OTPResponse requestToAuthenticateWithMobileNo(@RequestBody MobileNoUserAuthenticationRequest mobileNoUserAuthenticationRequest) {
        return userAuthenticationService.requestToAuthenticateWithMobileNo(mobileNoUserAuthenticationRequest);
    }

    @PostMapping("/request/emailAddress")
    public OTPResponse requestToAuthenticateWithEmailAddress(@RequestBody EmailAddressUserAuthenticationRequest emailAddressUserAuthenticationRequest) {
        return userAuthenticationService.requestToAuthenticateWithEmailAddress(emailAddressUserAuthenticationRequest);
    }

    // For testing only
    // Login
    @PostMapping("/usernamePassword")
    public UserAuthenticationResponse usernamePasswordAuthentication(@RequestBody UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        return userAuthenticationService.authenticateUsingUsernamePassword(usernamePasswordUserAuthenticationRequest);
    }

    @PostMapping("/emailAddress/authenticate")
    public UserAuthenticationResponse emailAuthentication(@RequestBody EmailOTPVerificationRequest emailOTPVerificationRequest) {
        return userAuthenticationService.verifyEmailAddressOTP(emailOTPVerificationRequest);
    }

    @PostMapping("/mobileNumber/authenticate")
    public UserAuthenticationResponse mobileNumberAuthentication(@RequestBody VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        return userAuthenticationService.verifyMobileNumberOTP(verifyMobileNumberOTPRequest);
    }
}
