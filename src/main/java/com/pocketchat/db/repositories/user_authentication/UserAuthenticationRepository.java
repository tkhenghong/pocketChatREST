package com.pocketchat.db.repositories.user_authentication;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserAuthenticationRepository extends MongoRepository<UserAuthentication, String> {
    Optional<UserAuthentication> findFirstByUsername(String username);

    Optional<UserAuthentication> findFirstByUserId(String userId);
}
