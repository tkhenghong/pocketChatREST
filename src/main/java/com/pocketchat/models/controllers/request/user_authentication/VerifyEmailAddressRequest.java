package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

public class VerifyEmailAddressRequest {
    @NotBlank
    private String emailAddress;

    public @NotBlank String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(@NotBlank String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
