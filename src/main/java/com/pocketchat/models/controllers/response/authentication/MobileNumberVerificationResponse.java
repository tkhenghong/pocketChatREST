package com.pocketchat.models.controllers.response.authentication;

import org.joda.time.DateTime;

public class MobileNumberVerificationResponse {
    String mobileNo;
    DateTime otpExpirationTime;

    MobileNumberVerificationResponse(String mobileNo, DateTime otpExpirationTime) {
        this.mobileNo = mobileNo;
        this.otpExpirationTime = otpExpirationTime;
    }

    public static MobileNumberVerificationResponseBuilder builder() {
        return new MobileNumberVerificationResponseBuilder();
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public DateTime getOtpExpirationTime() {
        return this.otpExpirationTime;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setOtpExpirationTime(DateTime otpExpirationTime) {
        this.otpExpirationTime = otpExpirationTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MobileNumberVerificationResponse))
            return false;
        final MobileNumberVerificationResponse other = (MobileNumberVerificationResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        final Object this$otpExpirationTime = this.getOtpExpirationTime();
        final Object other$otpExpirationTime = other.getOtpExpirationTime();
        if (this$otpExpirationTime == null ? other$otpExpirationTime != null : !this$otpExpirationTime.equals(other$otpExpirationTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MobileNumberVerificationResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        final Object $otpExpirationTime = this.getOtpExpirationTime();
        result = result * PRIME + ($otpExpirationTime == null ? 43 : $otpExpirationTime.hashCode());
        return result;
    }

    public String toString() {
        return "MobileNumberVerificationResponse(mobileNo=" + this.getMobileNo() + ", otpExpirationTime=" + this.getOtpExpirationTime() + ")";
    }

    public static class MobileNumberVerificationResponseBuilder {
        private String mobileNo;
        private DateTime otpExpirationTime;

        MobileNumberVerificationResponseBuilder() {
        }

        public MobileNumberVerificationResponse.MobileNumberVerificationResponseBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public MobileNumberVerificationResponse.MobileNumberVerificationResponseBuilder otpExpirationTime(DateTime otpExpirationTime) {
            this.otpExpirationTime = otpExpirationTime;
            return this;
        }

        public MobileNumberVerificationResponse build() {
            return new MobileNumberVerificationResponse(mobileNo, otpExpirationTime);
        }

        public String toString() {
            return "MobileNumberVerificationResponse.MobileNumberVerificationResponseBuilder(mobileNo=" + this.mobileNo + ", otpExpirationTime=" + this.otpExpirationTime + ")";
        }
    }
}
