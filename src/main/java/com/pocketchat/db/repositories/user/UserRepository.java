package com.pocketchat.db.repositories.user;

import com.pocketchat.db.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findFirstByMobileNo(String mobileNo);

    Optional<User> findFirstByEmailAddress(String emailAddress);
}
