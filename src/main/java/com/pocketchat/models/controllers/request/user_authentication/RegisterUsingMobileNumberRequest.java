package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

public class RegisterUsingMobileNumberRequest {
    @NotBlank
    private String mobileNo;

    @NotBlank
    private String countryCode; // Uses ISO-3166 Alpha-2 standard

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
    }

    public @NotBlank String getCountryCode() {
        return this.countryCode;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setCountryCode(@NotBlank String countryCode) {
        this.countryCode = countryCode;
    }
}
