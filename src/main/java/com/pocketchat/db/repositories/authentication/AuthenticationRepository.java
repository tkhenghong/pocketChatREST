package com.pocketchat.db.repositories.authentication;

import com.pocketchat.db.models.authentication.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthenticationRepository extends MongoRepository<Authentication, String> {
    Optional<Authentication> findFirstByUsername(String username);
}
