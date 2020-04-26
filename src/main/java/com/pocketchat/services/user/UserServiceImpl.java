package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;
import com.pocketchat.server.exceptions.user.UserGoogleAccountIsAlreadyRegisteredException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepoService userRepoService;

    private final AuthenticationService authenticationService;

    @Autowired
    public UserServiceImpl(UserRepoService userRepoService, AuthenticationService authenticationService) {
        this.userRepoService = userRepoService;
        this.authenticationService = authenticationService;
    }

    @Override
    public UserResponse addUser(CreateUserRequest createUserRequest) {
        User user = createUserRequestToUserMapper(createUserRequest);

        Optional<User> userOptional = userRepoService.findByGoogleAccountId(user.getGoogleAccountId());
        if (userOptional.isPresent()) {
            throw new UserGoogleAccountIsAlreadyRegisteredException("Google Account has already registered. Google Account ID: " + user.getGoogleAccountId());
        }

        return userResponseMapper(userRepoService.save(user)); // userOptional.isPresent() ? userOptional.get() : userRepoService.save(user)
    }

    @Override
    public UserResponse editUser(UpdateUserRequest updateUserRequest) {
        getUser(updateUserRequest.getId());
        return userResponseMapper(userRepoService.save(updateUserRequestToUserMapper(updateUserRequest)));
    }

    @Override
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
    public UserResponse getUserByGoogleAccountId(String googleAccountId) {
        // NOTE: normally you wouldn't find multiple Users with same googleAccountId back from database,
        // But for safety, I have used findFirst in MongoRepository of User class.
        Optional<User> userOptional = userRepoService.findByGoogleAccountId(googleAccountId);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found by using googleAccountId: " + googleAccountId);
        }
        return userResponseMapper(userOptional.get());
    }

    @Override
    public UserResponse getUserByMobileNo(String mobileNo) {
        Optional<User> userOptional = userRepoService.findByMobileNo(mobileNo);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found by using mobileNo: " + mobileNo);
        }
        return userResponseMapper(userOptional.get());
    }

    @Override
    public User createUserRequestToUserMapper(CreateUserRequest createUserRequest) {
        return User.builder()
                .id(createUserRequest.getId())
                .countryCode(createUserRequest.getCountryCode())
                .displayName(createUserRequest.getDisplayName())
                .realName(createUserRequest.getRealName())
                .effectivePhoneNumber(createUserRequest.getEffectivePhoneNumber())
                .googleAccountId(createUserRequest.getGoogleAccountId())
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
                .effectivePhoneNumber(updateUserRequest.getEffectivePhoneNumber())
                .googleAccountId(updateUserRequest.getGoogleAccountId())
                .mobileNo(updateUserRequest.getMobileNo())
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
                .effectivePhoneNumber(user.getEffectivePhoneNumber())
                .displayName(user.getDisplayName())
                .build();
    }
}
