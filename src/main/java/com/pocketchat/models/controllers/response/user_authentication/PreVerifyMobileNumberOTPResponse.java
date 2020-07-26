package com.pocketchat.models.controllers.response.user_authentication;

import java.time.LocalDateTime;
public class PreVerifyMobileNumberOTPResponse {
    private LocalDateTime tokenExpiryTime;

    private String mobileNumber;

    private String emailAddress;

    private String secureKeyword;

    PreVerifyMobileNumberOTPResponse(LocalDateTime tokenExpiryTime, String mobileNumber, String emailAddress, String secureKeyword) {
        this.tokenExpiryTime = tokenExpiryTime;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.secureKeyword = secureKeyword;
    }

    public static PreVerifyMobileNumberOTPResponseBuilder builder() {
        return new PreVerifyMobileNumberOTPResponseBuilder();
    }


    public LocalDateTime getTokenExpiryTime() {
        return this.tokenExpiryTime;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getSecureKeyword() {
        return this.secureKeyword;
    }

    public void setTokenExpiryTime(LocalDateTime tokenExpiryTime) {
        this.tokenExpiryTime = tokenExpiryTime;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        final Object this$mobileNumber = this.getMobileNumber();
        final Object other$mobileNumber = other.getMobileNumber();
        if (this$mobileNumber == null ? other$mobileNumber != null : !this$mobileNumber.equals(other$mobileNumber))
            return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
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
        final Object $mobileNumber = this.getMobileNumber();
        result = result * PRIME + ($mobileNumber == null ? 43 : $mobileNumber.hashCode());
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        final Object $secureKeyword = this.getSecureKeyword();
        result = result * PRIME + ($secureKeyword == null ? 43 : $secureKeyword.hashCode());
        return result;
    }

    public String toString() {
        return "PreVerifyMobileNumberOTPResponse(tokenExpiryTime=" + this.getTokenExpiryTime() + ", mobileNumber=" + this.getMobileNumber() + ", emailAddress=" + this.getEmailAddress() + ", secureKeyword=" + this.getSecureKeyword() + ")";
    }

    public static class PreVerifyMobileNumberOTPResponseBuilder {
        private LocalDateTime tokenExpiryTime;
        private String mobileNumber;
        private String emailAddress;
        private String secureKeyword;

        PreVerifyMobileNumberOTPResponseBuilder() {
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder tokenExpiryTime(LocalDateTime tokenExpiryTime) {
            this.tokenExpiryTime = tokenExpiryTime;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder secureKeyword(String secureKeyword) {
            this.secureKeyword = secureKeyword;
            return this;
        }

        public PreVerifyMobileNumberOTPResponse build() {
            return new PreVerifyMobileNumberOTPResponse(tokenExpiryTime, mobileNumber, emailAddress, secureKeyword);
        }

        public String toString() {
            return "PreVerifyMobileNumberOTPResponse.PreVerifyMobileNumberOTPResponseBuilder(tokenExpiryTime=" + this.tokenExpiryTime + ", mobileNumber=" + this.mobileNumber + ", emailAddress=" + this.emailAddress + ", secureKeyword=" + this.secureKeyword + ")";
        }
    }
}
