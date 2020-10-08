package com.pocketchat.models.controllers.response.user;

public class UserResponse {

    private String id;

    private String displayName;

    private String realName;

    private String emailAddress;

    private String countryCode;

    private String mobileNo;

    protected UserResponse(UserResponseBuilder<?, ?> b) {
        this.id = b.id;
        this.displayName = b.displayName;
        this.realName = b.realName;
        this.emailAddress = b.emailAddress;
        this.countryCode = b.countryCode;
        this.mobileNo = b.mobileNo;
    }

    public static UserResponseBuilder<?, ?> builder() {
        return new UserResponseBuilderImpl();
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getRealName() {
        return this.realName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserResponse)) return false;
        final UserResponse other = (UserResponse) o;
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
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        final Object this$countryCode = this.getCountryCode();
        final Object other$countryCode = other.getCountryCode();
        if (this$countryCode == null ? other$countryCode != null : !this$countryCode.equals(other$countryCode))
            return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserResponse;
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
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        final Object $countryCode = this.getCountryCode();
        result = result * PRIME + ($countryCode == null ? 43 : $countryCode.hashCode());
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        return result;
    }

    public String toString() {
        return "UserResponse(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", emailAddress=" + this.getEmailAddress() + ", countryCode=" + this.getCountryCode() + ", mobileNo=" + this.getMobileNo() + ")";
    }

    public static abstract class UserResponseBuilder<C extends UserResponse, B extends UserResponse.UserResponseBuilder<C, B>> {
        private String id;
        private String displayName;
        private String realName;
        private String emailAddress;
        private String countryCode;
        private String mobileNo;

        public B id(String id) {
            this.id = id;
            return self();
        }

        public B displayName(String displayName) {
            this.displayName = displayName;
            return self();
        }

        public B realName(String realName) {
            this.realName = realName;
            return self();
        }

        public B emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return self();
        }

        public B countryCode(String countryCode) {
            this.countryCode = countryCode;
            return self();
        }

        public B mobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
            return self();
        }

        protected abstract B self();

        public abstract C build();

        public String toString() {
            return "UserResponse.UserResponseBuilder(id=" + this.id + ", displayName=" + this.displayName + ", realName=" + this.realName + ", emailAddress=" + this.emailAddress + ", countryCode=" + this.countryCode + ", mobileNo=" + this.mobileNo + ")";
        }
    }

    private static final class UserResponseBuilderImpl extends UserResponseBuilder<UserResponse, UserResponseBuilderImpl> {
        private UserResponseBuilderImpl() {
        }

        protected UserResponse.UserResponseBuilderImpl self() {
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
}
