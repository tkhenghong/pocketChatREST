package com.pocketchat.services.user_authentication;

import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
import com.pocketchat.models.otp.OTP;


public interface UserAuthenticationService {

    OTPResponse requestToAuthenticateWithMobileNo(MobileNoUserAuthenticationRequest mobileNoUserAuthenticationRequest);

    OTPResponse requestToAuthenticateWithEmailAddress(EmailAddressUserAuthenticationRequest mobileNoAuthenticationRequest);

    // For testing only
    // Registration
    @Deprecated
    UserAuthenticationResponse addUsernamePasswordAuthenticationRequest(UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest);

    // For testing only
    // Login
    @Deprecated
    UserAuthenticationResponse authenticateUsingUsernamePassword(UsernamePasswordUserAuthenticationRequest usernamePasswordUserAuthenticationRequest);

    UserAuthenticationResponse verifyEmailAddressOTP(EmailOTPVerificationRequest emailOTPVerificationRequest);

    UserAuthenticationResponse verifyMobileNumberOTP(MobileNumberOTPVerificationRequest mobileNumberOTPVerificationRequest);

    OTPResponse otpResponseMapper(OTP otp);
}
