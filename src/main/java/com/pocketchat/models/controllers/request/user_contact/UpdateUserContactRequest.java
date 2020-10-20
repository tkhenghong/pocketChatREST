package com.pocketchat.models.controllers.request.user_contact;

import javax.validation.constraints.NotBlank;

public class UpdateUserContactRequest {
    @NotBlank
    private String displayName;

    @NotBlank
    private String realName;

    private String about;

    private String profilePhoto;

    public UpdateUserContactRequest() {
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

    public String getProfilePhoto() {
        return this.profilePhoto;
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

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateUserContactRequest))
            return false;
        final UpdateUserContactRequest other = (UpdateUserContactRequest) o;
        if (!other.canEqual((Object) this)) return false;
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
        final Object this$profilePhoto = this.getProfilePhoto();
        final Object other$profilePhoto = other.getProfilePhoto();
        if (this$profilePhoto == null ? other$profilePhoto != null : !this$profilePhoto.equals(other$profilePhoto))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UpdateUserContactRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $displayName = this.getDisplayName();
        result = result * PRIME + ($displayName == null ? 43 : $displayName.hashCode());
        final Object $realName = this.getRealName();
        result = result * PRIME + ($realName == null ? 43 : $realName.hashCode());
        final Object $about = this.getAbout();
        result = result * PRIME + ($about == null ? 43 : $about.hashCode());
        final Object $profilePhoto = this.getProfilePhoto();
        result = result * PRIME + ($profilePhoto == null ? 43 : $profilePhoto.hashCode());
        return result;
    }

    public String toString() {
        return "UpdateUserContactRequest(displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", about=" + this.getAbout() + ", profilePhoto=" + this.getProfilePhoto() + ")";
    }
}
