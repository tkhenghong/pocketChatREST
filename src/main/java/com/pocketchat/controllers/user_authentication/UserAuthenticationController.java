package com.pocketchat.controllers.user_authentication;

import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.PreVerifyMobileNumberOTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
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

    @PostMapping("/mobileNumber/preAuthenticate")
    public PreVerifyMobileNumberOTPResponse preVerifyMobileNumber(@Valid @RequestBody PreVerifyMobileNumberOTPRequest preVerifyMobileNumberOTPRequest) {
        return userAuthenticationService.preVerifyMobileNumber(preVerifyMobileNumberOTPRequest);
    }

    @PostMapping("/mobileNumber/authenticate")
    public UserAuthenticationResponse mobileNumberAuthentication(@Valid @RequestBody VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest) {
        return userAuthenticationService.verifyMobileNumberOTP(verifyMobileNumberOTPRequest);
    }

    @PostMapping("/emailAddress/preVerify")
    public VerifyEmailAddressResponse requestVerifyEmailAddress(@Valid @RequestBody VerifyEmailAddressRequest verifyEmailADdressRequest) {
        return userAuthenticationService.requestVerifyEmailAddress(verifyEmailADdressRequest);
    }
}
