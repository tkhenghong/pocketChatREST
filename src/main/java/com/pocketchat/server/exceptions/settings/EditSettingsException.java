package com.pocketchat.server.exceptions.settings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class EditSettingsException extends RuntimeException {
    public EditSettingsException(String message) {
        super(message);
    }
}
