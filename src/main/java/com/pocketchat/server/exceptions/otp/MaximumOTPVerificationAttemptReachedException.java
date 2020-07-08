package com.pocketchat.server.exceptions.otp;

public class MaximumOTPVerificationAttemptReachedException extends RuntimeException {
    public MaximumOTPVerificationAttemptReachedException(String message) {
        super(message);
    }
}
