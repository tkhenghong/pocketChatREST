package com.pocketchat.server.exceptions.conversation_group_block;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UnblockConversationGroupException extends RuntimeException {
    public UnblockConversationGroupException(String message) {
        super(message);
    }
}
