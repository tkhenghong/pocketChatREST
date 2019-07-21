package com.pocketchat.dbRepositories.settings;

import com.pocketchat.models.settings.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SettingsRepository extends MongoRepository<Settings, String> {
}
