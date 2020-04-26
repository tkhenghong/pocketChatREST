package com.pocketchat.models.controllers.request.user;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest extends UserRequest {

    CreateUserRequest(String id, @NotBlank String displayName, @NotBlank String realName, @NotBlank String mobileNo, @NotBlank String googleAccountId, String emailAddress, @NotBlank String countryCode, @NotBlank String effectivePhoneNumber) {
        super(id, displayName, realName, mobileNo, googleAccountId, emailAddress, countryCode, effectivePhoneNumber);
    }
}
