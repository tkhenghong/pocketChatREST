package com.pocketchat.services.user_contact;

import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.user.UserRepoService;
import com.pocketchat.db.repo_services.user_contact.UserContactRepoService;
import com.pocketchat.models.controllers.request.user_contact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public UserContactResponse addUserContact(CreateUserContactRequest createUserContactRequest) {
        UserContact userContact = createUserContactRequestToUserContactMapper(createUserContactRequest);

        // Reason to put it here:
        // 1. Filter out all spaces, special characters with plus sign.
        // 2. To save the phone no without any custom format, like: 018-226 2663 (I don't want space) or +60182262663 (I don't want plus sign)
        String filteredMobileNo = userContact.getMobileNo().replaceAll("[-.^:,\\s+]", "");
        userContact.setMobileNo(filteredMobileNo);

        // Check existing UserContact before add new unique UserContact
        UserContact existingUserContact = userContactRepoService.findByMobileNo(filteredMobileNo);
        if (ObjectUtils.isEmpty(existingUserContact)) {
            return userContactResponseMapper(userContactRepoService.save(userContact));
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

                return userContactResponseMapper(userContactRepoService.save(existingUserContact));
            }

            return userContactResponseMapper(existingUserContact);
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
    @Transactional
    public UserContactResponse editUserContact(UpdateUserContactRequest updateUserContactRequest) {
        getUserContact(updateUserContactRequest.getId());
        return userContactResponseMapper(userContactRepoService.save(updateUserContactRequestToUserContactMapper(updateUserContactRequest)));
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

    @Override
    public UserContact getUserContactByMobileNo(String mobileNo) {
        String filteredMobileNo = mobileNo.replaceAll("[-.^:,\\s+]", "");
        return userContactRepoService.findByMobileNo(filteredMobileNo);
    }

    @Override
    public List<UserContactResponse> getUserContactsByUserId(String userId) {
        List<UserContact> userContactList = userContactRepoService.findByUserIds(userId);
        return userContactList.stream().map(this::userContactResponseMapper).collect(Collectors.toList());
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
                .lastSeenDate(userContact.getLastSeenDate().getMillis())
                .build();
    }
}
