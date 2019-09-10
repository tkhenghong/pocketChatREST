package com.pocketchat.services.models.user;

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
        Optional<User> userOptional = userRepoService.findByGoogleAccountId(user.getGoogleAccountId());
        return userOptional.orElseGet(() -> userRepoService.save(user)); // userOptional.isPresent() ? userOptional.get() : userRepoService.save(user)
    }

    @Override
    public void editUser(User user) {
        getUser(user.getId());
        addUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepoService.delete(getUser(userId));
    }

    @Override
    public User getUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        return validateUserNotFound(userOptional, userId);
    }

    @Override
    public User getUserByGoogleAccountId(String googleAccountId) {
        // NOTE: normally you wouldn't find multiple Users with same googleAccountId back from database,
        // But for safety, I have used findFirst in MongoRepository of User class.
        Optional<User> userOptional = userRepoService.findByGoogleAccountId(googleAccountId);
        return validateUserNotFound(userOptional, googleAccountId);
    }

    @Override
    public User getUserByMobileNo(String mobileNo) {
        Optional<User> userOptional = userRepoService.findByMobileNo(mobileNo);
        return validateUserNotFound(userOptional, mobileNo);
    }

    private User validateUserNotFound(Optional<User> userOptional, String userId) {
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("userId-" + userId);
        } else {
            return userOptional.get();
        }
    }
}
