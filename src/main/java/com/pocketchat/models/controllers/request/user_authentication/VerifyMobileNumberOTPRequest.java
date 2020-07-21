package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

public class VerifyMobileNumberOTPRequest {
    @NotBlank
    private String mobileNo;

    @NotBlank
    private String otpNumber;

    @NotBlank
    private String secureKeyword;

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
    }

    public @NotBlank String getOtpNumber() {
        return this.otpNumber;
    }

    public @NotBlank String getSecureKeyword() {
        return this.secureKeyword;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setOtpNumber(@NotBlank String otpNumber) {
        this.otpNumber = otpNumber;
    }

    public void setSecureKeyword(@NotBlank String secureKeyword) {
        this.secureKeyword = secureKeyword;
    }
}
