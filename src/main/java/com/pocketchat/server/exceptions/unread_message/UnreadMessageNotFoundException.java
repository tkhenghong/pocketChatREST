package com.pocketchat.server.exceptions.unread_message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnreadMessageNotFoundException extends RuntimeException {
    public UnreadMessageNotFoundException(String message) {
        super(message);
    }
}
