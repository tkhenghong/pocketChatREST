package com.pocketchat.models.controllers.request.user;

import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String mobileNo;

    private String emailAddress;

    CreateUserRequest(@NotBlank String displayName, @NotBlank String realName, @NotBlank String countryCode, @NotBlank String mobileNo, String emailAddress) {
        this.displayName = displayName;
        this.realName = realName;
        this.countryCode = countryCode;
        this.mobileNo = mobileNo;
        this.emailAddress = emailAddress;
    }

    public static CreateUserRequestBuilder builder() {
        return new CreateUserRequestBuilder();
    }

    public @NotBlank String getDisplayName() {
        return this.displayName;
    }

    public @NotBlank String getRealName() {
        return this.realName;
    }

    public @NotBlank String getCountryCode() {
        return this.countryCode;
    }

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setDisplayName(@NotBlank String displayName) {
        this.displayName = displayName;
    }

    public void setRealName(@NotBlank String realName) {
        this.realName = realName;
    }

    public void setCountryCode(@NotBlank String countryCode) {
        this.countryCode = countryCode;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateUserRequest)) return false;
        final CreateUserRequest other = (CreateUserRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$displayName = this.getDisplayName();
        final Object other$displayName = other.getDisplayName();
        if (this$displayName == null ? other$displayName != null : !this$displayName.equals(other$displayName))
            return false;
        final Object this$realName = this.getRealName();
        final Object other$realName = other.getRealName();
        if (this$realName == null ? other$realName != null : !this$realName.equals(other$realName)) return false;
        final Object this$countryCode = this.getCountryCode();
        final Object other$countryCode = other.getCountryCode();
        if (this$countryCode == null ? other$countryCode != null : !this$countryCode.equals(other$countryCode))
            return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateUserRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $displayName = this.getDisplayName();
        result = result * PRIME + ($displayName == null ? 43 : $displayName.hashCode());
        final Object $realName = this.getRealName();
        result = result * PRIME + ($realName == null ? 43 : $realName.hashCode());
        final Object $countryCode = this.getCountryCode();
        result = result * PRIME + ($countryCode == null ? 43 : $countryCode.hashCode());
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        return result;
    }

    public String toString() {
        return "CreateUserRequest(displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", countryCode=" + this.getCountryCode() + ", mobileNo=" + this.getMobileNo() + ", emailAddress=" + this.getEmailAddress() + ")";
    }

    public static class CreateUserRequestBuilder {
        private @NotBlank String displayName;
        private @NotBlank String realName;
        private @NotBlank String countryCode;
        private @NotBlank String mobileNo;
        private String emailAddress;

        CreateUserRequestBuilder() {
        }

        public CreateUserRequest.CreateUserRequestBuilder displayName(@NotBlank String displayName) {
            this.displayName = displayName;
            return this;
        }

        public CreateUserRequest.CreateUserRequestBuilder realName(@NotBlank String realName) {
            this.realName = realName;
            return this;
        }

        public CreateUserRequest.CreateUserRequestBuilder countryCode(@NotBlank String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CreateUserRequest.CreateUserRequestBuilder mobileNo(@NotBlank String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public CreateUserRequest.CreateUserRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(displayName, realName, countryCode, mobileNo, emailAddress);
        }

        public String toString() {
            return "CreateUserRequest.CreateUserRequestBuilder(displayName=" + this.displayName + ", realName=" + this.realName + ", countryCode=" + this.countryCode + ", mobileNo=" + this.mobileNo + ", emailAddress=" + this.emailAddress + ")";
        }
    }
}
