package com.pocketchat.db.models.message;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "message")
public class Message {

    @Id
    private String id;

    @NotBlank
    private String conversationId;

    @NotBlank
    private String senderId;

    @NotBlank
    private String senderName;

    @NotBlank
    private String senderMobileNo;

    @NotBlank
    private String receiverId;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverMobileNo;

    @NotBlank
    private String type;

    @NotBlank
    private String status; // Sent, received, unread, read

    @NotBlank
    private String messageContent;

    @NotBlank
    private String multimediaId;

    @NotBlank
    private String timestamp;
}
