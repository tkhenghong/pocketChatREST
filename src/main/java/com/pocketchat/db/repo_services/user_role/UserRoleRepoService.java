package com.pocketchat.db.repo_services.user_role;

import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.db.repositories.user_role.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleRepoService {
    private final UserRoleRepository userRoleRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    UserRoleRepoService(UserRoleRepository userRoleRepository,
                        MongoTemplate mongoTemplate) {
        this.userRoleRepository = userRoleRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<UserRole> findByName(String name) {
        return userRoleRepository.findFirstByName(name);
    }

    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    public void delete(UserRole userRole) {
        userRoleRepository.delete(userRole);
    }

}
