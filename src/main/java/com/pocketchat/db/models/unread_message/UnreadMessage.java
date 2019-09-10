package com.pocketchat.db.models.unread_message;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    private String lastMessage;

    @NotNull
    private DateTime date;

    private int count;
}
