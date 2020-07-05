package com.pocketchat.server.exceptions.otp;

public class WrongOTPException extends RuntimeException {
    public WrongOTPException(String message) {
        super(message);
    }
}
