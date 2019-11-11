package com.pocketchat.services.models.userContact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactServiceImpl implements UserContactService {
    private final UserContactRepoService userContactRepoService;
    private final UserRepoService userRepoService;

    @Autowired
    public UserContactServiceImpl(UserContactRepoService userContactRepoService, UserRepoService userRepoService) {
        this.userContactRepoService = userContactRepoService;
        this.userRepoService = userRepoService;
    }

    @Override
    public UserContact addUserContact(UserContact userContact) {
        System.out.println("UserContactServiceImpl.java addUserContact()");
        // Check existing UserContact before add new unique UserContact
        UserContact existingUserContact = userContactRepoService.findByMobileNo(userContact.getMobileNo());
        if (ObjectUtils.isEmpty(existingUserContact)) {
            System.out.println("if (ObjectUtils.isEmpty(existingUserContact))");
            return userContactRepoService.save(userContact);
        } else {
            System.out.println("if (!ObjectUtils.isEmpty(existingUserContact))");
            // Merge UserContact by putting that user ID into the existing UserContact
            List<String> currentUserIds = existingUserContact.getUserIds();

            existingUserContact = checkUserContactMobileNumber(existingUserContact, userContact);

            // This is for situation when another user added this mobile No during conversation creation
            // When that unknown number (for that user) is created and sent to here. There's only 1 User ID in userIds
            boolean userContactHasSameUserId = currentUserIds.contains(userContact.getUserIds().get(0));
            if (!userContactHasSameUserId) {
                currentUserIds.add(userContact.getUserIds().get(0));
                existingUserContact.setUserIds(currentUserIds);
            }

            return userContactRepoService.save(existingUserContact);
        }
    }

    private UserContact checkUserContactMobileNumber(UserContact existingUserContact, UserContact userContact) {
        boolean userContactUserIdIsEmpty = StringUtils.isEmpty(existingUserContact.getUserId());
        boolean existingUserContactUserIdIsEmpty = StringUtils.isEmpty(userContact.getUserId());
        boolean userContactMobileNoIsEmpty = StringUtils.isEmpty(userContact.getMobileNo());

        // This indicates that this user doesn't belonged to any user yet, and frontend has created a new user
        if (userContactUserIdIsEmpty && !existingUserContactUserIdIsEmpty) {
            boolean userExist = userRepoService.existById(userContact.getUserId());
            if (userExist) {
                existingUserContact.setUserId(userContact.getUserId());
                if (!userContactMobileNoIsEmpty) {
                    existingUserContact.setMobileNo(userContact.getMobileNo());
                }
            }
        }

        /* TODO: Check if 2 userContacts have mobile number, check which one has country code.
             If one have country code but another one don't have, remove - and spaces,
              and set them as a new mobile number */

        return userContact;
    }

    @Override
    public void editUserContact(UserContact userContact) {
        getUserContact(userContact.getId());
        addUserContact(userContact);
    }

    @Override
    public void deleteUserContact(String userContactId) {
        userContactRepoService.delete(getUserContact(userContactId));
    }

    @Override
    public UserContact getUserContact(String userContactId) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContactId);
        return validateUserContactNotFound(userContactOptional, userContactId);
    }

    @Override
    public UserContact getUserContactByMobileNo(String mobileNo) {
        UserContact userContact = userContactRepoService.findByMobileNo(mobileNo);
        if (ObjectUtils.isEmpty(userContact)) {
            throw new UserContactNotFoundException("userContactId-" + userContact.getId());
        }
        return userContact;
    }

    @Override
    public List<UserContact> getUserContactsByUserId(String userId) {
        List<UserContact> userContactList = userContactRepoService.findByUserIds(userId);
        return userContactList;
    }

    private UserContact validateUserContactNotFound(Optional<UserContact> userContactOptional, String userContactId) {
        if (!userContactOptional.isPresent()) {
            throw new UserContactNotFoundException("userContactId-" + userContactId);
        } else {
            return userContactOptional.get();
        }
    }
}
