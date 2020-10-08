package com.pocketchat.db.models.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    private String emailAddress;

    @NotBlank
    private String countryCode;

    @NotBlank
    private String mobileNo;

    User(String id, @NotBlank String displayName, @NotBlank String realName, String emailAddress, @NotBlank String countryCode, @NotBlank String mobileNo) {
        this.id = id;
        this.displayName = displayName;
        this.realName = realName;
        this.emailAddress = emailAddress;
        this.countryCode = countryCode;
        this.mobileNo = mobileNo;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
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

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public @NotBlank String getCountryCode() {
        return this.countryCode;
    }

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setCountryCode(@NotBlank String countryCode) {
        this.countryCode = countryCode;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
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
        return other instanceof User;
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
        return "User(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", emailAddress=" + this.getEmailAddress() + ", countryCode=" + this.getCountryCode() + ", mobileNo=" + this.getMobileNo() + ")";
    }

    public static class UserBuilder {
        private String id;
        private @NotBlank String displayName;
        private @NotBlank String realName;
        private String emailAddress;
        private @NotBlank String countryCode;
        private @NotBlank String mobileNo;

        UserBuilder() {
        }

        public User.UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public User.UserBuilder displayName(@NotBlank String displayName) {
            this.displayName = displayName;
            return this;
        }

        public User.UserBuilder realName(@NotBlank String realName) {
            this.realName = realName;
            return this;
        }

        public User.UserBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public User.UserBuilder countryCode(@NotBlank String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public User.UserBuilder mobileNo(@NotBlank String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public User build() {
            return new User(id, displayName, realName, emailAddress, countryCode, mobileNo);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", displayName=" + this.displayName + ", realName=" + this.realName + ", emailAddress=" + this.emailAddress + ", countryCode=" + this.countryCode + ", mobileNo=" + this.mobileNo + ")";
        }
    }
}
