package com.pocketchat.services.models.settings;

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
        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(settings.getUserId());
        return settingsOptional.orElseGet(() -> settingsRepoService.save(settings));
    }

    @Override
    public void editSettings(Settings settings) {
        getSingleSettings(settings.getId());
        settingsRepoService.save(settings);
    }

    @Override
    public void deleteSettings(String settingsId) {
        settingsRepoService.delete(getSingleSettings(settingsId));
    }

    @Override
    public Settings getSingleSettings(String settingsId) {
        Optional<Settings> settingsOptional = settingsRepoService.findById(settingsId);
        return validateSettingsNotFound(settingsOptional, settingsId);
    }

    @Override
    public Settings getSettingsOfAUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found, userId:-" + userId);
        }

        Optional<Settings> settingsOptional = settingsRepoService.findByUserId(userOptional.get().getId());

        return validateSettingsNotFound(settingsOptional, "");
    }


    private Settings validateSettingsNotFound(Optional<Settings> settingsOptional, String settingsId) {
        if (!settingsOptional.isPresent()) {
            throw new SettingsNotFoundException("Settings not found, id:-" + settingsId);
        } else {
            return settingsOptional.get();
        }
    }
}
