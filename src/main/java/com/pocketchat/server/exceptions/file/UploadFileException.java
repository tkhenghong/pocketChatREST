package com.pocketchat.server.exceptions.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UploadFileException extends RuntimeException {
    public UploadFileException(String message) {
        super(message);
    }
}
