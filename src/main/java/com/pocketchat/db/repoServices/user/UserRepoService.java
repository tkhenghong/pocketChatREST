package com.pocketchat.db.repoServices.user;

import com.pocketchat.db.repositories.user.UserRepository;
import com.pocketchat.db.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRepoService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    public List<User> findByIdsIn(List<String> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    public User save(User conversationGroup) {
        return userRepository.save(conversationGroup);
    }

    public void delete(User conversationGroup) {
        userRepository.delete(conversationGroup);
    }

    public Optional<User> findByGoogleAccountId(String googleAccountId) {
        return userRepository.findFirstByGoogleAccountId(googleAccountId);
    }

    public Optional<User> findByMobileNo(String googleAccountId) {
        return userRepository.findFirstByMobileNo(googleAccountId);
    }
}
