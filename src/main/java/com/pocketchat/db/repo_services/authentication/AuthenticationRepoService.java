package com.pocketchat.db.repo_services.authentication;

import com.pocketchat.db.models.authentication.Authentication;
import com.pocketchat.db.repositories.authentication.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationRepoService {

    private final AuthenticationRepository authenticationRepository;

    @Autowired
    AuthenticationRepoService(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public Optional<Authentication> findById(String id) {
        return authenticationRepository.findById(id);
    }

    public Optional<Authentication> findFirstByUsername(String username) {
        return authenticationRepository.findFirstByUsername(username);
    }

    public Authentication save(Authentication authentication) {
        return authenticationRepository.save(authentication);
    }

    public void delete(Authentication authentication) {
        authenticationRepository.delete(authentication);
    }
}
