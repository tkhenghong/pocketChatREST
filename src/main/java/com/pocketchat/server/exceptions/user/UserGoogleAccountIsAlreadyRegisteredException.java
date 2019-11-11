package com.pocketchat.server.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserGoogleAccountIsAlreadyRegisteredException extends RuntimeException {
    public UserGoogleAccountIsAlreadyRegisteredException(String message) {
        super(message);
    }
}
