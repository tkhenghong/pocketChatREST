package com.pocketchat.models.controllers.request.authentication;

public class EmailOTPVerificationRequest {
    private String emailAddress;
    private String otpNumber;

    EmailOTPVerificationRequest(String emailAddress, String otpNumber) {
        this.emailAddress = emailAddress;
        this.otpNumber = otpNumber;
    }

    public static EmailOTPVerificationRequestBuilder builder() {
        return new EmailOTPVerificationRequestBuilder();
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getOtpNumber() {
        return this.otpNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setOtpNumber(String otpNumber) {
        this.otpNumber = otpNumber;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EmailOTPVerificationRequest))
            return false;
        final EmailOTPVerificationRequest other = (EmailOTPVerificationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        final Object this$otpNumber = this.getOtpNumber();
        final Object other$otpNumber = other.getOtpNumber();
        if (this$otpNumber == null ? other$otpNumber != null : !this$otpNumber.equals(other$otpNumber)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EmailOTPVerificationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        final Object $otpNumber = this.getOtpNumber();
        result = result * PRIME + ($otpNumber == null ? 43 : $otpNumber.hashCode());
        return result;
    }

    public String toString() {
        return "EmailOTPVerificationRequest(emailAddress=" + this.getEmailAddress() + ", otpNumber=" + this.getOtpNumber() + ")";
    }

    public static class EmailOTPVerificationRequestBuilder {
        private String emailAddress;
        private String otpNumber;

        EmailOTPVerificationRequestBuilder() {
        }

        public EmailOTPVerificationRequest.EmailOTPVerificationRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public EmailOTPVerificationRequest.EmailOTPVerificationRequestBuilder otpNumber(String otpNumber) {
            this.otpNumber = otpNumber;
            return this;
        }

        public EmailOTPVerificationRequest build() {
            return new EmailOTPVerificationRequest(emailAddress, otpNumber);
        }

        public String toString() {
            return "EmailOTPVerificationRequest.EmailOTPVerificationRequestBuilder(emailAddress=" + this.emailAddress + ", otpNumber=" + this.otpNumber + ")";
        }
    }
}
