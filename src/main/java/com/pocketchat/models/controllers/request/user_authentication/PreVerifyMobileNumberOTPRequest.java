package com.pocketchat.models.controllers.request.user_authentication;

public class RequestVerifyMobileNumberRequest {
    private String mobileNumber;

    RequestVerifyMobileNumberRequest(String mobileNumber) {
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

        public RequestVerifyMobileNumberRequest.MobileNumberVerificationRequestBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public RequestVerifyMobileNumberRequest build() {
            return new RequestVerifyMobileNumberRequest(mobileNumber);
        }

        public String toString() {
            return "MobileNumberVerificationRequest.MobileNumberVerificationRequestBuilder(mobileNumber=" + this.mobileNumber + ")";
        }
    }
}
