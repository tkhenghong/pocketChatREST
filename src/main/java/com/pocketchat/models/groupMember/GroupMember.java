package com.pocketchat.models.groupMember;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

// Should use User Contact from now on
@Deprecated
@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "group_member")
public class GroupMember {

    @Id
    private String id;

    @NotBlank
    private String conversationId;

    @NotBlank
    private String name;

    @NotBlank
    private String contactNo;
}
