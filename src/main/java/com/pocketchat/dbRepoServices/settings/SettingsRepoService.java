package com.pocketchat.dbRepoServices.settings;

import com.pocketchat.dbRepositories.settings.SettingsRepository;
import com.pocketchat.models.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsRepoService {

    @Autowired
    private SettingsRepository settingsRepository;

    public SettingsRepository getSettingsRepository() {
        return settingsRepository;
    }

    public Settings save(Settings conversationGroup) {
        return settingsRepository.save(conversationGroup);
    }

    public void delete(Settings conversationGroup) {
        settingsRepository.delete(conversationGroup);
    }
}
