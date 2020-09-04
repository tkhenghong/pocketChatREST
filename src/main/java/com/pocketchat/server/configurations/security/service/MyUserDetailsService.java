package com.pocketchat.server.configurations.security.service;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_privilege.UserPrivilege;
import com.pocketchat.db.models.user_role.UserRole;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public MyUserDetailsService(@Lazy UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAuthentication userAuthentication = userAuthenticationService.findByUsername(username);
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
