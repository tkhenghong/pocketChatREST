package com.pocketchat.server.configurations.security.service;

import com.pocketchat.db.models.authentication.Authentication;
import com.pocketchat.db.repositories.authentication.AuthenticationRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AuthenticationRepository authenticationRepository;

    MyUserDetailsService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Authentication authentication = authenticationRepository.findByUsername(username);

        if (authentication == null) {
            throw new UsernameNotFoundException("Username not found with this username: " + username);
        }

        return new User(authentication.getUsername(), authentication.getPassword(), Collections.emptyList());
    }
}