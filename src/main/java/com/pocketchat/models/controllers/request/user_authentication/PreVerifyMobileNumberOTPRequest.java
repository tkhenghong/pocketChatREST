package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

public class PreVerifyMobileNumberOTPRequest {
    @NotBlank
    private String mobileNumber;

    public @NotBlank String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(@NotBlank String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
