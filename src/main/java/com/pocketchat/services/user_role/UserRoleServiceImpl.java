package com.pocketchat.services.user_role;

import com.pocketchat.db.repo_services.user_role.UserRoleRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepoService userRoleRepoService;

    @Autowired
    UserRoleServiceImpl(UserRoleRepoService userRoleRepoService) {
        this.userRoleRepoService = userRoleRepoService;
    }
}
