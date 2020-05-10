package com.pocketchat.services.authentication;

import com.pocketchat.models.controllers.request.authentication.*;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.otp.OTP;


public interface AuthenticationService {

    OTP requestToAuthenticateWithMobileNo(MobileNoAuthenticationRequest mobileNoAuthenticationRequest);

    OTP requestToAuthenticateWithEmailAddress(EmailAddressAuthenticationRequest mobileNoAuthenticationRequest);

    AuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest);

    AuthenticationResponse authenticateUsingUsernamePassword(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest);

    AuthenticationResponse verifyEmailAddressOTP(EmailOTPVerificationRequest emailOTPVerificationRequest);

    AuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest);
}
