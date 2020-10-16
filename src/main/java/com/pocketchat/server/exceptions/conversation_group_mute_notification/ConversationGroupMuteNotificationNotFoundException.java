package com.pocketchat.server.exceptions.conversation_group_mute_notification;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ConversationGroupMuteNotificationNotFoundException extends RuntimeException {
    public ConversationGroupMuteNotificationNotFoundException(String message) {
        super(message);
    }
}
