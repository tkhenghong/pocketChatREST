package com.pocketchat.models.groupMember;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "group_member")
public class GroupMember {

    @Id
    String id;

    @NotBlank
    String conversationId;

    @NotBlank
    String name;

    @NotBlank
    String contactNo;
}
