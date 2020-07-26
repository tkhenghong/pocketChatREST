package com.pocketchat.models.otp;

import java.time.LocalDateTime;

public class OTP {
    private String userId;
    private Integer otp;
    private String keyword;
    private Integer length;
    private LocalDateTime otpExpirationDateTime;
    private Integer verifyAttempt;

    OTP(String userId, Integer otp, String keyword, Integer length, LocalDateTime otpExpirationDateTime, Integer verifyAttempt) {
        this.userId = userId;
        this.otp = otp;
        this.keyword = keyword;
        this.length = length;
        this.otpExpirationDateTime = otpExpirationDateTime;
        this.verifyAttempt = verifyAttempt;
    }

    public static OTPBuilder builder() {
        return new OTPBuilder();
    }

    public String getUserId() {
        return this.userId;
    }

    public Integer getOtp() {
        return this.otp;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public Integer getLength() {
        return this.length;
    }

    public LocalDateTime getOtpExpirationDateTime() {
        return this.otpExpirationDateTime;
    }

    public Integer getVerifyAttempt() {
        return this.verifyAttempt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setOtpExpirationDateTime(LocalDateTime otpExpirationDateTime) {
        this.otpExpirationDateTime = otpExpirationDateTime;
    }

    public void setVerifyAttempt(Integer verifyAttempt) {
        this.verifyAttempt = verifyAttempt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OTP)) return false;
        final OTP other = (OTP) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$otp = this.getOtp();
        final Object other$otp = other.getOtp();
        if (this$otp == null ? other$otp != null : !this$otp.equals(other$otp)) return false;
        final Object this$keyword = this.getKeyword();
        final Object other$keyword = other.getKeyword();
        if (this$keyword == null ? other$keyword != null : !this$keyword.equals(other$keyword)) return false;
        final Object this$length = this.getLength();
        final Object other$length = other.getLength();
        if (this$length == null ? other$length != null : !this$length.equals(other$length)) return false;
        final Object this$otpExpirationDateTime = this.getOtpExpirationDateTime();
        final Object other$otpExpirationDateTime = other.getOtpExpirationDateTime();
        if (this$otpExpirationDateTime == null ? other$otpExpirationDateTime != null : !this$otpExpirationDateTime.equals(other$otpExpirationDateTime))
            return false;
        final Object this$verifyAttempt = this.getVerifyAttempt();
        final Object other$verifyAttempt = other.getVerifyAttempt();
        if (this$verifyAttempt == null ? other$verifyAttempt != null : !this$verifyAttempt.equals(other$verifyAttempt))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OTP;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $otp = this.getOtp();
        result = result * PRIME + ($otp == null ? 43 : $otp.hashCode());
        final Object $keyword = this.getKeyword();
        result = result * PRIME + ($keyword == null ? 43 : $keyword.hashCode());
        final Object $length = this.getLength();
        result = result * PRIME + ($length == null ? 43 : $length.hashCode());
        final Object $otpExpirationDateTime = this.getOtpExpirationDateTime();
        result = result * PRIME + ($otpExpirationDateTime == null ? 43 : $otpExpirationDateTime.hashCode());
        final Object $verifyAttempt = this.getVerifyAttempt();
        result = result * PRIME + ($verifyAttempt == null ? 43 : $verifyAttempt.hashCode());
        return result;
    }

    public String toString() {
        return "OTP(userId=" + this.getUserId() + ", otp=" + this.getOtp() + ", keyword=" + this.getKeyword() + ", length=" + this.getLength() + ", otpExpirationDateTime=" + this.getOtpExpirationDateTime() + ", verifyAttempt=" + this.getVerifyAttempt() + ")";
    }

    public static class OTPBuilder {
        private String userId;
        private Integer otp;
        private String keyword;
        private Integer length;
        private LocalDateTime otpExpirationDateTime;
        private Integer verifyAttempt;

        OTPBuilder() {
        }

        public OTP.OTPBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public OTP.OTPBuilder otp(Integer otp) {
            this.otp = otp;
            return this;
        }

        public OTP.OTPBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public OTP.OTPBuilder length(Integer length) {
            this.length = length;
            return this;
        }

        public OTP.OTPBuilder otpExpirationDateTime(LocalDateTime otpExpirationDateTime) {
            this.otpExpirationDateTime = otpExpirationDateTime;
            return this;
        }

        public OTP.OTPBuilder verifyAttempt(Integer verifyAttempt) {
            this.verifyAttempt = verifyAttempt;
            return this;
        }

        public OTP build() {
            return new OTP(userId, otp, keyword, length, otpExpirationDateTime, verifyAttempt);
        }

        public String toString() {
            return "OTP.OTPBuilder(userId=" + this.userId + ", otp=" + this.otp + ", keyword=" + this.keyword + ", length=" + this.length + ", otpExpirationDateTime=" + this.otpExpirationDateTime + ", verifyAttempt=" + this.verifyAttempt + ")";
        }
    }
}
