package com.pocketchat.server.exceptions.conversation_group_block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConversationGroupBlockNotFoundException extends RuntimeException {
    public ConversationGroupBlockNotFoundException(String message) {
        super(message);
    }
}
