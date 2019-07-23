package com.pocketchat.dbRepoServices.user;

import com.pocketchat.dbRepositories.user.UserRepository;
import com.pocketchat.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepoService {
    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public User save(User conversationGroup) {
        return userRepository.save(conversationGroup);
    }

    public void delete(User conversationGroup) {
        userRepository.delete(conversationGroup);
    }
}
