package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

public class MobileNoUserAuthenticationRequest {
    @NotBlank
    private String mobileNo;

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
