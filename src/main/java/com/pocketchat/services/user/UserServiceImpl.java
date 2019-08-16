package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepoService userRepoService;

    @Autowired
    public UserServiceImpl(UserRepoService userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    public User addUser(User user) {
        return userRepoService.save(user);
    }

    @Override
    public void editUser(User user) {
        Optional<User> userOptional = userRepoService.findById(user.getId());
        validateUserNotFound(userOptional, user.getId());
        userRepoService.save(user);
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        validateUserNotFound(userOptional, userId);
        userRepoService.delete(userOptional.get());
    }

    @Override
    public User getUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        validateUserNotFound(userOptional, userId);
        return userOptional.get();
    }

    private void validateUserNotFound(Optional<User> userOptional, String userId) {
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("userId-" + userId);
        }
    }
}