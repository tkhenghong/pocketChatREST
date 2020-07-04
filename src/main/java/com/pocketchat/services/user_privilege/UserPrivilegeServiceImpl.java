package com.pocketchat.services.user_privilege;

import com.pocketchat.db.repo_services.user_privilege.UserPrivilegeRepoService;
import org.springframework.stereotype.Service;

@Service
public class UserPrivilegeServiceImpl implements UserPrivilegeService {
    private final UserPrivilegeRepoService userPrivilegeRepoService;

    UserPrivilegeServiceImpl(UserPrivilegeRepoService userPrivilegeRepoService) {
        this.userPrivilegeRepoService = userPrivilegeRepoService;
    }
}
