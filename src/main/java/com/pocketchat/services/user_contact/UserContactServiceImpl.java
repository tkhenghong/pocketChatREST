package com.pocketchat.services.user_contact;

import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.user_contact.UserContactRepoService;
import com.pocketchat.models.controllers.request.user_contact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;
import com.pocketchat.server.exceptions.user_contact.NotOwnUserContactException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.user.UserService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactServiceImpl implements UserContactService {

    private final UserContactRepoService userContactRepoService;

    private final UserAuthenticationService userAuthenticationService;

    private final UserService userService;

    @Autowired
    public UserContactServiceImpl(UserContactRepoService userContactRepoService,
                                  UserAuthenticationService userAuthenticationService,
                                  UserService userService) {
        this.userContactRepoService = userContactRepoService;
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserContact addUserContact(CreateUserContactRequest createUserContactRequest) {
        UserContact userContact = createUserContactRequestToUserContactMapper(createUserContactRequest);

        // Reason to put it here:
        // 1. Filter out all spaces, special characters with plus sign.
        // 2. To save the phone no without any custom format, like: 018-226 2663 (I don't want space) or +60182262663 (I don't want plus sign)
        String filteredMobileNo = userContact.getMobileNo().replaceAll("[-.^:,\\s+]", "");
        userContact.setMobileNo(filteredMobileNo);

        // Check existing UserContact before add new unique UserContact
        UserContact existingUserContact = userContactRepoService.findByMobileNo(filteredMobileNo);
        if (ObjectUtils.isEmpty(existingUserContact)) {
            return userContactRepoService.save(userContact);
        } else {
            // Merge UserContact by putting that user ID into the existing UserContact
            List<String> currentUserIds = existingUserContact.getUserIds();

            existingUserContact = checkAndMergeUserContact(existingUserContact, userContact);
            // This is for situation when another user added this mobile No during conversation creation
            boolean userContactHasSameUserId = currentUserIds.contains(userContact.getUserIds().get(0));
            if (!userContactHasSameUserId) {
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
            boolean userExist = userService.existById(userContact.getUserId());
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
    @Transactional
    public UserContact editOwnUserContact(UpdateUserContactRequest updateUserContactRequest) {
        getUserContact(updateUserContactRequest.getId());
        UserContact ownUserContact = getOwnUserContact();
        if (!updateUserContactRequest.getId().equals(ownUserContact.getId())) {
            throw new NotOwnUserContactException("Unable to edit UserContact due to not user's own UserContact. userContactId: " + updateUserContactRequest.getId());
        }

        return userContactRepoService.save(updateUserContactRequestToUserContactMapper(updateUserContactRequest));
    }

    @Override
    @Transactional
    public void deleteUserContact(String userContactId) {
        userContactRepoService.delete(getUserContact(userContactId));
    }

    @Override
    public UserContact getUserContact(String userContactId) {
        Optional<UserContact> userContactOptional = userContactRepoService.findById(userContactId);
        if (userContactOptional.isEmpty()) {
            throw new UserContactNotFoundException("userContactId-" + userContactId);
        }
        return userContactOptional.get();
    }

    /**
     * Find User Contact by using Mobile no.
     * @param mobileNo: Mobile number of a potential UserContact.
     * @return UserContact which has existing UserContact's information.
     */
    @Override
    public UserContact getUserContactByMobileNo(String mobileNo) {
        String filteredMobileNo = mobileNo.replaceAll("[-.^:,\\s+]", "");

        UserContact userContact = userContactRepoService.findByMobileNo(filteredMobileNo);
        if (ObjectUtils.isEmpty(userContact)) {
            throw new UserContactNotFoundException("UserContact not found: " + mobileNo);
        }
        return userContact;
    }

    @Override
    public UserContact getOwnUserContact() {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
        Optional<UserContact> userContactOptional = userContactRepoService.findByUserId(userAuthentication.getUserId());

        if (userContactOptional.isEmpty()) {
            throw new UserContactNotFoundException("Unable to find own UserContact. userId: " + userAuthentication.getUserId());
        }

        return userContactOptional.get();
    }

    @Override
    public List<UserContact> getUserContactsOfAUser() {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
        return userContactRepoService.findByUserIds(userAuthentication.getUserId());
    }

    @Override
    public UserContact createUserContactRequestToUserContactMapper(CreateUserContactRequest createUserContactRequest) {
        return UserContact.builder()
                .id(createUserContactRequest.getId())
                .displayName(createUserContactRequest.getDisplayName())
                .about(createUserContactRequest.getAbout())
                .block(createUserContactRequest.isBlock())
                .lastSeenDate(createUserContactRequest.getLastSeenDate())
                .mobileNo(createUserContactRequest.getMobileNo())
                .multimediaId(createUserContactRequest.getMultimediaId())
                .realName(createUserContactRequest.getRealName())
                .userId(createUserContactRequest.getUserId())
                .userIds(createUserContactRequest.getUserIds())
                .build();
    }

    @Override
    public UserContact updateUserContactRequestToUserContactMapper(UpdateUserContactRequest updateUserContactRequest) {
        return UserContact.builder()
                .id(updateUserContactRequest.getId())
                .displayName(updateUserContactRequest.getDisplayName())
                .about(updateUserContactRequest.getAbout())
                .block(updateUserContactRequest.isBlock())
                .lastSeenDate(updateUserContactRequest.getLastSeenDate())
                .mobileNo(updateUserContactRequest.getMobileNo())
                .multimediaId(updateUserContactRequest.getMultimediaId())
                .realName(updateUserContactRequest.getRealName())
                .userId(updateUserContactRequest.getUserId())
                .userIds(updateUserContactRequest.getUserIds())
                .build();
    }

    @Override
    public UserContactResponse userContactResponseMapper(UserContact userContact) {
        return UserContactResponse.builder()
                .id(userContact.getId())
                .displayName(userContact.getDisplayName())
                .realName(userContact.getRealName())
                .about(userContact.getAbout())
                .block(userContact.isBlock())
                .mobileNo(userContact.getMobileNo())
                .multimediaId(userContact.getMultimediaId())
                .userIds(userContact.getUserIds())
                .userId(userContact.getUserId())
                .lastSeenDate(userContact.getLastSeenDate())
                .build();
    }
}
