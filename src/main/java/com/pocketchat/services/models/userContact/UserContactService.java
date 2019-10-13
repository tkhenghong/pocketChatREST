package com.pocketchat.services.models.userContact;

import com.pocketchat.db.models.user_contact.UserContact;

import java.util.List;

public interface UserContactService {
    UserContact addUserContact(UserContact userContact);

    void editUserContact(UserContact userContact);

    void deleteUserContact(String userContactId);

    UserContact getUserContact(String userContactId);

    UserContact getUserContactByMobileNo(String mobileNo);

    List<UserContact> getUserContactsByUserId(String userId);
}