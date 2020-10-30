package com.pocketchat.services.user_contact;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.user_contact.UserContactRepoService;
import com.pocketchat.models.controllers.request.user_contact.CreateUserContactRequest;
import com.pocketchat.models.controllers.request.user_contact.UpdateUserContactRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.user_contact.UserContactResponse;
import com.pocketchat.server.exceptions.file.UploadFileException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.multimedia.MultimediaService;
import com.pocketchat.services.user.UserService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import com.pocketchat.utils.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserContactServiceImpl implements UserContactService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserContactRepoService userContactRepoService;

    private final UserAuthenticationService userAuthenticationService;

    private final UserService userService;

    private final MultimediaService multimediaService;

    FileUtil fileUtil;

    private final String moduleDirectory = "userContact";

    @Autowired
    public UserContactServiceImpl(UserContactRepoService userContactRepoService,
                                  UserAuthenticationService userAuthenticationService,
                                  UserService userService,
                                  MultimediaService multimediaService,
                                  FileUtil fileUtil) {
        this.userContactRepoService = userContactRepoService;
        this.userAuthenticationService = userAuthenticationService;
        this.userService = userService;
        this.multimediaService = multimediaService;
        this.fileUtil = fileUtil;
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
        UserContact ownUserContact = getOwnUserContact();

        UserContact updatedUserContact = updateUserContactRequestToUserContactMapper(updateUserContactRequest);
        updatedUserContact.setId(ownUserContact.getId());

        return userContactRepoService.save(updatedUserContact);
    }

    @Override
    public MultimediaResponse uploadOwnUserContactProfilePhoto(MultipartFile multipartFile) {
        UserContact ownUserContact = getOwnUserContact();
        Multimedia savedMultimedia;

        if (StringUtils.hasText(ownUserContact.getProfilePicture())) {
            multimediaService.deleteMultimedia(ownUserContact.getProfilePicture(), moduleDirectory);
        }

        try {
            savedMultimedia = multimediaService.addMultimedia(fileUtil.createMultimedia(multipartFile, moduleDirectory));
        } catch (IOException ioException) {
            throw new UploadFileException("Unable to upload user contact multimedia to the server! userContactId: " + ownUserContact.getId() + ", message: " + ioException.getMessage());
        }

        if (ObjectUtils.isEmpty(savedMultimedia)) {
            throw new UploadFileException("Unable to upload user contact multimedia to the server due to savedMultimedia object is empty! userContactId: " + ownUserContact.getId());
        }

        return multimediaService.multimediaResponseMapper(savedMultimedia);
    }

    /**
     * Get own UserContact Profile Photo, without need to provide userContactId.
     *
     * @return File object.
     * @throws FileNotFoundException: Throws the exception when the file is not physically on the server.
     */
    @Override
    public File getOwnUserContactProfilePhoto() throws FileNotFoundException {
        Multimedia multimedia = multimediaService.getSingleMultimedia(getOwnUserContact().getProfilePicture());
        return fileUtil.getFileWithAbsolutePath(moduleDirectory, multimedia.getFileDirectory(), multimedia.getFileName());
    }

    /**
     * Get own UserContact Profile Photo, without need to provide userContactId.
     *
     * @param userContactId: ID of the UserContact object.
     * @return File object.
     * @throws FileNotFoundException: Throws the exception when the file is not physically on the server.
     */
    @Override
    public File getUserContactProfilePhoto(String userContactId) throws FileNotFoundException {
        Multimedia multimedia = multimediaService.getSingleMultimedia(getUserContact(userContactId).getProfilePicture());
        return fileUtil.getFileWithAbsolutePath(moduleDirectory, multimedia.getFileDirectory(), multimedia.getFileName());
    }

    @Override
    public void deleteOwnUserContactProfilePhoto() {
        multimediaService.deleteMultimedia(getOwnUserContact().getProfilePicture(), moduleDirectory);
    }

    @Override
    @Transactional
    public void deleteOwnUserContact(String userContactId) {
        userContactRepoService.delete(getOwnUserContact());
        deleteOwnUserContactProfilePhoto();
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
     *
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
    public Page<UserContact> getUserContactsOfAUser(String searchTerm, Pageable pageable) {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
        return userContactRepoService.findByUserIds(userAuthentication.getUserId(), searchTerm, pageable);
    }

    @Override
    public UserContact createUserContactRequestToUserContactMapper(CreateUserContactRequest createUserContactRequest) {
        return UserContact.builder()
                .displayName(createUserContactRequest.getDisplayName())
                .about(createUserContactRequest.getAbout())
                .mobileNo(createUserContactRequest.getMobileNo())
                .countryCode(createUserContactRequest.getCountryCode())
                .profilePicture(createUserContactRequest.getProfilePhoto())
                .realName(createUserContactRequest.getRealName())
                .userId(createUserContactRequest.getUserId())
                .userIds(createUserContactRequest.getUserIds())
                .build();
    }

    @Override
    public UserContact updateUserContactRequestToUserContactMapper(UpdateUserContactRequest updateUserContactRequest) {
        return UserContact.builder()
                .displayName(updateUserContactRequest.getDisplayName())
                .about(updateUserContactRequest.getAbout())
                .realName(updateUserContactRequest.getRealName())
                .profilePicture(updateUserContactRequest.getProfilePhoto())
                .build();
    }

    @Override
    public UserContactResponse userContactResponseMapper(UserContact userContact) {
        return UserContactResponse.builder()
                .id(userContact.getId())
                .displayName(userContact.getDisplayName())
                .realName(userContact.getRealName())
                .about(userContact.getAbout())
                .mobileNo(userContact.getMobileNo())
                .countryCode(userContact.getCountryCode())
                .profilePicture(userContact.getProfilePicture())
                .userIds(userContact.getUserIds())
                .userId(userContact.getUserId())
                .createdBy(userContact.getCreatedBy())
                .createdDate(userContact.getCreatedDate())
                .lastModifiedBy(userContact.getLastModifiedBy())
                .lastModifiedDate(userContact.getLastModifiedDate())
                .version(userContact.getVersion())
                .build();
    }

    @Override
    public Page<UserContactResponse> userContactPageResponseMapper(Page<UserContact> userContacts) {
        return userContacts.map(this::userContactResponseMapper);
    }
}
