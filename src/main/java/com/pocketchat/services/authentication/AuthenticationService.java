package com.pocketchat.services.authentication;

import com.pocketchat.models.controllers.request.authentication.MobileNumberOTPVerificationRequest;
import com.pocketchat.models.controllers.request.authentication.MobileNumberVerificationRequest;
import com.pocketchat.models.controllers.request.authentication.UsernamePasswordAuthenticationRequest;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.controllers.response.authentication.MobileNumberVerificationResponse;


public interface AuthenticationService {
    AuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest);

    AuthenticationResponse authenticateUsingUsernamePassword(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest);

    MobileNumberVerificationResponse verifyUsingMobileNumber(MobileNumberVerificationRequest mobileNumberVerificationRequest);

    AuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest);
}
