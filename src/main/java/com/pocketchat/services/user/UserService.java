package com.pocketchat.services.user;

import com.pocketchat.db.models.user.User;

public interface UserService {
    User addUser(User user);

    void editUser(User user);

    void deleteUser(String userId);

    User getUser(String userId);

    User getUserbyGoogleAccountId(String googleAccountId);

    User getUserByMobileNo(String mobileNo);
}
