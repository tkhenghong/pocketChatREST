package com.pocketchat.server.exceptions.general;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class StringEmptyException extends RuntimeException {
    public StringEmptyException(String message) {
        super(message);
    }
}
