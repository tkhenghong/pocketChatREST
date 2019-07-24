package com.pocketchat.db.repoServices.user;

import com.pocketchat.db.repositories.user.UserRepository;
import com.pocketchat.db.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepoService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    public User save(User conversationGroup) {
        return userRepository.save(conversationGroup);
    }

    public void delete(User conversationGroup) {
        userRepository.delete(conversationGroup);
    }
}
