package com.pocketchat.db.models.user_contact;

import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Document(collection = "user_contact")
public class UserContact extends Auditable {

    @Id
    private String id;

    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    private String about;

    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<String> userIds;

    private String userId;

    @NotBlank
    private String mobileNo;

    @NotBlank
    private String countryCode;

    // Using Multimedia ID.
    @NotBlank
    private String profilePicture;

    UserContact(String id, @NotBlank String displayName, @NotBlank String realName, String about, @Valid @NotEmpty @Size(min = 1) List<String> userIds, String userId, @NotBlank String mobileNo, @NotBlank String countryCode, @NotBlank String profilePicture) {
        this.id = id;
        this.displayName = displayName;
        this.realName = realName;
        this.about = about;
        this.userIds = userIds;
        this.userId = userId;
        this.mobileNo = mobileNo;
        this.countryCode = countryCode;
        this.profilePicture = profilePicture;
    }

    public static UserContactBuilder builder() {
        return new UserContactBuilder();
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

    public String getAbout() {
        return this.about;
    }

    public @Valid @NotEmpty @Size(min = 1) List<String> getUserIds() {
        return this.userIds;
    }

    public String getUserId() {
        return this.userId;
    }

    public @NotBlank String getMobileNo() {
        return this.mobileNo;
    }

    public @NotBlank String getCountryCode() {
        return this.countryCode;
    }

    public @NotBlank String getProfilePicture() {
        return this.profilePicture;
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

    public void setAbout(String about) {
        this.about = about;
    }

    public void setUserIds(@Valid @NotEmpty @Size(min = 1) List<String> userIds) {
        this.userIds = userIds;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMobileNo(@NotBlank String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setCountryCode(@NotBlank String countryCode) {
        this.countryCode = countryCode;
    }

    public void setProfilePicture(@NotBlank String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserContact)) return false;
        final UserContact other = (UserContact) o;
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
        final Object this$about = this.getAbout();
        final Object other$about = other.getAbout();
        if (this$about == null ? other$about != null : !this$about.equals(other$about)) return false;
        final Object this$userIds = this.getUserIds();
        final Object other$userIds = other.getUserIds();
        if (this$userIds == null ? other$userIds != null : !this$userIds.equals(other$userIds)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$mobileNo = this.getMobileNo();
        final Object other$mobileNo = other.getMobileNo();
        if (this$mobileNo == null ? other$mobileNo != null : !this$mobileNo.equals(other$mobileNo)) return false;
        final Object this$countryCode = this.getCountryCode();
        final Object other$countryCode = other.getCountryCode();
        if (this$countryCode == null ? other$countryCode != null : !this$countryCode.equals(other$countryCode))
            return false;
        final Object this$profilePicture = this.getProfilePicture();
        final Object other$profilePicture = other.getProfilePicture();
        if (this$profilePicture == null ? other$profilePicture != null : !this$profilePicture.equals(other$profilePicture))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserContact;
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
        final Object $about = this.getAbout();
        result = result * PRIME + ($about == null ? 43 : $about.hashCode());
        final Object $userIds = this.getUserIds();
        result = result * PRIME + ($userIds == null ? 43 : $userIds.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $mobileNo = this.getMobileNo();
        result = result * PRIME + ($mobileNo == null ? 43 : $mobileNo.hashCode());
        final Object $countryCode = this.getCountryCode();
        result = result * PRIME + ($countryCode == null ? 43 : $countryCode.hashCode());
        final Object $profilePicture = this.getProfilePicture();
        result = result * PRIME + ($profilePicture == null ? 43 : $profilePicture.hashCode());
        return result;
    }

    public String toString() {
        return "UserContact(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", about=" + this.getAbout() + ", userIds=" + this.getUserIds() + ", userId=" + this.getUserId() + ", mobileNo=" + this.getMobileNo() + ", countryCode=" + this.getCountryCode() + ", profilePicture=" + this.getProfilePicture() + ")";
    }

    public static class UserContactBuilder {
        private String id;
        private @NotBlank String displayName;
        private @NotBlank String realName;
        private String about;
        private @Valid @NotEmpty @Size(min = 1) List<String> userIds;
        private String userId;
        private @NotBlank String mobileNo;
        private @NotBlank String countryCode;
        private @NotBlank String profilePicture;

        UserContactBuilder() {
        }

        public UserContact.UserContactBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserContact.UserContactBuilder displayName(@NotBlank String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserContact.UserContactBuilder realName(@NotBlank String realName) {
            this.realName = realName;
            return this;
        }

        public UserContact.UserContactBuilder about(String about) {
            this.about = about;
            return this;
        }

        public UserContact.UserContactBuilder userIds(@Valid @NotEmpty @Size(min = 1) List<String> userIds) {
            this.userIds = userIds;
            return this;
        }

        public UserContact.UserContactBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserContact.UserContactBuilder mobileNo(@NotBlank String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public UserContact.UserContactBuilder countryCode(@NotBlank String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public UserContact.UserContactBuilder profilePicture(@NotBlank String profilePicture) {
            this.profilePicture = profilePicture;
            return this;
        }

        public UserContact build() {
            return new UserContact(id, displayName, realName, about, userIds, userId, mobileNo, countryCode, profilePicture);
        }

        public String toString() {
            return "UserContact.UserContactBuilder(id=" + this.id + ", displayName=" + this.displayName + ", realName=" + this.realName + ", about=" + this.about + ", userIds=" + this.userIds + ", userId=" + this.userId + ", mobileNo=" + this.mobileNo + ", countryCode=" + this.countryCode + ", profilePicture=" + this.profilePicture + ")";
        }
    }
}
