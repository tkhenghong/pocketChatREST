package com.pocketchat.db.models.settings;

import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "settings")
public class Settings extends Auditable {

    @Id
    private String id;

    @NotBlank
    private String userId;

    private boolean allowNotifications;

    Settings(String id, @NotBlank String userId, boolean allowNotifications) {
        this.id = id;
        this.userId = userId;
        this.allowNotifications = allowNotifications;
    }

    public static SettingsBuilder builder() {
        return new SettingsBuilder();
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
        if (!(o instanceof Settings)) return false;
        final Settings other = (Settings) o;
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
        return other instanceof Settings;
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
        return "Settings(id=" + this.getId() + ", userId=" + this.getUserId() + ", allowNotifications=" + this.isAllowNotifications() + ")";
    }

    public static class SettingsBuilder {
        private String id;
        private @NotBlank String userId;
        private boolean allowNotifications;

        SettingsBuilder() {
        }

        public Settings.SettingsBuilder id(String id) {
            this.id = id;
            return this;
        }

        public Settings.SettingsBuilder userId(@NotBlank String userId) {
            this.userId = userId;
            return this;
        }

        public Settings.SettingsBuilder allowNotifications(boolean allowNotifications) {
            this.allowNotifications = allowNotifications;
            return this;
        }

        public Settings build() {
            return new Settings(id, userId, allowNotifications);
        }

        public String toString() {
            return "Settings.SettingsBuilder(id=" + this.id + ", userId=" + this.userId + ", allowNotifications=" + this.allowNotifications + ")";
        }
    }
}
