package com.pocketchat.services.userContact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.models.controllers.request.userContact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.userContact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.userContact.UserContactResponse;

import java.util.List;

public interface UserContactService {
    UserContactResponse addUserContact(CreateUserContactRequest userContact);

    UserContactResponse editUserContact(UpdateUserContactRequest userContact);

    void deleteUserContact(String userContactId);

    UserContact getUserContact(String userContactId);

    UserContact getUserContactByMobileNo(String mobileNo);

    List<UserContactResponse> getUserContactsByUserId(String userId);

    UserContact createUserContactRequestToUserContactMapper(CreateUserContactRequest createUserContactRequest);

    UserContact updateUserContactRequestToUserContactMapper(UpdateUserContactRequest updateUserContactRequest);

    UserContactResponse userContactResponseMapper(UserContact userContact);
}
