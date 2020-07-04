package com.pocketchat.db.repositories.user_role;

import com.pocketchat.db.models.user_role.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRoleRepository extends MongoRepository<UserRole, String> {
    Optional<UserRole> findFirstByName(String name);
}
