package com.pocketchat.server.exceptions.otp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OTPNotFoundException extends RuntimeException {
    public OTPNotFoundException(String message) {
        super(message);
    }
}
