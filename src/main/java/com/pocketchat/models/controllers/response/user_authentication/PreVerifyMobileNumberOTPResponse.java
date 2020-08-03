package com.pocketchat.models.controllers.response.user_authentication;

import java.time.LocalDateTime;

public class PreVerifyMobileNumberOTPResponse {
    private LocalDateTime tokenExpiryTime;

    private String maskedMobileNumber;

    private String maskedEmailAddress;

    private String secureKeyword;

    PreVerifyMobileNumberOTPResponse(LocalDateTime tokenExpiryTime, String maskedMobileNumber, String maskedEmailAddress, String secureKeyword) {
        this.tokenExpiryTime = tokenExpiryTime;
        this.maskedMobileNumber = maskedMobileNumber;
        this.maskedEmailAddress = maskedEmailAddress;
        this.secureKeyword = secureKeyword;
    }

    public static PreVerifyMobileNumberOTPResponseBuilder builder() {
        return new PreVerifyMobileNumberOTPResponseBuilder();
    }

    public LocalDateTime getTokenExpiryTime() {
        return this.tokenExpiryTime;
    }

    public String getMaskedMobileNumber() {
        return this.maskedMobileNumber;
    }

    public String getMaskedEmailAddress() {
        return this.maskedEmailAddress;
    }

    public String getSecureKeyword() {
        return this.secureKeyword;
    }

    public void setTokenExpiryTime(LocalDateTime tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }

    public void setMaskedMobileNumber(String maskedMobileNumber) {
        this.maskedMobileNumber = maskedMobileNumber;
    }

    public void setMaskedEmailAddress(String maskedEmailAddress) {
        this.maskedEmailAddress = maskedEmailAddress;
    }

    public void setSecureKeyword(String secureKeyword) {
        this.secureKeyword = secureKeyword;
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
        final Object this$maskedMobileNumber = this.getMaskedMobileNumber();
        final Object other$maskedMobileNumber = other.getMaskedMobileNumber();
        if (this$maskedMobileNumber == null ? other$maskedMobileNumber != null : !this$maskedMobileNumber.equals(other$maskedMobileNumber))
            return false;
        final Object this$maskedEmailAddress = this.getMaskedEmailAddress();
        final Object other$maskedEmailAddress = other.getMaskedEmailAddress();
        if (this$maskedEmailAddress == null ? other$maskedEmailAddress != null : !this$maskedEmailAddress.equals(other$maskedEmailAddress))
            return false;
        final Object this$secureKeyword = this.getSecureKeyword();
        final Object other$secureKeyword = other.getSecureKeyword();
        if (this$secureKeyword == null ? other$secureKeyword != null : !this$secureKeyword.equals(other$secureKeyword))
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
        final Object $maskedMobileNumber = this.getMaskedMobileNumber();
        result = result * PRIME + ($maskedMobileNumber == null ? 43 : $maskedMobileNumber.hashCode());
        final Object $maskedEmailAddress = this.getMaskedEmailAddress();
        result = result * PRIME + ($maskedEmailAddress == null ? 43 : $maskedEmailAddress.hashCode());
        final Object $secureKeyword = this.getSecureKeyword();
        result = result * PRIME + ($secureKeyword == null ? 43 : $secureKeyword.hashCode());
        return result;
    }

    public String toString() {
        return "PreVerifyMobileNumberOTPResponse(tokenExpiryTime=" + this.getTokenExpiryTime() + ", maskedMobileNumber=" + this.getMaskedMobileNumber() + ", maskedEmailAddress=" + this.getMaskedEmailAddress() + ", secureKeyword=" + this.getSecureKeyword() + ")";
    }

    public static class PreVerifyMobileNumberOTPResponseBuilder {
        private LocalDateTime tokenExpiryTime;
        private String maskedMobileNumber;
        private String maskedEmailAddress;
        private String secureKeyword;

        PreVerifyMobileNumberOTPResponseBuilder() {
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder tokenExpiryTime(LocalDateTime tokenExpiryTime) {
            this.tokenExpiryTime = tokenExpiryTime;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder maskedMobileNumber(String maskedMobileNumber) {
            this.maskedMobileNumber = maskedMobileNumber;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder maskedEmailAddress(String maskedEmailAddress) {
            this.maskedEmailAddress = maskedEmailAddress;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder secureKeyword(String secureKeyword) {
            this.secureKeyword = secureKeyword;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse build() {
            return new PreVerifyMobileNumberOTPResponse(tokenExpiryTime, maskedMobileNumber, maskedEmailAddress, secureKeyword);
        }

        public String toString() {
            return "PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder(tokenExpiryTime=" + this.tokenExpiryTime + ", maskedMobileNumber=" + this.maskedMobileNumber + ", maskedEmailAddress=" + this.maskedEmailAddress + ", secureKeyword=" + this.secureKeyword + ")";
        }
    }
}
