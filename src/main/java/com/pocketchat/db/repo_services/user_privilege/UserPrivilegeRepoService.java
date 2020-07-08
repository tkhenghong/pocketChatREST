package com.pocketchat.db.repo_services.user_privilege;

import com.pocketchat.db.models.user_privilege.UserPrivilege;
import com.pocketchat.db.repositories.user_privilege.UserPrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrivilegeRepoService {
    private final UserPrivilegeRepository userPrivilegeRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    UserPrivilegeRepoService(UserPrivilegeRepository userPrivilegeRepository,
                             MongoTemplate mongoTemplate) {
        this.userPrivilegeRepository = userPrivilegeRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<UserPrivilege> findByName(String name) {
        return userPrivilegeRepository.findFirstByName(name);
    }

    public UserPrivilege save(UserPrivilege userPrivilege) {
        return userPrivilegeRepository.save(userPrivilege);
    }

    public void delete(UserPrivilege userPrivilege) {
        userPrivilegeRepository.delete(userPrivilege);
    }

}
