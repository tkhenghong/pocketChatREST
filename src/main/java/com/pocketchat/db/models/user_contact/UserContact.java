package com.pocketchat.db.models.user_contact;

import lombok.*;
import org.joda.time.DateTime;
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
    private String displayName;

    @NotBlank
    private String realName;

    private String about;

    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<String> userIds;

    private String userId;

    @NotBlank
    private String mobileNo;

    private DateTime lastSeenDate;

    private boolean block;

    private String multimediaId;
}
