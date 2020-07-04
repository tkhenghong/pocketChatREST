package com.pocketchat.db.repositories.user_privilege;

import com.pocketchat.db.models.user_privilege.UserPrivilege;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserPrivilegeRepository extends MongoRepository<UserPrivilege, String> {
    Optional<UserPrivilege> findFirstByName(String name);
}
