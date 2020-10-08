package com.pocketchat.controllers.user_authentication;

import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.PreVerifyMobileNumberOTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.VerifyEmailAddressResponse;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authentication")
public class UserAuthenticationController {

    UserAuthenticationService userAuthenticationService;

    @Autowired
    UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/register/mobileNumber")
    public PreVerifyMobileNumberOTPResponse registerMobileNumber(@Valid @RequestBody RegisterUsingMobileNumberRequest registerUsingMobileNumberRequest) {
        return userAuthenticationService.registerMobileNumber(registerUsingMobileNumberRequest);
    }

    @PostMapping("/register/mobileNumber/authenticate")
    public UserAuthenticationResponse registerMobileNumberOTPVerification(@Valid @RequestBody VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        return userAuthenticationService.registerMobileNumberOTPVerification(verifyMobileNumberOTPRequest);
    }

    @PostMapping("/login/mobileNumber")
    public PreVerifyMobileNumberOTPResponse loginMobileNumber(@Valid @RequestBody PreVerifyMobileNumberOTPRequest preVerifyMobileNumberOTPRequest) {
        return userAuthenticationService.loginMobileNumber(preVerifyMobileNumberOTPRequest);
    }

    @PostMapping("/login/mobileNumber/authenticate")
    public UserAuthenticationResponse loginMobileNumberOTPVerification(@Valid @RequestBody VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        return userAuthenticationService.loginMobileNumberOTPVerification(verifyMobileNumberOTPRequest);
    }

    @PostMapping("/emailAddress/preVerify")
    public VerifyEmailAddressResponse requestVerifyEmailAddress(@Valid @RequestBody VerifyEmailAddressRequest verifyEmailADdressRequest) {
        return userAuthenticationService.requestVerifyEmailAddress(verifyEmailADdressRequest);
    }

    // For testing only
    // Registration
    @PostMapping("")
    public UserAuthenticationResponse addUsernamePasswordAuthenticationRequest(@Valid @RequestBody UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        return userAuthenticationService.addUsernamePasswordAuthenticationRequest(usernamePasswordUserAuthenticationRequest);
    }

    // NOT RELATED TO POCKETCHAT
    @PostMapping("/request/mobileNumber")
    public OTPResponse requestToAuthenticateWithMobileNo(@Valid @RequestBody MobileNoUserAuthenticationRequest mobileNoUserAuthenticationRequest) {
        return userAuthenticationService.requestToAuthenticateWithMobileNo(mobileNoUserAuthenticationRequest);
    }

    // NOT RELATED TO POCKETCHAT
    @PostMapping("/request/emailAddress")
    public OTPResponse requestToAuthenticateWithEmailAddress(@Valid @RequestBody EmailAddressUserAuthenticationRequest emailAddressUserAuthenticationRequest) {
        return userAuthenticationService.requestToAuthenticateWithEmailAddress(emailAddressUserAuthenticationRequest);
    }

    // For testing only
    // Login
    @PostMapping("/usernamePassword")
    public UserAuthenticationResponse usernamePasswordAuthentication(@Valid @RequestBody UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest) {
        return userAuthenticationService.authenticateUsingUsernamePassword(usernamePasswordUserAuthenticationRequest);
    }
}
