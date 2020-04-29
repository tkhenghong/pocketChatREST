package com.pocketchat.server.exceptions.user_contact;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserContactNotFoundException extends RuntimeException {
    public UserContactNotFoundException(String message) {
        super(message);
    }
}
