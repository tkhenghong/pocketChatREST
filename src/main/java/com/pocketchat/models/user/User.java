package com.pocketchat.models.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode

@Document(collection = "user")
public class User {

    @Id
    String id;

    @NotBlank
    String displayName;

    @NotBlank
    String realName;

    @NotBlank
    String mobileNo;

    @NotBlank
    String googleAccountId;
}
