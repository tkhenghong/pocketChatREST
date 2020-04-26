package com.pocketchat.db.repositories.authentication;

import com.pocketchat.db.models.authentication.Authentication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthenticationRepository extends MongoRepository<Authentication, String> {
    Authentication findByUsername(String username);
}
