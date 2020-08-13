package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_authentication.UserAuthentication;
import com.pocketchat.db.repo_services.multimedia.MultimediaRepoService;
import com.pocketchat.models.controllers.request.multimedia.CreateMultimediaRequest;
import com.pocketchat.models.controllers.request.multimedia.UpdateMultimediaRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
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
    public MultimediaResponse addMultimedia(CreateMultimediaRequest createMultimediaRequest) {
        Multimedia multimedia = createMultimediaRequestToMultimediaMapper(createMultimediaRequest);
        Optional<Multimedia> multimediaOptional = Optional.empty();

        if (isUserMultimedia(multimedia)) {
            multimediaOptional = multimediaRepoService.findByUserId(multimedia.getUserId());
        } else if (isUserContactMultimedia(multimedia)) {
            multimediaOptional = multimediaRepoService.findByUserContactId(multimedia.getUserContactId());
        } else if (isGroupPhotoMultimedia(multimedia)) {
            multimediaOptional = multimediaRepoService.findGroupPhotoMultimedia(multimedia.getConversationId());
        } else if (isMessageMultimedia(multimedia)) {
            multimediaOptional = multimediaRepoService.findMessageMultimedia(multimedia.getConversationId(), multimedia.getMessageId());
        }

        return multimediaResponseMapper(multimediaOptional.orElseGet(() -> multimediaRepoService.save(multimedia)));
    }

    @Override
    @Transactional
    public MultimediaResponse editMultimedia(UpdateMultimediaRequest updateMultimediaRequest) {
        Multimedia multimedia = updateMultimediaRequestToMultimediaMapper(updateMultimediaRequest);
        getSingleMultimedia(multimedia.getId());
        return multimediaResponseMapper(multimediaRepoService.save(multimedia)); // Straight save
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
        User user = userService.getUser(userId); // Validate user first
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserId(user.getId());
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with userId: " + userId);
        }
        return multimediaOptional.get();
    }

    @Override
    public Multimedia getUserOwnProfilePictureMultimedia() {
        UserAuthentication userAuthentication = userAuthenticationService.getOwnUserAuthentication();
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserId(userAuthentication.getUserId());
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with userAuthentication.getUserId(): " + userAuthentication.getUserId());
        }
        return multimediaOptional.get();
    }

    // Find a UserContact's multimedia
    @Override
    public Multimedia getMultimediaOfAUserContact(String userContactId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserContactId(userContactId);
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with userContactId: " + userContactId);
        }
        return multimediaOptional.get();
    }

    // Find a Conversation Group's multimedia
    @Override
    public Multimedia getConversationGroupMultimedia(String conversationGroupId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findGroupPhotoMultimedia(conversationGroupId);
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with conversationGroupId: " + conversationGroupId);
        }
        return multimediaOptional.get();
    }

    // Find a Message's multimedia
    @Override
    public Multimedia getMessageMultimedia(String conversationGroupId, String messageId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findMessageMultimedia(conversationGroupId, messageId);
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with messageId-" + messageId);
        }
        return multimediaOptional.get();
    }

    @Override
    public List<Multimedia> getMultimediaOfAConversation(String conversationGroupId) {
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(conversationGroupId);
        List<Multimedia> multimediaList = multimediaRepoService.findAllByConversationGroupId(conversationGroup.getId());
        if (multimediaList.isEmpty()) {
            // To tell no multimedia is found using this conversationGroupId.
            throw new MultimediaNotFoundException("Unable to find multimedia of this conversationGroupId: " + conversationGroupId);
        }
        return multimediaList;
    }

    boolean isUserMultimedia(Multimedia multimedia) {
        return !StringUtils.isEmpty(multimedia.getUserId());
    }

    boolean isUserContactMultimedia(Multimedia multimedia) {
        return !StringUtils.isEmptyOrWhitespace(multimedia.getUserContactId());
    }

    boolean isGroupPhotoMultimedia(Multimedia multimedia) {
        return !StringUtils.isEmptyOrWhitespace(multimedia.getConversationId()) && StringUtils.isEmptyOrWhitespace(multimedia.getMessageId());
    }

    boolean isMessageMultimedia(Multimedia multimedia) {
        return !StringUtils.isEmptyOrWhitespace(multimedia.getConversationId()) && !StringUtils.isEmptyOrWhitespace(multimedia.getMessageId());
    }

    @Override
    public Multimedia createMultimediaRequestToMultimediaMapper(CreateMultimediaRequest createMultimediaRequest) {
        return Multimedia.builder()
                .id(createMultimediaRequest.getId())
                .conversationId(createMultimediaRequest.getConversationId())
                .localFullFileUrl(createMultimediaRequest.getLocalFullFileUrl())
                .localThumbnailUrl(createMultimediaRequest.getLocalThumbnailUrl())
                .messageId(createMultimediaRequest.getMessageId())
                .remoteFullFileUrl(createMultimediaRequest.getRemoteFullFileUrl())
                .remoteThumbnailUrl(createMultimediaRequest.getRemoteThumbnailUrl())
                .size(createMultimediaRequest.getFileSize())
                .userContactId(createMultimediaRequest.getUserContactId())
                .userId(createMultimediaRequest.getUserId())
                .build();
    }

    @Override
    public Multimedia updateMultimediaRequestToMultimediaMapper(UpdateMultimediaRequest updateMultimediaRequest) {
        return Multimedia.builder()
                .id(updateMultimediaRequest.getId())
                .conversationId(updateMultimediaRequest.getConversationId())
                .localFullFileUrl(updateMultimediaRequest.getLocalFullFileUrl())
                .localThumbnailUrl(updateMultimediaRequest.getLocalThumbnailUrl())
                .messageId(updateMultimediaRequest.getMessageId())
                .remoteFullFileUrl(updateMultimediaRequest.getRemoteFullFileUrl())
                .remoteThumbnailUrl(updateMultimediaRequest.getRemoteThumbnailUrl())
                .size(updateMultimediaRequest.getFileSize())
                .userContactId(updateMultimediaRequest.getUserContactId())
                .userId(updateMultimediaRequest.getUserId())
                .build();
    }

    @Override
    public MultimediaResponse multimediaResponseMapper(Multimedia multimedia) {
        return MultimediaResponse.builder()
                .id(multimedia.getId())
                .conversationId(multimedia.getConversationId())
                .fileSize(multimedia.getFileSize())
                .localFullFileUrl(multimedia.getLocalFullFileUrl())
                .localThumbnailUrl(multimedia.getLocalThumbnailUrl())
                .messageId(multimedia.getMessageId())
                .remoteFullFileUrl(multimedia.getRemoteFullFileUrl())
                .remoteThumbnailUrl(multimedia.getRemoteThumbnailUrl())
                .userContactId(multimedia.getUserContactId())
                .userId(multimedia.getUserId())
                .build();

    }
}
