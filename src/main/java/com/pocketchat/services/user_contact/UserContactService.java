package com.pocketchat.services.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.models.controllers.request.user_contact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;

import java.util.List;

public interface UserContactService {
    UserContact addUserContact(CreateUserContactRequest userContact);

    UserContact editUserContact(UpdateUserContactRequest userContact);

    void deleteUserContact(String userContactId);

    UserContact getUserContact(String userContactId);

    UserContact getUserContactByMobileNo(String mobileNo);

    UserContact getOwnUserContact();

    List<UserContact> getUserContactsOfAUser();

    UserContact createUserContactRequestToUserContactMapper(CreateUserContactRequest createUserContactRequest);

    UserContact updateUserContactRequestToUserContactMapper(UpdateUserContactRequest updateUserContactRequest);

    UserContactResponse userContactResponseMapper(UserContact userContact);
}
