package com.pocketchat.db.repo_services.user_authentication;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.repositories.user_authentication.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticationRepoService {

    private final UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    UserAuthenticationRepoService(UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
    }

    public Optional<UserAuthentication> findById(String id) {
        return userAuthenticationRepository.findById(id);
    }

    public Optional<UserAuthentication> findFirstByUsername(String username) {
        return userAuthenticationRepository.findFirstByUsername(username);
    }

    public Optional<UserAuthentication> findByUserId(String userId) {
        return userAuthenticationRepository.findFirstByUserId(userId);
    }

    public UserAuthentication save(UserAuthentication userAuthentication) {
        return userAuthenticationRepository.save(userAuthentication);
    }

    public void delete(UserAuthentication userAuthentication) {
        userAuthenticationRepository.delete(userAuthentication);
    }
}
