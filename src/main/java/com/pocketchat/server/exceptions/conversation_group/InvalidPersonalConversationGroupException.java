package com.pocketchat.server.exceptions.conversation_group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidPersonalConversationGroupException extends RuntimeException {
    public InvalidPersonalConversationGroupException(String message) {
        super(message);
    }
}
