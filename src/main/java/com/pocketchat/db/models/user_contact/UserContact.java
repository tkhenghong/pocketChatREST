package com.pocketchat.db.models.user_contact;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

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

    @Valid
    @NotEmpty
    @Size(min = 1)
    private String[] userIds;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String lastSeenDate;

    private boolean block;

    @NotBlank
    private String multimediaId;
}
