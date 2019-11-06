package com.pocketchat.db.models.message;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    private String receiverId;

    private String receiverName;

    private String receiverMobileNo;

    private String type;

    private String status; // Sent, received, unread, read

    @NotBlank
    private String messageContent;

    private String multimediaId;

    @NotNull
    private DateTime timestamp;
}
