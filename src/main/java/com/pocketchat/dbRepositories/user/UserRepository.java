package com.pocketchat.dbRepositories.user;

import com.pocketchat.models.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
