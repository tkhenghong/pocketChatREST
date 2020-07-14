package com.pocketchat.models.controllers.request.user_authentication;

public class RegisterUsingMobileNumberRequest {
    private String mobileNo;

    private String countryCode; // Uses ISO-3166 Alpha-2 standard

    RegisterUsingMobileNumberRequest(String mobileNo, String countryCode) {
        this.mobileNo = mobileNo;
        this.countryCode = countryCode;
    }

    public static RegisterUsingMobileNumberRequestBuilder builder() {
        return new RegisterUsingMobileNumberRequestBuilder();
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RegisterUsingMobileNumberRequest))
            return false;
        final RegisterUsingMobileNumberRequest other = (RegisterUsingMobileNumberRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        final Object this$countryCode = this.getCountryCode();
        final Object other$countryCode = other.getCountryCode();
        if (this$countryCode == null ? other$countryCode != null : !this$countryCode.equals(other$countryCode))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RegisterUsingMobileNumberRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        final Object $countryCode = this.getCountryCode();
        result = result * PRIME + ($countryCode == null ? 43 : $countryCode.hashCode());
        return result;
    }

    public String toString() {
        return "RegisterUsingMobileNumberRequest(mobileNo=" + this.getMobileNo() + ", countryCode=" + this.getCountryCode() + ")";
    }

    public static class RegisterUsingMobileNumberRequestBuilder {
        private String mobileNo;
        private String countryCode;

        RegisterUsingMobileNumberRequestBuilder() {
        }

        public RegisterUsingMobileNumberRequest.RegisterUsingMobileNumberRequestBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public RegisterUsingMobileNumberRequest.RegisterUsingMobileNumberRequestBuilder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public RegisterUsingMobileNumberRequest build() {
            return new RegisterUsingMobileNumberRequest(mobileNo, countryCode);
        }

        public String toString() {
            return "RegisterUsingMobileNumberRequest.RegisterUsingMobileNumberRequestBuilder(mobileNo=" + this.mobileNo + ", countryCode=" + this.countryCode + ")";
        }
    }
}
