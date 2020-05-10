package com.pocketchat.models.controllers.request.authentication;

public class MobileNoAuthenticationRequest {
    private String mobileNo;

    MobileNoAuthenticationRequest(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public static MobileNoAuthenticationRequestBuilder builder() {
        return new MobileNoAuthenticationRequestBuilder();
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MobileNoAuthenticationRequest))
            return false;
        final MobileNoAuthenticationRequest other = (MobileNoAuthenticationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MobileNoAuthenticationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        return result;
    }

    public String toString() {
        return "MobileNoAuthenticationRequest(mobileNo=" + this.getMobileNo() + ")";
    }

    public static class MobileNoAuthenticationRequestBuilder {
        private String mobileNo;

        MobileNoAuthenticationRequestBuilder() {
        }

        public MobileNoAuthenticationRequest.MobileNoAuthenticationRequestBuilder mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public MobileNoAuthenticationRequest build() {
            return new MobileNoAuthenticationRequest(mobileNo);
        }

        public String toString() {
            return "MobileNoAuthenticationRequest.MobileNoAuthenticationRequestBuilder(mobileNo=" + this.mobileNo + ")";
        }
    }
}
