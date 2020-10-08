package com.pocketchat.server.exceptions.general;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class StringNotEmptyException extends RuntimeException {
    public StringNotEmptyException(String message) {
        super(message);
    }
}
