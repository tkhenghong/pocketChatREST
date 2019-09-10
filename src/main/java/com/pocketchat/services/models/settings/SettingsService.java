package com.pocketchat.services.models.settings;

import com.pocketchat.db.models.settings.Settings;

public interface SettingsService {
    Settings addSettings(Settings settings);

    void editSettings(Settings settings);

    void deleteSettings(String settingsId);

    Settings getSingleSettings(String settingsId);

    Settings getSettingsOfAUser(String userId);
}
