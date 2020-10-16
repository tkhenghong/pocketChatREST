package com.pocketchat.server.exceptions.conversation_group;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ConversationGroupMemberPermissionException extends RuntimeException {
    public ConversationGroupMemberPermissionException(String message) {
        super(message);
    }
}
