package com.pocketchat.models.controllers.request.unread_message;

import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUnreadMessageRequest extends UnreadMessageRequest {
    CreateUnreadMessageRequest(String id, @NotBlank String conversationId, @NotBlank String userId, String lastMessage, @NotNull DateTime date, int count) {
        super(id, conversationId, userId, lastMessage, date, count);
    }
}
