package com.pocketchat.server.exceptions.sms;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidSendSMSRequestException extends RuntimeException {
    public InvalidSendSMSRequestException(String message) {
        super(message);
    }
}
