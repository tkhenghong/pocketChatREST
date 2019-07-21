package com.pocketchat.models.message;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "message")
public class Message {

    @Id
    String id;

    @NotBlank
    String conversationId;

    @NotBlank
    String senderId;

    @NotBlank
    String senderName;

    @NotBlank
    String senderMobileNo;

    @NotBlank
    String receiverId;

    @NotBlank
    String receiverName;

    @NotBlank
    String receiverMobileNo;

    @NotBlank
    String type;

    @NotBlank
    String status; // Sent, received, unread, read

    @NotBlank
    String messageContent;

    @NotBlank
    String multimediaId;

    @NotBlank
    String timestamp;
}
