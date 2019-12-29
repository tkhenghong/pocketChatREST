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
        System.out.println("UserContactServiceImpl.java userContact: " + userContact.toString());
        System.out.println("UserContactServiceImpl.java userContact: " + userContact.toString());

        // Reason to put it here:
        // 1. Filter out all spaces, special characters with plus sign.
        // 2. To save the phone no without any custom format, like: 018-226 2663 (I don't want space) or +60182262663 (I don't want plus sign)
        String filteredMobileNo = userContact.getMobileNo().replaceAll("[-.^:,\\s+]", "");
        userContact.setMobileNo(filteredMobileNo);

        // Check existing UserContact before add new unique UserContact
        UserContact existingUserContact = userContactRepoService.findByMobileNo(filteredMobileNo);
        if (ObjectUtils.isEmpty(existingUserContact)) {
            System.out.println("UserContactServiceImpl.java if (ObjectUtils.isEmpty(existingUserContact))");
            return userContactRepoService.save(userContact);
        } else {
            System.out.println("UserContactServiceImpl.java if (!ObjectUtils.isEmpty(existingUserContact))");
            // Merge UserContact by putting that user ID into the existing UserContact
            List<String> currentUserIds = existingUserContact.getUserIds();

            existingUserContact = checkAndMergeUserContact(existingUserContact, userContact);
            System.out.println("UserContactServiceImpl.java currentUserIds: " + currentUserIds.toString());
            System.out.println("UserContactServiceImpl.java existingUserContact: " + existingUserContact.toString());
            // This is for situation when another user added this mobile No during conversation creation
            boolean userContactHasSameUserId = currentUserIds.contains(userContact.getUserIds().get(0));
            System.out.println("UserContactServiceImpl.java userContactHasSameUserId: " + userContactHasSameUserId);
            if (!userContactHasSameUserId) {
                System.out.println("UserContactServiceImpl.java if (!userContactHasSameUserId)");
                // When that unknown number (for that user) is created and sent to here. There's only 1 User ID in userIds
                currentUserIds.add(userContact.getUserIds().get(0));
                existingUserContact.setUserIds(currentUserIds);

                return userContactRepoService.save(existingUserContact);
            }

            return existingUserContact;
        }
    }

    private UserContact checkAndMergeUserContact(UserContact existingUserContact, UserContact userContact) {
        boolean userContactUserIdIsEmpty = StringUtils.isEmpty(userContact.getUserId());
        boolean existingUserContactUserIdIsEmpty = StringUtils.isEmpty(existingUserContact.getUserId());
        boolean userContactMobileNoIsEmpty = StringUtils.isEmpty(userContact.getMobileNo());
        boolean existingUserContactMobileNumberHasPlusSign = userContact.getMobileNo().contains("+");

        // Check the newcomer UserContact has UserId or not. If have check existence. If yes, add it into existingUserContact object.
        if (!userContactUserIdIsEmpty && existingUserContactUserIdIsEmpty) {
            boolean userExist = userRepoService.existById(userContact.getUserId());
            if (userExist) {
                existingUserContact.setUserId(userContact.getUserId());
            }
        }

        // Check existingUserContact Mobile Number has plus sign or not
        if (!userContactMobileNoIsEmpty && !existingUserContactMobileNumberHasPlusSign) {
            // Filter out spaces, special characters but remains plus sign
            String filteredMobileNo = userContact.getMobileNo().replaceAll("[-.^:,]", "");
            existingUserContact.setMobileNo(filteredMobileNo);
        }

        return existingUserContact;
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
        String filteredMobileNo = mobileNo.replaceAll("[-.^:,\\s+]", "");
        UserContact userContact = userContactRepoService.findByMobileNo(filteredMobileNo);
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
