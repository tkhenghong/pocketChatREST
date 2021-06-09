package com.pocketchat.models.controllers.response.user_authentication;

import java.time.LocalDateTime;

public class OTPResponse {
    private LocalDateTime otpExpirationDateTime;

    private String secureKeyword;

    OTPResponse(LocalDateTime otpExpirationDateTime, String secureKeyword) {
        this.otpExpirationDateTime = otpExpirationDateTime;
        this.secureKeyword = secureKeyword;
    }

    public static OTPResponseBuilder builder() {
        return new OTPResponseBuilder();
    }

    public LocalDateTime getOtpExpirationDateTime() {
        return this.otpExpirationDateTime;
    }

    public String getSecureKeyword() {
        return this.secureKeyword;
    }

    public void setOtpExpirationDateTime(LocalDateTime otpExpirationDateTime) {
        this.otpExpirationDateTime = otpExpirationDateTime;
    }

    public void setSecureKeyword(String secureKeyword) {
        this.secureKeyword = secureKeyword;
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
        final Object this$secureKeyword = this.getSecureKeyword();
        final Object other$secureKeyword = other.getSecureKeyword();
        if (this$secureKeyword == null ? other$secureKeyword != null : !this$secureKeyword.equals(other$secureKeyword))
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
        final Object $secureKeyword = this.getSecureKeyword();
        result = result * PRIME + ($secureKeyword == null ? 43 : $secureKeyword.hashCode());
        return result;
    }

    public String toString() {
        return "OTPResponse(otpExpirationDateTime=" + this.getOtpExpirationDateTime() + ", secureKeyword=" + this.getSecureKeyword() + ")";
    }

    public static class OTPResponseBuilder {
        private LocalDateTime otpExpirationDateTime;
        private String secureKeyword;

        OTPResponseBuilder() {
        }

        public OTPResponseBuilder otpExpirationDateTime(LocalDateTime otpExpirationDateTime) {
            this.otpExpirationDateTime = otpExpirationDateTime;
            return this;
        }

        public OTPResponseBuilder secureKeyword(String secureKeyword) {
            this.secureKeyword = secureKeyword;
            return this;
        }

        public OTPResponse build() {
            return new OTPResponse(otpExpirationDateTime, secureKeyword);
        }

        public String toString() {
            return "OTPResponse.OTPResponseBuilder(otpExpirationDateTime=" + this.otpExpirationDateTime + ", secureKeyword=" + this.secureKeyword + ")";
        }
    }
}
