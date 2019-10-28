package com.pocketchat.db.repositories.user;

import com.pocketchat.db.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findFirstByGoogleAccountId(String googleAccountId);

    List<User> findByIdIn(List<String> userIds);

    Optional<User> findFirstByMobileNo(String mobileNo);
}
