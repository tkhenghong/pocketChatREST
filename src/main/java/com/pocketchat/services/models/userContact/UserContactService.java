package com.pocketchat.services.userContact;

import com.pocketchat.db.models.user_contact.UserContact;

public interface UserContactService {
    UserContact addUserContact(UserContact userContact);

    void editUserContact(UserContact userContact);

    void deleteUserContact(String userContactId);

    UserContact getUserContact(String userContactId);

    UserContact getUserContactByMobileNo(String mobileNo);
}
