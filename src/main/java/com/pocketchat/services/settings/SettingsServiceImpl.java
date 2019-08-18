package com.pocketchat.services.settings;

import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.settings.SettingsRepoService;
import com.pocketchat.db.repoServices.user.UserRepoService;
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
    public Settings addSettings(Settings settings) {
        return settingsRepoService.save(settings);
    }

    @Override
    public void editSettings(Settings settings) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settings.getId());
        validateSettingsNotFound(settingsOptional, settings.getId());
        addSettings(settings);
    }

    @Override
    public void deleteSettings(String settingsId) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settingsId);
        validateSettingsNotFound(settingsOptional, settingsId);
        settingsRepoService.delete(settingsOptional.get());
    }

    @Override
    public Settings getSingleSettings(String settingsId) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settingsId);
        validateSettingsNotFound(settingsOptional, settingsId);
        return settingsOptional.get();
    }

    @Override
    public Settings getSettingsOfAUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found, userId:-" + userId);
        }

        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(userOptional.get().getId());

        validateSettingsNotFound(settingsOptional, "");
        return settingsOptional.get();
    }


    private void validateSettingsNotFound(Optional<Settings> settingsOptional, String settingsId) {
        if (!settingsOptional.isPresent()) {
            throw new SettingsNotFoundException("Settings not found, id:-" + settingsId);
        }
    }
}
