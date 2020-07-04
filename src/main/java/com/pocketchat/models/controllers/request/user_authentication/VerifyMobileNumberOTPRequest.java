package com.pocketchat.models.controllers.request.user_authentication;

public class MobileNumberOTPVerificationRequest {
    private String mobileNo;
    private String otpNumber;

    MobileNumberOTPVerificationRequest(String mobileNo, String otpNumber) {
        this.mobileNo = mobileNo;
        this.otpNumber = otpNumber;
    }

    public static MobileNumberOTPVerificationRequestBuilder builder() {
        return new MobileNumberOTPVerificationRequestBuilder();
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public String getOtpNumber() {
        return this.otpNumber;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setOtpNumber(String otpNumber) {
        this.otpNumber = otpNumber;
    }

    public String toString() {
        return "MobileNumberOTPVerificationRequest(mobileNo=" + this.getMobileNo() + ", otpNumber=" + this.getOtpNumber() + ")";
    }

    public static class MobileNumberOTPVerificationRequestBuilder {
        private String mobileNo;
        private String otpNumber;

        MobileNumberOTPVerificationRequestBuilder() {
        }

        public MobileNumberOTPVerificationRequest.MobileNumberOTPVerificationRequestBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public MobileNumberOTPVerificationRequest.MobileNumberOTPVerificationRequestBuilder otpNumber(String otpNumber) {
            this.otpNumber = otpNumber;
            return this;
        }

        public MobileNumberOTPVerificationRequest build() {
            return new MobileNumberOTPVerificationRequest(mobileNo, otpNumber);
        }

        public String toString() {
            return "MobileNumberOTPVerificationRequest.MobileNumberOTPVerificationRequestBuilder(mobileNo=" + this.mobileNo + ", otpNumber=" + this.otpNumber + ")";
        }
    }
}
