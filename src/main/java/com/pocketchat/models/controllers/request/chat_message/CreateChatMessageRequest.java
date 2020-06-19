package com.pocketchat.models.controllers.request.chat_message;

import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateChatMessageRequest extends ChatMessageRequest {
    CreateChatMessageRequest(String id, @NotBlank String conversationId, @NotBlank String senderId, @NotBlank String senderName, @NotBlank String senderMobileNo, String receiverId, String receiverName, String receiverMobileNo, String type, String status, @NotBlank String messageContent, String multimediaId, @NotNull DateTime createdTime, DateTime sentTime) {
        super(id, conversationId, senderId, senderName, senderMobileNo, receiverId, receiverName, receiverMobileNo, type, status, messageContent, multimediaId, createdTime, sentTime);
    }
}
