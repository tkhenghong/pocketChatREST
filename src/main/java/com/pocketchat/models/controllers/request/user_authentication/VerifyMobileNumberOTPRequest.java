package com.pocketchat.models.controllers.request.user_authentication;

public class VerifyMobileNumberOTPRequest {
    private String mobileNo;
    private String otpNumber;
    private String secureKeyword;

    VerifyMobileNumberOTPRequest(String mobileNo, String otpNumber, String secureKeyword) {
        this.mobileNo = mobileNo;
        this.otpNumber = otpNumber;
        this.secureKeyword = secureKeyword;
    }

    public static VerifyMobileNumberOTPRequestBuilder builder() {
        return new VerifyMobileNumberOTPRequestBuilder();
    }


    public String getMobileNo() {
        return this.mobileNo;
    }

    public String getOtpNumber() {
        return this.otpNumber;
    }

    public String getSecureKeyword() {
        return this.secureKeyword;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setOtpNumber(String otpNumber) {
        this.otpNumber = otpNumber;
    }

    public void setSecureKeyword(String secureKeyword) {
        this.secureKeyword = secureKeyword;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof VerifyMobileNumberOTPRequest))
            return false;
        final VerifyMobileNumberOTPRequest other = (VerifyMobileNumberOTPRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        final Object this$otpNumber = this.getOtpNumber();
        final Object other$otpNumber = other.getOtpNumber();
        if (this$otpNumber == null ? other$otpNumber != null : !this$otpNumber.equals(other$otpNumber)) return false;
        final Object this$secureKeyword = this.getSecureKeyword();
        final Object other$secureKeyword = other.getSecureKeyword();
        if (this$secureKeyword == null ? other$secureKeyword != null : !this$secureKeyword.equals(other$secureKeyword))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof VerifyMobileNumberOTPRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        final Object $otpNumber = this.getOtpNumber();
        result = result * PRIME + ($otpNumber == null ? 43 : $otpNumber.hashCode());
        final Object $secureKeyword = this.getSecureKeyword();
        result = result * PRIME + ($secureKeyword == null ? 43 : $secureKeyword.hashCode());
        return result;
    }

    public String toString() {
        return "VerifyMobileNumberOTPRequest(mobileNo=" + this.getMobileNo() + ", otpNumber=" + this.getOtpNumber() + ", secureKeyword=" + this.getSecureKeyword() + ")";
    }

    public static class VerifyMobileNumberOTPRequestBuilder {
        private String mobileNo;
        private String otpNumber;
        private String secureKeyword;

        VerifyMobileNumberOTPRequestBuilder() {
        }

        public VerifyMobileNumberOTPRequest.VerifyMobileNumberOTPRequestBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public VerifyMobileNumberOTPRequest.VerifyMobileNumberOTPRequestBuilder otpNumber(String otpNumber) {
            this.otpNumber = otpNumber;
            return this;
        }

        public VerifyMobileNumberOTPRequest.VerifyMobileNumberOTPRequestBuilder secureKeyword(String secureKeyword) {
            this.secureKeyword = secureKeyword;
            return this;
        }

        public VerifyMobileNumberOTPRequest build() {
            return new VerifyMobileNumberOTPRequest(mobileNo, otpNumber, secureKeyword);
        }

        public String toString() {
            return "VerifyMobileNumberOTPRequest.VerifyMobileNumberOTPRequestBuilder(mobileNo=" + this.mobileNo + ", otpNumber=" + this.otpNumber + ", secureKeyword=" + this.secureKeyword + ")";
        }
    }
}
