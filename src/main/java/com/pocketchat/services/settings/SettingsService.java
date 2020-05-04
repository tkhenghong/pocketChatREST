package com.pocketchat.services.settings;

import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.models.controllers.request.settings.CreateSettingsRequest;
import com.pocketchat.models.controllers.request.settings.UpdateSettingsRequest;
import com.pocketchat.models.controllers.response.settings.SettingsResponse;

public interface SettingsService {
    SettingsResponse addSettings(CreateSettingsRequest settings);

    SettingsResponse editSettings(UpdateSettingsRequest settings);

    void deleteSettings(String settingsId);

    Settings getSingleSettings(String settingsId);

    SettingsResponse getSettingsOfAUser(String userId);

    Settings createSettingsRequestToSettingsMapper(CreateSettingsRequest createSettingsRequest);

    Settings updateSettingsRequestToSettingsMapper(UpdateSettingsRequest updateSettingsRequest);

    SettingsResponse settingsResponseMapper(Settings settings);
}
