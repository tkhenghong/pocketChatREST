package com.pocketchat.services.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.models.controllers.request.user_contact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface UserContactService {
    UserContact addUserContact(CreateUserContactRequest userContact);

    UserContact editOwnUserContact(UpdateUserContactRequest userContact);

    MultimediaResponse uploadOwnUserContactProfilePhoto(MultipartFile multipartFile);

    File getOwnUserContactProfilePhoto() throws FileNotFoundException;

    File getUserContactProfilePhoto(String userContactId) throws FileNotFoundException;

    void deleteOwnUserContactProfilePhoto();

    void deleteOwnUserContact(String userContactId);

    UserContact getUserContact(String userContactId);

    UserContact getUserContactByMobileNo(String mobileNo);

    UserContact getOwnUserContact();

    List<UserContact> getUserContactsOfAUser();

    UserContact createUserContactRequestToUserContactMapper(CreateUserContactRequest createUserContactRequest);

    UserContact updateUserContactRequestToUserContactMapper(UpdateUserContactRequest updateUserContactRequest);

    UserContactResponse userContactResponseMapper(UserContact userContact);
}
