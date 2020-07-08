package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

public class PreVerifyMobileNumberOTPRequest {
    @NotBlank
    private String mobileNumber;

    PreVerifyMobileNumberOTPRequest(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public static MobileNumberVerificationRequestBuilder builder() {
        return new MobileNumberVerificationRequestBuilder();
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String toString() {
        return "MobileNumberVerificationRequest(mobileNumber=" + this.getMobileNumber() + ")";
    }

    public static class MobileNumberVerificationRequestBuilder {
        private String mobileNumber;

        MobileNumberVerificationRequestBuilder() {
        }

        public PreVerifyMobileNumberOTPRequest.MobileNumberVerificationRequestBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public PreVerifyMobileNumberOTPRequest build() {
            return new PreVerifyMobileNumberOTPRequest(mobileNumber);
        }

        public String toString() {
            return "MobileNumberVerificationRequest.MobileNumberVerificationRequestBuilder(mobileNumber=" + this.mobileNumber + ")";
        }
    }
}
