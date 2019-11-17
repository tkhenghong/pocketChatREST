package com.pocketchat.db.models.user;

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
@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String googleAccountId;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String effectivePhoneNumber;
}
