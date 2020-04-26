package com.pocketchat.models.controllers.request.settings;

import javax.validation.constraints.NotBlank;

public class UpdateSettingsRequest extends SettingsRequest {
    UpdateSettingsRequest(String id, @NotBlank String userId, boolean allowNotifications) {
        super(id, userId, allowNotifications);
    }
}
