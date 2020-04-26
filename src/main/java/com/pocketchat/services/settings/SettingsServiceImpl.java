package com.pocketchat.services.settings;

import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.settings.SettingsRepoService;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.models.controllers.request.settings.CreateSettingsRequest;
import com.pocketchat.models.controllers.request.settings.UpdateSettingsRequest;
import com.pocketchat.models.controllers.response.settings.SettingsResponse;
import com.pocketchat.server.exceptions.settings.SettingsNotFoundException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepoService settingsRepoService;

    private final UserRepoService userRepoService;

    @Autowired
    public SettingsServiceImpl(SettingsRepoService settingsRepoService, UserRepoService userRepoService) {
        this.settingsRepoService = settingsRepoService;
        this.userRepoService = userRepoService;
    }

    @Override
    public SettingsResponse addSettings(CreateSettingsRequest createSettingsRequest) {
        Settings settings = createSettingsRequestToSettingsMapper(createSettingsRequest);
        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(settings.getUserId());
        return settingsResponseMapper(settingsOptional.orElseGet(() -> settingsRepoService.save(settings)));
    }

    @Override
    public SettingsResponse editSettings(UpdateSettingsRequest updateSettingsRequest) {
        Settings settings = updateSettingsRequestToSettingsMapper(updateSettingsRequest);
        getSingleSettings(settings.getId());
        return settingsResponseMapper(settingsRepoService.save(settings));
    }

    @Override
    public void deleteSettings(String settingsId) {
        settingsRepoService.delete(getSingleSettings(settingsId));
    }

    @Override
    public Settings getSingleSettings(String settingsId) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settingsId);
        if (!settingsOptional.isPresent()) {
            throw new SettingsNotFoundException("Settings not found, id:-" + settingsId);
        }
        return settingsOptional.get();
    }

    @Override
    public SettingsResponse getSettingsOfAUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found, userId:-" + userId);
        }

        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(userOptional.get().getId());

        if (!settingsOptional.isPresent()) {
            throw new SettingsNotFoundException("userId not found in getSettingsOfAUser, userId:-" + userId);
        }
        return settingsResponseMapper(settingsOptional.get());
    }

    @Override
    public Settings createSettingsRequestToSettingsMapper(CreateSettingsRequest createSettingsRequest) {
        return Settings.builder()
                .id(createSettingsRequest.getId())
                .allowNotifications(createSettingsRequest.isAllowNotifications())
                .userId(createSettingsRequest.getUserId())
                .build();
    }

    @Override
    public Settings updateSettingsRequestToSettingsMapper(UpdateSettingsRequest updateSettingsRequest) {
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
