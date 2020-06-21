package com.pocketchat.models.controllers.request.user;

import javax.validation.constraints.NotBlank;

public class UpdateUserRequest {

    private String id;

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String googleAccountId;

    private String emailAddress;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String effectivePhoneNumber;

    UpdateUserRequest(String id, @NotBlank String displayName, @NotBlank String realName, @NotBlank String mobileNo, @NotBlank String googleAccountId, String emailAddress, @NotBlank String countryCode, @NotBlank String effectivePhoneNumber) {
        this.id = id;
        this.displayName = displayName;
        this.realName = realName;
        this.mobileNo = mobileNo;
        this.googleAccountId = googleAccountId;
        this.emailAddress = emailAddress;
        this.countryCode = countryCode;
        this.effectivePhoneNumber = effectivePhoneNumber;
    }

    public static UpdateUserRequestBuilder builder() {
        return new UpdateUserRequestBuilder();
    }

    public String getId() {
        return this.id;
    }

    public @NotBlank String getDisplayName() {
        return this.displayName;
    }

    public @NotBlank String getRealName() {
        return this.realName;
    }

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
    }

    public @NotBlank String getGoogleAccountId() {
        return this.googleAccountId;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public @NotBlank String getCountryCode() {
        return this.countryCode;
    }

    public @NotBlank String getEffectivePhoneNumber() {
        return this.effectivePhoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(@NotBlank String displayName) {
        this.displayName = displayName;
    }

    public void setRealName(@NotBlank String realName) {
        this.realName = realName;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setGoogleAccountId(@NotBlank String googleAccountId) {
        this.googleAccountId = googleAccountId;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCountryCode(@NotBlank String countryCode) {
        this.countryCode = countryCode;
    }

    public void setEffectivePhoneNumber(@NotBlank String effectivePhoneNumber) {
        this.effectivePhoneNumber = effectivePhoneNumber;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateUserRequest)) return false;
        final UpdateUserRequest other = (UpdateUserRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$displayName = this.getDisplayName();
        final Object other$displayName = other.getDisplayName();
        if (this$displayName == null ? other$displayName != null : !this$displayName.equals(other$displayName))
            return false;
        final Object this$realName = this.getRealName();
        final Object other$realName = other.getRealName();
        if (this$realName == null ? other$realName != null : !this$realName.equals(other$realName)) return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        final Object this$googleAccountId = this.getGoogleAccountId();
        final Object other$googleAccountId = other.getGoogleAccountId();
        if (this$googleAccountId == null ? other$googleAccountId != null : !this$googleAccountId.equals(other$googleAccountId))
            return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        final Object this$countryCode = this.getCountryCode();
        final Object other$countryCode = other.getCountryCode();
        if (this$countryCode == null ? other$countryCode != null : !this$countryCode.equals(other$countryCode))
            return false;
        final Object this$effectivePhoneNumber = this.getEffectivePhoneNumber();
        final Object other$effectivePhoneNumber = other.getEffectivePhoneNumber();
        if (this$effectivePhoneNumber == null ? other$effectivePhoneNumber != null : !this$effectivePhoneNumber.equals(other$effectivePhoneNumber))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UpdateUserRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $displayName = this.getDisplayName();
        result = result * PRIME + ($displayName == null ? 43 : $displayName.hashCode());
        final Object $realName = this.getRealName();
        result = result * PRIME + ($realName == null ? 43 : $realName.hashCode());
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        final Object $googleAccountId = this.getGoogleAccountId();
        result = result * PRIME + ($googleAccountId == null ? 43 : $googleAccountId.hashCode());
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        final Object $countryCode = this.getCountryCode();
        result = result * PRIME + ($countryCode == null ? 43 : $countryCode.hashCode());
        final Object $effectivePhoneNumber = this.getEffectivePhoneNumber();
        result = result * PRIME + ($effectivePhoneNumber == null ? 43 : $effectivePhoneNumber.hashCode());
        return result;
    }

    public String toString() {
        return "UpdateUserRequest(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", mobileNo=" + this.getMobileNo() + ", googleAccountId=" + this.getGoogleAccountId() + ", emailAddress=" + this.getEmailAddress() + ", countryCode=" + this.getCountryCode() + ", effectivePhoneNumber=" + this.getEffectivePhoneNumber() + ")";
    }

    public static class UpdateUserRequestBuilder {
        private String id;
        private @NotBlank String displayName;
        private @NotBlank String realName;
        private @NotBlank String mobileNo;
        private @NotBlank String googleAccountId;
        private String emailAddress;
        private @NotBlank String countryCode;
        private @NotBlank String effectivePhoneNumber;

        UpdateUserRequestBuilder() {
        }

        public UpdateUserRequest.UpdateUserRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder displayName(@NotBlank String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder realName(@NotBlank String realName) {
            this.realName = realName;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder mobileNo(@NotBlank String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder googleAccountId(@NotBlank String googleAccountId) {
            this.googleAccountId = googleAccountId;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder countryCode(@NotBlank String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public UpdateUserRequest.UpdateUserRequestBuilder effectivePhoneNumber(@NotBlank String effectivePhoneNumber) {
            this.effectivePhoneNumber = effectivePhoneNumber;
            return this;
        }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(id, displayName, realName, mobileNo, googleAccountId, emailAddress, countryCode, effectivePhoneNumber);
        }

        public String toString() {
            return "UpdateUserRequest.UpdateUserRequestBuilder(id=" + this.id + ", displayName=" + this.displayName + ", realName=" + this.realName + ", mobileNo=" + this.mobileNo + ", googleAccountId=" + this.googleAccountId + ", emailAddress=" + this.emailAddress + ", countryCode=" + this.countryCode + ", effectivePhoneNumber=" + this.effectivePhoneNumber + ")";
        }
    }
}
