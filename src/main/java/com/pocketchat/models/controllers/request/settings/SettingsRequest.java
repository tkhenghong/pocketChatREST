package com.pocketchat.models.controllers.request.settings;

import javax.validation.constraints.NotBlank;

public class SettingsRequest {

    private String id;

    @NotBlank
    private String userId;

    private boolean allowNotifications;

    SettingsRequest(String id, @NotBlank String userId, boolean allowNotifications) {
        this.id = id;
        this.userId = userId;
        this.allowNotifications = allowNotifications;
    }

    public static SettingsRequestBuilder builder() {
        return new SettingsRequestBuilder();
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
        if (!(o instanceof SettingsRequest)) return false;
        final SettingsRequest other = (SettingsRequest) o;
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
        return other instanceof SettingsRequest;
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
        return "SettingsRequest(id=" + this.getId() + ", userId=" + this.getUserId() + ", allowNotifications=" + this.isAllowNotifications() + ")";
    }

    public static class SettingsRequestBuilder {
        private String id;
        private @NotBlank String userId;
        private boolean allowNotifications;

        SettingsRequestBuilder() {
        }

        public SettingsRequest.SettingsRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SettingsRequest.SettingsRequestBuilder userId(@NotBlank String userId) {
            this.userId = userId;
            return this;
        }

        public SettingsRequest.SettingsRequestBuilder allowNotifications(boolean allowNotifications) {
            this.allowNotifications = allowNotifications;
            return this;
        }

        public SettingsRequest build() {
            return new SettingsRequest(id, userId, allowNotifications);
        }

        public String toString() {
            return "SettingsRequest.SettingsRequestBuilder(id=" + this.id + ", userId=" + this.userId + ", allowNotifications=" + this.allowNotifications + ")";
        }
    }
}
