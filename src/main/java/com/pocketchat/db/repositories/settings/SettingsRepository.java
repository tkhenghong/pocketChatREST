package com.pocketchat.db.repositories.settings;

import com.pocketchat.db.models.settings.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SettingsRepository extends MongoRepository<Settings, String> {
    Optional<Settings> findByUserId (String userId);
}
