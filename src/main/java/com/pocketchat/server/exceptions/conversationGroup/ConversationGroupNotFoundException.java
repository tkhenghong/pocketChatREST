package com.pocketchat.server.exceptions.conversationGroup;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConversationGroupNotFoundException extends RuntimeException {
    public ConversationGroupNotFoundException(String message) {
        super(message);
    }
}
