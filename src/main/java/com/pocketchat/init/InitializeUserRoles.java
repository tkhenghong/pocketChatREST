package com.pocketchat.init;

import com.pocketchat.db.models.user_privilege.UserPrivilege;
import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.db.repo_services.user_authentication.UserAuthenticationRepoService;
import com.pocketchat.db.repo_services.user_privilege.UserPrivilegeRepoService;
import com.pocketchat.db.repo_services.user_role.UserRoleRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// https://www.baeldung.com/role-and-privilege-for-spring-security-registration
@Component
public class InitializeUserRoles implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserAuthenticationRepoService userAuthenticationRepoService;

    private final UserRoleRepoService userRoleRepoService;

    private final UserPrivilegeRepoService userPrivilegeRepoService;

    @Autowired
    InitializeUserRoles(UserAuthenticationRepoService userAuthenticationRepoService,
                        UserRoleRepoService userRoleRepoService,
                        UserPrivilegeRepoService userPrivilegeRepoService) {
        this.userAuthenticationRepoService = userAuthenticationRepoService;
        this.userRoleRepoService = userRoleRepoService;
        this.userPrivilegeRepoService = userPrivilegeRepoService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        UserPrivilege readPrivilege
                = createUserPrivilegeIfNotFound("ROLE_READ_PRIVILEGE");
        UserPrivilege writePrivilege
                = createUserPrivilegeIfNotFound("ROLE_WRITE_PRIVILEGE");

        List<UserPrivilege> userPrivilegeList = Arrays.asList(readPrivilege, writePrivilege);

        createUserRoleIfNotFound("ROLE_ADMIN", userPrivilegeList);
        createUserRoleIfNotFound("ROLE_USER", userPrivilegeList);

        alreadySetup = true;
    }

    @Transactional
    UserPrivilege createUserPrivilegeIfNotFound(String name) {
        UserPrivilege userPrivilege;
        Optional<UserPrivilege> privilege = userPrivilegeRepoService.findByName(name);

        if (privilege.isEmpty()) {
            userPrivilege = UserPrivilege.builder()
                    .name(name)
                    .build();
            userPrivilege = userPrivilegeRepoService.save(userPrivilege);
        } else {
            userPrivilege = privilege.get();
        }
        return userPrivilege;
    }

    @Transactional
    UserRole createUserRoleIfNotFound(String name, List<UserPrivilege> userPrivilegeList) {
        UserRole userRole;
        Optional<UserRole> privilege = userRoleRepoService.findByName(name);

        if (privilege.isEmpty()) {
            userRole = UserRole.builder()
                    .name(name)
                    .privileges(userPrivilegeList)
                    .build();
            userRole = userRoleRepoService.save(userRole);
        } else {
            userRole = privilege.get();
        }
        return userRole;
    }
}
