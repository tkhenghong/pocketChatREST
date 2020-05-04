package com.pocketchat.models.controllers.response.settings;

public class SettingsResponse {

    private String id;

    private String userId;

    private boolean allowNotifications;

    SettingsResponse(String id, String userId, boolean allowNotifications) {
        this.id = id;
        this.userId = userId;
        this.allowNotifications = allowNotifications;
    }

    public static SettingsResponseBuilder builder() {
        return new SettingsResponseBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isAllowNotifications() {
        return this.allowNotifications;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAllowNotifications(boolean allowNotifications) {
        this.allowNotifications = allowNotifications;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SettingsResponse)) return false;
        final SettingsResponse other = (SettingsResponse) o;
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
        return other instanceof SettingsResponse;
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
        return "SettingsResponse(id=" + this.getId() + ", userId=" + this.getUserId() + ", allowNotifications=" + this.isAllowNotifications() + ")";
    }

    public static class SettingsResponseBuilder {
        private String id;
        private String userId;
        private boolean allowNotifications;

        SettingsResponseBuilder() {
        }

        public SettingsResponse.SettingsResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SettingsResponse.SettingsResponseBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public SettingsResponse.SettingsResponseBuilder allowNotifications(boolean allowNotifications) {
            this.allowNotifications = allowNotifications;
            return this;
        }

        public SettingsResponse build() {
            return new SettingsResponse(id, userId, allowNotifications);
        }

        public String toString() {
            return "SettingsResponse.SettingsResponseBuilder(id=" + this.id + ", userId=" + this.userId + ", allowNotifications=" + this.allowNotifications + ")";
        }
    }
}
