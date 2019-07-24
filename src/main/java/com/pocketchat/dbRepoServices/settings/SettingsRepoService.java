package com.pocketchat.dbRepoServices.settings;

import com.pocketchat.dbRepositories.settings.SettingsRepository;
import com.pocketchat.models.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsRepoService {

    private final SettingsRepository settingsRepository;

    @Autowired
    public SettingsRepoService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public Optional<Settings> findById(String settingsId) {
        return settingsRepository.findById(settingsId);
    }

    public Optional<Settings> findByUserId(String userId) {
        return settingsRepository.findByUserId(userId);
    }

    public Settings save(Settings settings) {
        return settingsRepository.save(settings);
    }

    public void delete(Settings settings) {
        settingsRepository.delete(settings);
    }
}
