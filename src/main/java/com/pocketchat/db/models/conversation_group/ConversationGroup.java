package com.pocketchat.db.models.conversation_group;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    String description;

    @Valid
    @NotEmpty
    @Size(min = 1) // If 1 of 2 persons group left, that person still can have this group
    String[] memberIds;

    @Valid
    @NotEmpty
    @Size(min = 1)
    String[] adminMemberIds;

    //    @NotBlank
    boolean block;

    //    @NotBlank
    int notificationExpireDate;
}
