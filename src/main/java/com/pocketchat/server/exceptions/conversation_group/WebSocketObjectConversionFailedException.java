package com.pocketchat.server.exceptions.conversation_group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WebSocketObjectConversionFailedException extends RuntimeException {
    public WebSocketObjectConversionFailedException(String message) {
        super(message);
    }
}
