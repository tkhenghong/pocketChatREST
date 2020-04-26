package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;

public interface UserService {
    UserResponse addUser(CreateUserRequest user);

    UserResponse editUser(UpdateUserRequest user);

    void deleteUser(String userId);

    User getUser(String userId);

    UserResponse getUserByGoogleAccountId(String googleAccountId);

    UserResponse getUserByMobileNo(String mobileNo);

    User createUserRequestToUserMapper(CreateUserRequest createUserRequest);

    User updateUserRequestToUserMapper(UpdateUserRequest updateUserRequest);

    UserResponse userResponseMapper(User user);
}
