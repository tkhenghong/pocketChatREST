package com.pocketchat.server.exceptions.conversation_group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NoConversationGroupAdminException extends RuntimeException {
    public NoConversationGroupAdminException(String message) {
        super(message);
    }
}
