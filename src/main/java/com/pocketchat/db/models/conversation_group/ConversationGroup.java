package com.pocketchat.db.models.conversation_group;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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
    private String id;

    @NotBlank
    private String creatorUserId;

    @NotNull
    private DateTime createdDate;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    String description;

//    @Valid
//    @NotEmpty
//    @Size(min = 1) // If 1 of 2 persons group left, that person still can have this group
    private List<String> memberIds;

//    @Valid
//    @NotEmpty
//    @Size(min = 1)
    private List<String> adminMemberIds;

    private boolean block;

    private DateTime notificationExpireDate;
}
