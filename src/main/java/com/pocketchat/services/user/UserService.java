package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;
import com.pocketchat.models.controllers.request.user.CreateUserRequest;
import com.pocketchat.models.controllers.request.user.UpdateUserRequest;
import com.pocketchat.models.controllers.response.user.UserResponse;

public interface UserService {
    User addUser(CreateUserRequest user);

    User editUser(UpdateUserRequest user);

    void deleteUser(String userId);

    User getUser(String userId);

    User getUserByGoogleAccountId(String googleAccountId);

    User getUserByMobileNo(String mobileNo);

    User createUserRequestToUserMapper(CreateUserRequest createUserRequest);

    User updateUserRequestToUserMapper(UpdateUserRequest updateUserRequest);

    UserResponse userResponseMapper(User user);
}
