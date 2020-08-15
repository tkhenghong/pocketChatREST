package com.pocketchat.services.settings;

import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.models.controllers.request.settings.CreateSettingsRequest;
import com.pocketchat.models.controllers.request.settings.UpdateSettingsRequest;
import com.pocketchat.models.controllers.response.settings.SettingsResponse;

public interface SettingsService {
    Settings addSettings(CreateSettingsRequest settings);

    Settings editSettings(UpdateSettingsRequest settings);

    void deleteSettings(String settingsId);

    Settings getSingleSettings(String settingsId);

    Settings getUserOwnSettings();

    SettingsResponse settingsResponseMapper(Settings settings);
}
