package com.pocketchat.services.user_authentication;

import com.pocketchat.models.controllers.request.user_authentication.*;
import com.pocketchat.models.controllers.response.user_authentication.OTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.PreVerifyMobileNumberOTPResponse;
import com.pocketchat.models.controllers.response.user_authentication.UserAuthenticationResponse;
import com.pocketchat.models.controllers.response.user_authentication.VerifyEmailAddressResponse;
import com.pocketchat.models.otp.OTP;


public interface UserAuthenticationService {

    PreVerifyMobileNumberOTPResponse registerMobileNumber(RegisterUsingMobileNumberRequest registerUsingMobileNumberRequest);

    PreVerifyMobileNumberOTPResponse loginMobileNumber(PreVerifyMobileNumberOTPRequest preVerifyMobileNumberOTPRequest);

    UserAuthenticationResponse registerMobileNumberOTPVerification(VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest);

    UserAuthenticationResponse loginMobileNumberOTPVerification(VerifyMobileNumberOTPRequest verifyMobileNumberOTPRequest);

    VerifyEmailAddressResponse requestVerifyEmailAddress(VerifyEmailAddressRequest verifyEmailADdressRequest);

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


    OTPResponse otpResponseMapper(OTP otp);
}
