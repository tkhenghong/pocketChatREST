package com.pocketchat.server.exceptions.otp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class MaximumOTPVerificationAttemptReachedException extends RuntimeException {
    public MaximumOTPVerificationAttemptReachedException(String message) {
        super(message);
    }
}
