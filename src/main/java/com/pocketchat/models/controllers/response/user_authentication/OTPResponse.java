package com.pocketchat.models.controllers.response.user_authentication;

import org.joda.time.DateTime;

public class OTPResponse {
    private DateTime otpExpirationDateTime;

    OTPResponse(DateTime otpExpirationDateTime) {
        this.otpExpirationDateTime = otpExpirationDateTime;
    }

    public static OTPResponseBuilder builder() {
        return new OTPResponseBuilder();
    }

    public DateTime getOtpExpirationDateTime() {
        return this.otpExpirationDateTime;
    }

    public void setOtpExpirationDateTime(DateTime otpExpirationDateTime) {
        this.otpExpirationDateTime = otpExpirationDateTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OTPResponse)) return false;
        final OTPResponse other = (OTPResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$otpExpirationDateTime = this.getOtpExpirationDateTime();
        final Object other$otpExpirationDateTime = other.getOtpExpirationDateTime();
        if (this$otpExpirationDateTime == null ? other$otpExpirationDateTime != null : !this$otpExpirationDateTime.equals(other$otpExpirationDateTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OTPResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $otpExpirationDateTime = this.getOtpExpirationDateTime();
        result = result * PRIME + ($otpExpirationDateTime == null ? 43 : $otpExpirationDateTime.hashCode());
        return result;
    }

    public String toString() {
        return "OTPResponse(otpExpirationDateTime=" + this.getOtpExpirationDateTime() + ")";
    }

    public static class OTPResponseBuilder {
        private DateTime otpExpirationDateTime;

        OTPResponseBuilder() {
        }

        public OTPResponse.OTPResponseBuilder otpExpirationDateTime(DateTime otpExpirationDateTime) {
            this.otpExpirationDateTime = otpExpirationDateTime;
            return this;
        }

        public OTPResponse build() {
            return new OTPResponse(otpExpirationDateTime);
        }

        public String toString() {
            return "OTPResponse.OTPResponseBuilder(otpExpirationDateTime=" + this.otpExpirationDateTime + ")";
        }
    }
}
