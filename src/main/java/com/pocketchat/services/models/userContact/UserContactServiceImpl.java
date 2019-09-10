package com.pocketchat.services.models.userContact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repoServices.userContact.UserContactRepoService;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactServiceImpl implements UserContactService {
    private final UserContactRepoService userContactRepoService;

    @Autowired
    public UserContactServiceImpl(UserContactRepoService userContactRepoService) {
        this.userContactRepoService = userContactRepoService;
    }

    @Override
    public UserContact addUserContact(UserContact userContact) {
        // Check existing UserContact before add new unique UserContact
        Optional<UserContact> existingUserContact = userContactRepoService.findByMobileNo(userContact.getMobileNo());
        if(!existingUserContact.isPresent()) {
            return userContactRepoService.save(userContact);
        } else {
            // Merge UserContact by putting that user ID into the existing UserContact
            UserContact userContact1 = existingUserContact.get();
            List<String> currentUserIds = userContact1.getUserIds();
            boolean userContactHasSameUserId = currentUserIds.contains(userContact.getUserIds().get(0));
            if(!userContactHasSameUserId) {
                currentUserIds.add(userContact.getUserIds().get(0));
                userContact1.setUserIds(currentUserIds);
            }

            return userContactRepoService.save(userContact1);
        }
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
        Optional<UserContact> userContactOptional = userContactRepoService.findByMobileNo(mobileNo);
        return validateUserContactNotFound(userContactOptional, mobileNo);
    }

    private UserContact validateUserContactNotFound(Optional<UserContact> userContactOptional, String userContactId) {
        if (!userContactOptional.isPresent()) {
            throw new UserContactNotFoundException("userContactId-" + userContactId);
        } else {
            return userContactOptional.get();
        }
    }
}
