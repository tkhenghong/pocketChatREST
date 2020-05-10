package com.pocketchat.models.controllers.request.unread_message;

import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateUnreadMessageRequest extends UnreadMessageRequest {
    UpdateUnreadMessageRequest(String id, @NotBlank String conversationId, @NotBlank String userId, String lastMessage, @NotNull DateTime date, Integer count) {
        super(id, conversationId, userId, lastMessage, date, count);
    }
}
