package com.pocketchat.models.controllers.response.user_authentication;

import org.joda.time.DateTime;

public class PreVerifyMobileNumberOTPResponse {
    private DateTime tokenExpiryTime;

    private String mobileNumber;

    private String emailAddress;

    PreVerifyMobileNumberOTPResponse(DateTime tokenExpiryTime, String mobileNumber, String emailAddress) {
        this.tokenExpiryTime = tokenExpiryTime;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
    }

    public static RequestVerifyMobileNumberResponseBuilder builder() {
        return new RequestVerifyMobileNumberResponseBuilder();
    }

    public DateTime getTokenExpiryTime() {
        return this.tokenExpiryTime;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setTokenExpiryTime(DateTime tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PreVerifyMobileNumberOTPResponse))
            return false;
        final PreVerifyMobileNumberOTPResponse other = (PreVerifyMobileNumberOTPResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$tokenExpiryTime = this.getTokenExpiryTime();
        final Object other$tokenExpiryTime = other.getTokenExpiryTime();
        if (this$tokenExpiryTime == null ? other$tokenExpiryTime != null : !this$tokenExpiryTime.equals(other$tokenExpiryTime))
            return false;
        final Object this$mobileNumber = this.getMobileNumber();
        final Object other$mobileNumber = other.getMobileNumber();
        if (this$mobileNumber == null ? other$mobileNumber != null : !this$mobileNumber.equals(other$mobileNumber))
            return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PreVerifyMobileNumberOTPResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $tokenExpiryTime = this.getTokenExpiryTime();
        result = result * PRIME + ($tokenExpiryTime == null ? 43 : $tokenExpiryTime.hashCode());
        final Object $mobileNumber = this.getMobileNumber();
        result = result * PRIME + ($mobileNumber == null ? 43 : $mobileNumber.hashCode());
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        return result;
    }

    public String toString() {
        return "RequestVerifyMobileNumberResponse(tokenExpiryTime=" + this.getTokenExpiryTime() + ", mobileNumber=" + this.getMobileNumber() + ", emailAddress=" + this.getEmailAddress() + ")";
    }

    public static class RequestVerifyMobileNumberResponseBuilder {
        private DateTime tokenExpiryTime;
        private String mobileNumber;
        private String emailAddress;

        RequestVerifyMobileNumberResponseBuilder() {
        }

        public PreVerifyMobileNumberOTPResponse.RequestVerifyMobileNumberResponseBuilder tokenExpiryTime(DateTime tokenExpiryTime) {
            this.tokenExpiryTime = tokenExpiryTime;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.RequestVerifyMobileNumberResponseBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.RequestVerifyMobileNumberResponseBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse build() {
            return new PreVerifyMobileNumberOTPResponse(tokenExpiryTime, mobileNumber, emailAddress);
        }

        public String toString() {
            return "RequestVerifyMobileNumberResponse.RequestVerifyMobileNumberResponseBuilder(tokenExpiryTime=" + this.tokenExpiryTime + ", mobileNumber=" + this.mobileNumber + ", emailAddress=" + this.emailAddress + ")";
        }
    }
}
