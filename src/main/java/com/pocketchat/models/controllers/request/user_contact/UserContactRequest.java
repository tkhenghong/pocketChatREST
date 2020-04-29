package com.pocketchat.models.controllers.request.user_contact;

import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class UserContactRequest {

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

    private DateTime lastSeenDate;

    private boolean block;

    private String multimediaId;

    UserContactRequest(String id, @NotBlank String displayName, @NotBlank String realName, String about, @Valid @NotEmpty @Size(min = 1) List<String> userIds, String userId, @NotBlank String mobileNo, DateTime lastSeenDate, boolean block, String multimediaId) {
        this.id = id;
        this.displayName = displayName;
        this.realName = realName;
        this.about = about;
        this.userIds = userIds;
        this.userId = userId;
        this.mobileNo = mobileNo;
        this.lastSeenDate = lastSeenDate;
        this.block = block;
        this.multimediaId = multimediaId;
    }

    public static UserContactRequestBuilder builder() {
        return new UserContactRequestBuilder();
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

    public DateTime getLastSeenDate() {
        return this.lastSeenDate;
    }

    public boolean isBlock() {
        return this.block;
    }

    public String getMultimediaId() {
        return this.multimediaId;
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

    public void setLastSeenDate(DateTime lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setMultimediaId(String multimediaId) {
        this.multimediaId = multimediaId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserContactRequest)) return false;
        final UserContactRequest other = (UserContactRequest) o;
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
        final Object this$lastSeenDate = this.getLastSeenDate();
        final Object other$lastSeenDate = other.getLastSeenDate();
        if (this$lastSeenDate == null ? other$lastSeenDate != null : !this$lastSeenDate.equals(other$lastSeenDate))
            return false;
        if (this.isBlock() != other.isBlock()) return false;
        final Object this$multimediaId = this.getMultimediaId();
        final Object other$multimediaId = other.getMultimediaId();
        if (this$multimediaId == null ? other$multimediaId != null : !this$multimediaId.equals(other$multimediaId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserContactRequest;
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
        final Object $lastSeenDate = this.getLastSeenDate();
        result = result * PRIME + ($lastSeenDate == null ? 43 : $lastSeenDate.hashCode());
        result = result * PRIME + (this.isBlock() ? 79 : 97);
        final Object $multimediaId = this.getMultimediaId();
        result = result * PRIME + ($multimediaId == null ? 43 : $multimediaId.hashCode());
        return result;
    }

    public String toString() {
        return "UserContactRequest(id=" + this.getId() + ", displayName=" + this.getDisplayName() + ", realName=" + this.getRealName() + ", about=" + this.getAbout() + ", userIds=" + this.getUserIds() + ", userId=" + this.getUserId() + ", mobileNo=" + this.getMobileNo() + ", lastSeenDate=" + this.getLastSeenDate() + ", block=" + this.isBlock() + ", multimediaId=" + this.getMultimediaId() + ")";
    }

    public static class UserContactRequestBuilder {
        private String id;
        private @NotBlank String displayName;
        private @NotBlank String realName;
        private String about;
        private @Valid @NotEmpty @Size(min = 1) List<String> userIds;
        private String userId;
        private @NotBlank String mobileNo;
        private DateTime lastSeenDate;
        private boolean block;
        private String multimediaId;

        UserContactRequestBuilder() {
        }

        public UserContactRequest.UserContactRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder displayName(@NotBlank String displayName) {
            this.displayName = displayName;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder realName(@NotBlank String realName) {
            this.realName = realName;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder about(String about) {
            this.about = about;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder userIds(@Valid @NotEmpty @Size(min = 1) List<String> userIds) {
            this.userIds = userIds;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder mobileNo(@NotBlank String mobileNo) {
            this.mobileNo = mobileNo;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder lastSeenDate(DateTime lastSeenDate) {
            this.lastSeenDate = lastSeenDate;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder block(boolean block) {
            this.block = block;
            return this;
        }

        public UserContactRequest.UserContactRequestBuilder multimediaId(String multimediaId) {
            this.multimediaId = multimediaId;
            return this;
        }

        public UserContactRequest build() {
            return new UserContactRequest(id, displayName, realName, about, userIds, userId, mobileNo, lastSeenDate, block, multimediaId);
        }

        public String toString() {
            return "UserContactRequest.UserContactRequestBuilder(id=" + this.id + ", displayName=" + this.displayName + ", realName=" + this.realName + ", about=" + this.about + ", userIds=" + this.userIds + ", userId=" + this.userId + ", mobileNo=" + this.mobileNo + ", lastSeenDate=" + this.lastSeenDate + ", block=" + this.block + ", multimediaId=" + this.multimediaId + ")";
        }
    }
}
