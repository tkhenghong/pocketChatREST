package com.pocketchat.models.controllers.request.user;

import javax.validation.constraints.NotBlank;

public class UpdateUserRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    @NotBlank
    private String mobileNo;

    private String emailAddress;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String effectivePhoneNumber;

    protected UpdateUserRequest(UpdateUserRequestBuilder<?, ?> b) {
        this.id = b.id;
        this.displayName = b.displayName;
        this.realName = b.realName;
        this.mobileNo = b.mobileNo;
        this.emailAddress = b.emailAddress;
        this.countryCode = b.countryCode;
        this.effectivePhoneNumber = b.effectivePhoneNumber;
    }

    public static UpdateUserRequestBuilder<?, ?> builder() {
        return new UpdateUserRequestBuilderImpl();
    }

    public @NotBlank String getId() {
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

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public @NotBlank String getCountryCode() {
        return this.countryCode;
    }

    public @NotBlank String getEffectivePhoneNumber() {
        return this.effectivePhoneNumber;
    }

    public void setId(@NotBlank String id) {
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
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        final Object $countryCode = this.getCountryCode();
        result = result * PRIME + ($countryCode == null ? 43 : $countryCode.hashCode());
        final Object $effectivePhoneNumber = this.getEffectivePhoneNumber();
        result = result * PRIME + ($effectivePhoneNumber == null ? 43 : $effectivePhoneNumber.hashCode());
        return result;
    }

    public String toString() {
        return "UpdateUserRequest(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", mobileNo=" + this.getMobileNo() + ", emailAddress=" + this.getEmailAddress() + ", countryCode=" + this.getCountryCode() + ", effectivePhoneNumber=" + this.getEffectivePhoneNumber() + ")";
    }

    public static abstract class UpdateUserRequestBuilder<C extends UpdateUserRequest, B extends UpdateUserRequest.UpdateUserRequestBuilder<C, B>> {
        private @NotBlank String id;
        private @NotBlank String displayName;
        private @NotBlank String realName;
        private @NotBlank String mobileNo;
        private String emailAddress;
        private @NotBlank String countryCode;
        private @NotBlank String effectivePhoneNumber;

        public B id(@NotBlank String id) {
            this.id = id;
            return self();
        }

        public B displayName(@NotBlank String displayName) {
            this.displayName = displayName;
            return self();
        }

        public B realName(@NotBlank String realName) {
            this.realName = realName;
            return self();
        }

        public B mobileNo(@NotBlank String mobileNo) {
            this.mobileNo = mobileNo;
            return self();
        }

        public B emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return self();
        }

        public B countryCode(@NotBlank String countryCode) {
            this.countryCode = countryCode;
            return self();
        }

        public B effectivePhoneNumber(@NotBlank String effectivePhoneNumber) {
            this.effectivePhoneNumber = effectivePhoneNumber;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "UpdateUserRequest.UpdateUserRequestBuilder(id=" + this.id + ", displayName=" + this.displayName + ", realName=" + this.realName + ", mobileNo=" + this.mobileNo + ", emailAddress=" + this.emailAddress + ", countryCode=" + this.countryCode + ", effectivePhoneNumber=" + this.effectivePhoneNumber + ")";
        }
    }

    private static final class UpdateUserRequestBuilderImpl extends UpdateUserRequestBuilder<UpdateUserRequest, UpdateUserRequestBuilderImpl> {
        private UpdateUserRequestBuilderImpl() {
        }

        protected UpdateUserRequest.UpdateUserRequestBuilderImpl self() {
            return this;
        }

        public UpdateUserRequest build() {
            return new UpdateUserRequest(this);
        }
    }
}
