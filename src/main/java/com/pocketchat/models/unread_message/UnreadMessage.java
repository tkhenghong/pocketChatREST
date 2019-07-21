package com.pocketchat.models.unread_message;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "unread_message")
public class UnreadMessage {

    @Id
    String id;

    @NotBlank
    String conversationId;

    @NotBlank
    String userId;

    @NotBlank
    String lastMessage;

    @NotBlank
    int date;

    @NotBlank
    int count;
}
