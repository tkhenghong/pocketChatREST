package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repo_services.user.UserRepoService;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;
import com.pocketchat.server.exceptions.user.UserGoogleAccountIsAlreadyRegisteredException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepoService userRepoService;

    @Autowired
    public UserServiceImpl(UserRepoService userRepoService) {
        this.userRepoService = userRepoService;
    }

    @Override
    @Transactional
    public User addUser(CreateUserRequest createUserRequest) {
        User user = createUserRequestToUserMapper(createUserRequest);

        Optional<User> userOptional = userRepoService.findByGoogleAccountId(user.getGoogleAccountId());
        if (userOptional.isPresent()) {
            throw new UserGoogleAccountIsAlreadyRegisteredException("Google Account has already registered. Google Account ID: " + user.getGoogleAccountId());
        }

        return userRepoService.save(user); // userOptional.isPresent() ? userOptional.get() : userRepoService.save(user)
    }

    @Override
    @Transactional
    public User editUser(UpdateUserRequest updateUserRequest) {
        getUser(updateUserRequest.getId());
        return userRepoService.save(updateUserRequestToUserMapper(updateUserRequest));
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        userRepoService.delete(getUser(userId));
    }

    @Override
    public User getUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found by using userId: " + userId);
        }
        return userOptional.get();
    }

    @Override
    public User getUserByGoogleAccountId(String googleAccountId) {
        // NOTE: normally you wouldn't find multiple Users with same googleAccountId back from database,
        // But for safety, I have used findFirst in MongoRepository of User class.
        Optional<User> userOptional = userRepoService.findByGoogleAccountId(googleAccountId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found by using googleAccountId: " + googleAccountId);
        }
        return userOptional.get();
    }

    @Override
    public User getUserByMobileNo(String mobileNo) {
        Optional<User> userOptional = userRepoService.findByMobileNo(mobileNo);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found by using mobileNo: " + mobileNo);
        }
        return userOptional.get();
    }

    @Override
    public User createUserRequestToUserMapper(CreateUserRequest createUserRequest) {
        return User.builder()
                .countryCode(createUserRequest.getCountryCode())
                .displayName(createUserRequest.getDisplayName())
                .realName(createUserRequest.getRealName())
                .mobileNo(createUserRequest.getMobileNo())
                .build();
    }

    @Override
    public User updateUserRequestToUserMapper(UpdateUserRequest updateUserRequest) {
        return User.builder()
                .id(updateUserRequest.getId())
                .countryCode(updateUserRequest.getCountryCode())
                .displayName(updateUserRequest.getDisplayName())
                .realName(updateUserRequest.getRealName())
                .googleAccountId(updateUserRequest.getGoogleAccountId())
                .mobileNo(updateUserRequest.getMobileNo())
                .emailAddress(updateUserRequest.getEmailAddress())
                .build();
    }

    @Override
    public UserResponse userResponseMapper(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .countryCode(user.getCountryCode())
                .realName(user.getRealName())
                .mobileNo(user.getMobileNo())
                .googleAccountId(user.getGoogleAccountId())
                .emailAddress(user.getEmailAddress())
                .displayName(user.getDisplayName())
                .build();
    }
}
