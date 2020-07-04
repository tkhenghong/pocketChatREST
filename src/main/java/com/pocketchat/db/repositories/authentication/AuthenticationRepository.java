package com.pocketchat.db.repositories.authentication;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthenticationRepository extends MongoRepository<UserAuthentication, String> {
    Optional<UserAuthentication> findFirstByUsername(String username);
}
