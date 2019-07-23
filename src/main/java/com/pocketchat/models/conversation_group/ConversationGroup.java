package com.pocketchat.models.conversation_group;

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
@Document(collection = "conversation_group")
public class ConversationGroup {

    @Id
    String id;

    @NotBlank
    String creatorUserId;

    @NotBlank
    String createdDate;

    @NotBlank
    String name;

    @NotBlank
    String type;

    @NotBlank
    String unreadMessageId;

    @NotBlank
    String description;

    @NotBlank
    boolean block;

    @NotBlank
    int notificationExpireDate;

    @NotBlank
    String timestamp;
}
