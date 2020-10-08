package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.repo_services.user.UserRepoService;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;
import com.pocketchat.server.exceptions.user.UserGoogleAccountIsAlreadyRegisteredException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepoService userRepoService;

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public UserServiceImpl(UserRepoService userRepoService, UserAuthenticationService userAuthenticationService) {
        this.userRepoService = userRepoService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    @Transactional
    public User addUser(CreateUserRequest createUserRequest) {
        User user = createUserRequestToUserMapper(createUserRequest);

        checkUserExistingGoogleAccount(user);

        return userRepoService.save(user); // userOptional.isPresent() ? userOptional.get() : userRepoService.save(user)
    }

    private void checkUserExistingGoogleAccount(User user) {
//        if (!StringUtils.isEmpty(user.getGoogleAccountId())) {
//            Optional<User> userOptional = userRepoService.findByGoogleAccountId(user.getGoogleAccountId());
//            if (userOptional.isPresent()) {
//                throw new UserGoogleAccountIsAlreadyRegisteredException("Google Account has been already registered. Google Account ID: " + user.getGoogleAccountId());
//            }
//        }
    }

    @Override
    @Transactional
    public User editUser(UpdateUserRequest updateUserRequest) {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
        getUser(userAuthentication.getId());
        return userRepoService.save(updateUserRequestToUserMapper(updateUserRequest));
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        userRepoService.delete(getUser(userId));
    }

    @Override
    public boolean existById(String userId) {
        return userRepoService.existById(userId);
    }

    @Override
    public User getUser(String userId) {
        Optional<User> userOptional = userRepoService.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Unable to find the User by using userId: " + userId);
        }
        return userOptional.get();
    }

    @Override
    public User getOwnUser() {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
        return getUser(userAuthentication.getUserId());
    }

    @Override
    public User getUserByMobileNo(String mobileNo) {
        Optional<User> userOptional = userRepoService.findByMobileNo(mobileNo);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Unable to find the User by using mobileNo: " + mobileNo);
        }
        return userOptional.get();
    }

    @Override
    public User getUserByEmailAddress(String emailAddress) {
        Optional<User> userOptional = userRepoService.findByEmailAddress(emailAddress);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Unable to find the User by using emailAddress: " + emailAddress);
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
//                .countryCode(updateUserRequest.getCountryCode())
//                .displayName(updateUserRequest.getDisplayName())
//                .realName(updateUserRequest.getRealName())
//                .googleAccountId(updateUserRequest.getGoogleAccountId())
//                .mobileNo(updateUserRequest.getMobileNo())
//                .emailAddress(updateUserRequest.getEmailAddress())
                .build();
    }

    @Override
    public UserResponse userResponseMapper(User user) {
        return UserResponse.builder()
//                .id(user.getId())
//                .countryCode(user.getCountryCode())
//                .realName(user.getRealName())
//                .mobileNo(user.getMobileNo())
//                .googleAccountId(user.getGoogleAccountId())
//                .emailAddress(user.getEmailAddress())
//                .displayName(user.getDisplayName())
                .build();
    }
}
