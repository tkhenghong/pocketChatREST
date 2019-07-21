package com.pocketchat.models.user_contact;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "user_contact")
public class UserContact {

    @Id
    String id;

    @NotBlank
    String conversationId;

    @NotBlank
    String displayName;

    @NotBlank
    String realName;

    @NotBlank
    String userId;

    @NotBlank
    String mobileNo;

    @NotBlank
    String lastSeenDate;

    @NotBlank
    boolean block;
}
