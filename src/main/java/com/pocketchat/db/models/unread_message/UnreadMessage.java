package com.pocketchat.db.models.unread_message;

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
@Document(collection = "unread_message")
public class UnreadMessage {

    @Id
    private String id;

    @NotBlank
    private String conversationId;

    @NotBlank
    private String userId;

    @NotBlank
    private String lastMessage;

    private int date;

    private int count;
}
