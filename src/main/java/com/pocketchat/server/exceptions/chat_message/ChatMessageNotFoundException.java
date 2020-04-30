package com.pocketchat.server.exceptions.chat_message;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatMessageNotFoundException extends RuntimeException {
    public ChatMessageNotFoundException(String message) {
        super(message);
    }
}
