package com.pocketchat.models.controllers.request.settings;

import javax.validation.constraints.NotBlank;

public class UpdateSettingsRequest {

    @NotBlank
    private String id;

    private boolean allowNotifications;

    public UpdateSettingsRequest() {
    }

    public @NotBlank String getId() {
        return this.id;
    }

    public boolean isAllowNotifications() {
        return this.allowNotifications;
    }

    public void setId(@NotBlank String id) {
        this.id = id;
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
        result = result * PRIME + (this.isAllowNotifications() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "UpdateSettingsRequest(id=" + this.getId() + ", allowNotifications=" + this.isAllowNotifications() + ")";
    }
}
