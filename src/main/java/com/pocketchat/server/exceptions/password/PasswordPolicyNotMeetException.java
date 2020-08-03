package com.pocketchat.server.exceptions.password;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordPolicyNotMeetException extends RuntimeException {
    public PasswordPolicyNotMeetException(String message) {
        super(message);
    }
}
