package com.pocketchat.models.sms;

import javax.validation.constraints.NotBlank;

public class SendSMSRequest {
    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String message;

    SendSMSRequest(String mobileNumber, String message) {
        this.mobileNumber = mobileNumber;
        this.message = message;
    }

    public static SendSMSRequestBuilder builder() {
        return new SendSMSRequestBuilder();
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SendSMSRequest)) return false;
        final SendSMSRequest other = (SendSMSRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mobileNumber = this.getMobileNumber();
        final Object other$mobileNumber = other.getMobileNumber();
        if (this$mobileNumber == null ? other$mobileNumber != null : !this$mobileNumber.equals(other$mobileNumber))
            return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SendSMSRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mobileNumber = this.getMobileNumber();
        result = result * PRIME + ($mobileNumber == null ? 43 : $mobileNumber.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "SendSMSRequest(mobileNumber=" + this.getMobileNumber() + ", message=" + this.getMessage() + ")";
    }

    public static class SendSMSRequestBuilder {
        private String mobileNumber;
        private String message;

        SendSMSRequestBuilder() {
        }

        public SendSMSRequest.SendSMSRequestBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public SendSMSRequest.SendSMSRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public SendSMSRequest build() {
            return new SendSMSRequest(mobileNumber, message);
        }

        public String toString() {
            return "SendSMSRequest.SendSMSRequestBuilder(mobileNumber=" + this.mobileNumber + ", message=" + this.message + ")";
        }
    }
}
