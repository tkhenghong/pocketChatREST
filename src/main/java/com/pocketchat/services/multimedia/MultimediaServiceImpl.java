package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.repo_services.multimedia.MultimediaRepoService;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.server.exceptions.general.StringNotEmptyException;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.user.UserService;
import com.pocketchat.services.user_authentication.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class MultimediaServiceImpl implements MultimediaService {

    private final MultimediaRepoService multimediaRepoService;

    private final ConversationGroupService conversationGroupService;

    private final UserService userService;

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public MultimediaServiceImpl(MultimediaRepoService multimediaRepoService,
                                 ConversationGroupService conversationGroupService,
                                 UserService userService,
                                 UserAuthenticationService userAuthenticationService) {
        this.multimediaRepoService = multimediaRepoService;
        this.conversationGroupService = conversationGroupService;
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    @Transactional
    public Multimedia addMultimedia(Multimedia multimedia) {
        if (!StringUtils.isEmpty(multimedia.getId())) {
            // Prevent multimedia from updated using the wrong method.
            throw new StringNotEmptyException("Unable to add multimedia that has already exist. multimediaId: " + multimedia.getId());
        }
        return multimediaRepoService.save(multimedia);
    }

    @Override
    @Transactional
    public Multimedia editMultimedia(Multimedia multimedia) {
        getSingleMultimedia(multimedia.getId());
        return multimediaRepoService.save(multimedia); // Straight save
    }

    @Override
    @Transactional
    public void deleteMultimedia(String multimediaId) {
        multimediaRepoService.delete(getSingleMultimedia(multimediaId));
    }

    @Override
    public Multimedia getSingleMultimedia(String multimediaId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimediaId);
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with multimediaId: " + multimediaId);
        }
        return multimediaOptional.get();
    }

    // Find a User's multimedia (Profile picture)
    @Override
    public Multimedia getMultimediaOfAUser(String userId) {
//        User user = userService.getUser(userId); // Validate user first
//        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserId(user.getId());
//        if (multimediaOptional.isEmpty()) {
//            throw new MultimediaNotFoundException("Unable to find multimedia with userId: " + userId);
//        }
//        return multimediaOptional.get();
        return null;
    }

    @Override
    public Multimedia getUserOwnProfilePictureMultimedia() {
//        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
//        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserId(userAuthentication.getUserId());
//        if (multimediaOptional.isEmpty()) {
//            throw new MultimediaNotFoundException("Unable to find multimedia with userAuthentication.getUserId(): " + userAuthentication.getUserId());
//        }
//        return multimediaOptional.get();
        return null;
    }

    // Find a UserContact's multimedia
    @Override
    public Multimedia getMultimediaOfAUserContact(String userContactId) {
//        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserContactId(userContactId);
//        if (multimediaOptional.isEmpty()) {
//            throw new MultimediaNotFoundException("Unable to find multimedia with userContactId: " + userContactId);
//        }
//        return multimediaOptional.get();
        return null;
    }

    // Find a Conversation Group's multimedia
    @Override
    public Multimedia getConversationGroupMultimedia(String conversationGroupId) {
//        Optional<Multimedia> multimediaOptional = multimediaRepoService.findGroupPhotoMultimedia(conversationGroupId);
//        if (multimediaOptional.isEmpty()) {
//            throw new MultimediaNotFoundException("Unable to find multimedia with conversationGroupId: " + conversationGroupId);
//        }
//        return multimediaOptional.get();
        return null;
    }

    // Find a Message's multimedia
    @Override
    public Multimedia getMessageMultimedia(String conversationGroupId, String messageId) {
//        Optional<Multimedia> multimediaOptional = multimediaRepoService.findMessageMultimedia(conversationGroupId, messageId);
//        if (multimediaOptional.isEmpty()) {
//            throw new MultimediaNotFoundException("Unable to find multimedia with messageId-" + messageId);
//        }
//        return multimediaOptional.get();
        return null;
    }

    @Override
    public List<Multimedia> getMultimediaOfAConversation(String conversationGroupId) {
//        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(conversationGroupId);
//        List<Multimedia> multimediaList = multimediaRepoService.findAllByConversationGroupId(conversationGroup.getId());
//        if (multimediaList.isEmpty()) {
//            // To tell no multimedia is found using this conversationGroupId.
//            throw new MultimediaNotFoundException("Unable to find multimedia of this conversationGroupId: " + conversationGroupId);
//        }
//        return multimediaList;
        return null;
    }

    @Override
    public MultimediaResponse multimediaResponseMapper(Multimedia multimedia) {
        return MultimediaResponse.builder()
//                .id(multimedia.getId())
//                .conversationId(multimedia.getConversationId())
//                .fileSize(multimedia.getFileSize())
//                .localFullFileUrl(multimedia.getLocalFullFileUrl())
//                .localThumbnailUrl(multimedia.getLocalThumbnailUrl())
//                .messageId(multimedia.getMessageId())
//                .remoteFullFileUrl(multimedia.getRemoteFullFileUrl())
//                .remoteThumbnailUrl(multimedia.getRemoteThumbnailUrl())
//                .userContactId(multimedia.getUserContactId())
//                .userId(multimedia.getUserId())
                .build();

    }
}
