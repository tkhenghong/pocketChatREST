package com.pocketchat.server.configurations.websocket;

import lombok.*;

@Getter
@Setter
public class CustomizedMessage {

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

    private int timestamp;
}
