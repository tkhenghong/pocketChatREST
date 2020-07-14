package com.pocketchat.server.configurations.security.service;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_privilege.UserPrivilege;
import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.db.repo_services.user_authentication.UserAuthenticationRepoService;
import com.pocketchat.db.repo_services.user_role.UserRoleRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserAuthenticationRepoService userAuthenticationRepoService;

    private final UserRoleRepoService userRoleRepoService;

    @Autowired
    MyUserDetailsService(UserAuthenticationRepoService userAuthenticationRepoService,
                         UserRoleRepoService userRoleRepoService) {
        this.userAuthenticationRepoService = userAuthenticationRepoService;
        this.userRoleRepoService = userRoleRepoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserAuthentication> authenticationOptional = userAuthenticationRepoService.findFirstByUsername(username);
        if (authenticationOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found with this username: " + username);
        }
        UserAuthentication userAuthentication = authenticationOptional.get();
        return new User(userAuthentication.getUsername(), userAuthentication.getPassword(), getUserAuthorities(userAuthentication.getUserRoles()));
    }

    // List of UserRoles >> UserPrivileges >> List of GrantedAuthorities in Spring Security.
    private Collection<? extends GrantedAuthority> getUserAuthorities(Collection<UserRole> userRoles) {
        return getUserGrantedAuthorities(getUserPrivileges(userRoles));
    }

    private List<String> getUserPrivileges(Collection<UserRole> roles) {
        List<String> privileges = new ArrayList<>();
        List<UserPrivilege> collection = new ArrayList<>();
        for (UserRole role : roles) {
            collection.addAll(role.getUserPrivileges());
        }
        for (UserPrivilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getUserGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
