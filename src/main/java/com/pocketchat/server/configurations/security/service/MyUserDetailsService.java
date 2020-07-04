package com.pocketchat.server.configurations.security.service;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.repo_services.authentication.AuthenticationRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AuthenticationRepoService authenticationRepoService;

    @Autowired
    MyUserDetailsService(AuthenticationRepoService authenticationRepoService) {
        this.authenticationRepoService = authenticationRepoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserAuthentication> authenticationOptional = authenticationRepoService.findFirstByUsername(username);
        if (!authenticationOptional.isPresent()) {
            throw new UsernameNotFoundException("Username not found with this username: " + username);
        }
        UserAuthentication userAuthentication = authenticationOptional.get();
        return new User(userAuthentication.getUsername(), userAuthentication.getPassword(), Collections.emptyList());
    }
}
