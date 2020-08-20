package com.pocketchat.server.exceptions.conversation_group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CreatorIsNotConversationGroupMemberException extends RuntimeException {
    public CreatorIsNotConversationGroupMemberException(String message) {
        super(message);
    }
}
