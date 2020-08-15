package com.pocketchat.services.settings;

import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.repo_services.settings.SettingsRepoService;
import com.pocketchat.models.controllers.request.settings.CreateSettingsRequest;
import com.pocketchat.models.controllers.request.settings.UpdateSettingsRequest;
import com.pocketchat.models.controllers.response.settings.SettingsResponse;
import com.pocketchat.server.exceptions.settings.SettingsNotFoundException;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepoService settingsRepoService;

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public SettingsServiceImpl(SettingsRepoService settingsRepoService,
                               UserAuthenticationService userAuthenticationService) {
        this.settingsRepoService = settingsRepoService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    @Transactional
    public Settings addSettings(CreateSettingsRequest createSettingsRequest) {
        Settings settings = createSettingsRequestToSettingsMapper(createSettingsRequest);
        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(settings.getUserId());
        return settingsOptional.orElseGet(() -> settingsRepoService.save(settings));
    }

    @Override
    @Transactional
    public Settings editSettings(UpdateSettingsRequest updateSettingsRequest) {
        Settings settings = updateSettingsRequestToSettingsMapper(updateSettingsRequest);
        getSingleSettings(settings.getId());
        return settingsRepoService.save(settings);
    }

    @Override
    @Transactional
    public void deleteSettings(String settingsId) {
        settingsRepoService.delete(getSingleSettings(settingsId));
    }

    @Override
    public Settings getSingleSettings(String settingsId) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settingsId);
        if (settingsOptional.isEmpty()) {
            throw new SettingsNotFoundException("Settings not found, id:-" + settingsId);
        }
        return settingsOptional.get();
    }

    @Override
    public Settings getUserOwnSettings() {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();

        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(userAuthentication.getUserId());

        if (settingsOptional.isEmpty()) {
            throw new SettingsNotFoundException("userId not found in getSettingsOfAUser, userId:-" + userAuthentication.getUserId());
        }
        return settingsOptional.get();
    }

    private Settings createSettingsRequestToSettingsMapper(CreateSettingsRequest createSettingsRequest) {
        return Settings.builder()
                .id(createSettingsRequest.getId())
                .allowNotifications(createSettingsRequest.isAllowNotifications())
                .userId(createSettingsRequest.getUserId())
                .build();
    }

    private Settings updateSettingsRequestToSettingsMapper(UpdateSettingsRequest updateSettingsRequest) {
        return Settings.builder()
                .id(updateSettingsRequest.getId())
                .allowNotifications(updateSettingsRequest.isAllowNotifications())
                .userId(updateSettingsRequest.getUserId())
                .build();
    }

    @Override
    public SettingsResponse settingsResponseMapper(Settings settings) {
        return SettingsResponse.builder()
                .id(settings.getId())
                .allowNotifications(settings.isAllowNotifications())
                .userId(settings.getUserId())
                .build();
    }
}
