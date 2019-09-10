package com.pocketchat.controllers.response.message;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageResponse {

    private String id;

    private String conversationId;

    private String senderId;

    private String senderName;

    private String senderMobileNo;

    private String receiverId;

    private String receiverName;

    private String receiverMobileNo;

    private String type;

    private String status; // Sent, received, unread, read

    private String messageContent;

    private String multimediaId;

    private long timestamp;
}
