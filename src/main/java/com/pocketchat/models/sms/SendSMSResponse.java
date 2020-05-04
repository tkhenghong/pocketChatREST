package com.pocketchat.models.sms;

public class SendSMSResponse {
    private String mobileNumber;
    private String message;

    SendSMSResponse(String mobileNumber, String message) {
        this.mobileNumber = mobileNumber;
        this.message = message;
    }

    public static SendSMSResponseBuilder builder() {
        return new SendSMSResponseBuilder();
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
        if (!(o instanceof SendSMSResponse)) return false;
        final SendSMSResponse other = (SendSMSResponse) o;
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
        return other instanceof SendSMSResponse;
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
        return "SendSMSResponse(mobileNumber=" + this.getMobileNumber() + ", message=" + this.getMessage() + ")";
    }

    public static class SendSMSResponseBuilder {
        private String mobileNumber;
        private String message;

        SendSMSResponseBuilder() {
        }

        public SendSMSResponse.SendSMSResponseBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public SendSMSResponse build() {
            return new SendSMSResponse(mobileNumber, message);
        }

        public String toString() {
            return "SendSMSResponse.SendSMSResponseBuilder(mobileNumber=" + this.mobileNumber + ", message=" + this.message + ")";
        }
    }
}
