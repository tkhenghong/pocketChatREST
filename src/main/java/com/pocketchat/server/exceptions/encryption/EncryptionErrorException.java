package com.pocketchat.server.exceptions.encryption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EncryptionErrorException extends RuntimeException {
    public EncryptionErrorException(String message) {
        super(message);
    }
}
