package com.pocketchat.server.exceptions.conversation_group_mute_notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UnmuteConversationGroupException extends RuntimeException {
    public UnmuteConversationGroupException(String message) {
        super(message);
    }
}
