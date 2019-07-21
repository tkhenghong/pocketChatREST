package com.pocketchat.dbRepoServices.settings;

import com.pocketchat.dbRepositories.settings.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsRepoService {

    @Autowired
    private SettingsRepository settingsRepository;

    public SettingsRepository getSettingsRepository() {
        return settingsRepository;
    }
}
