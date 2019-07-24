package com.pocketchat.db.repositories.user;

import com.pocketchat.db.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
