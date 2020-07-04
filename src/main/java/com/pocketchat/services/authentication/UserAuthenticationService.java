package com.pocketchat.services.authentication;

import com.pocketchat.models.controllers.request.authentication.*;
import com.pocketchat.models.controllers.response.authentication.AuthenticationResponse;
import com.pocketchat.models.controllers.response.authentication.OTPResponse;
import com.pocketchat.models.otp.OTP;


public interface AuthenticationService {

    OTPResponse requestToAuthenticateWithMobileNo(MobileNoAuthenticationRequest mobileNoAuthenticationRequest);

    OTPResponse requestToAuthenticateWithEmailAddress(EmailAddressAuthenticationRequest mobileNoAuthenticationRequest);

    // For testing only
    // Registration
    @Deprecated
    AuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest);

    // For testing only
    // Login
    @Deprecated
    AuthenticationResponse authenticateUsingUsernamePassword(UsernamePasswordAuthenticationRequest usernamePasswordAuthenticationRequest);

    AuthenticationResponse verifyEmailAddressOTP(EmailOTPVerificationRequest emailOTPVerificationRequest);

    AuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest);

    OTPResponse otpResponseMapper(OTP otp);
}
