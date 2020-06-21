package com.pocketchat.models.controllers.request.settings;

import javax.validation.constraints.NotBlank;

public class UpdateSettingsRequest {

    private String id;

    @NotBlank
    private String userId;

    private boolean allowNotifications;

    UpdateSettingsRequest(String id, @NotBlank String userId, boolean allowNotifications) {
        this.id = id;
        this.userId = userId;
        this.allowNotifications = allowNotifications;
    }

    public static UpdateSettingsRequestBuilder builder() {
        return new UpdateSettingsRequestBuilder();
    }

    public String getId() {
        return this.id;
    }

    public @NotBlank String getUserId() {
        return this.userId;
    }

    public boolean isAllowNotifications() {
        return this.allowNotifications;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(@NotBlank String userId) {
        this.userId = userId;
    }

    public void setAllowNotifications(boolean allowNotifications) {
        this.allowNotifications = allowNotifications;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateSettingsRequest)) return false;
        final UpdateSettingsRequest other = (UpdateSettingsRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        if (this.isAllowNotifications() != other.isAllowNotifications()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UpdateSettingsRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        result = result * PRIME + (this.isAllowNotifications() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "UpdateSettingsRequest(id=" + this.getId() + ", userId=" + this.getUserId() + ", allowNotifications=" + this.isAllowNotifications() + ")";
    }

    public static class UpdateSettingsRequestBuilder {
        private String id;
        private @NotBlank String userId;
        private boolean allowNotifications;

        UpdateSettingsRequestBuilder() {
        }

        public UpdateSettingsRequest.UpdateSettingsRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UpdateSettingsRequest.UpdateSettingsRequestBuilder userId(@NotBlank String userId) {
            this.userId = userId;
            return this;
        }

        public UpdateSettingsRequest.UpdateSettingsRequestBuilder allowNotifications(boolean allowNotifications) {
            this.allowNotifications = allowNotifications;
            return this;
        }

        public UpdateSettingsRequest build() {
            return new UpdateSettingsRequest(id, userId, allowNotifications);
        }

        public String toString() {
            return "UpdateSettingsRequest.UpdateSettingsRequestBuilder(id=" + this.id + ", userId=" + this.userId + ", allowNotifications=" + this.allowNotifications + ")";
        }
    }
}
