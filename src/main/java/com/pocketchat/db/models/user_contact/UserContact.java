package com.pocketchat.db.models.user_contact;

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
@Document(collection = "user_contact")
public class UserContact {

    @Id
    private String id;

    @NotBlank
    private String conversationId;

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    @NotBlank
    private String userId;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String lastSeenDate;

    private boolean block;
}
