package com.pocketchat.db.repo_services.authentication;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.repositories.authentication.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationRepoService {

    private final AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationRepoService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public Optional<UserAuthentication> findById(String id) {
        return authenticationRepository.findById(id);
    }

    public Optional<UserAuthentication> findFirstByUsername(String username) {
        return authenticationRepository.findFirstByUsername(username);
    }

    public UserAuthentication save(UserAuthentication userAuthentication) {
        return authenticationRepository.save(userAuthentication);
    }

    public void delete(UserAuthentication userAuthentication) {
        authenticationRepository.delete(userAuthentication);
    }
}
