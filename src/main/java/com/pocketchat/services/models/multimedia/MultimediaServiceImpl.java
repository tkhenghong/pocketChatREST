package com.pocketchat.services.models.multimedia;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.multimedia.MultimediaRepoService;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.services.models.conversationGroup.ConversationGroupService;
import com.pocketchat.services.models.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class MultimediaServiceImpl implements MultimediaService {
    private final MultimediaRepoService multimediaRepoService;

    private final ConversationGroupService conversationGroupService;
    private final UserService userService;

    @Autowired
    public MultimediaServiceImpl(MultimediaRepoService multimediaRepoService, ConversationGroupService conversationGroupService, UserService userService) {
        this.multimediaRepoService = multimediaRepoService;
        this.conversationGroupService = conversationGroupService;
        this.userService = userService;
    }

    @Override
    public Multimedia addMultimedia(Multimedia multimedia) {

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

        return multimediaOptional.orElseGet(() -> multimediaRepoService.save(multimedia));
    }

    @Override
    public void editMultimedia(Multimedia multimedia) {
        getSingleMultimedia(multimedia.getId());
        multimediaRepoService.save(multimedia); // Straight save
    }

    @Override
    public void deleteMultimedia(String multimediaId) {
        multimediaRepoService.delete(getSingleMultimedia(multimediaId));
    }

    @Override
    public Multimedia getSingleMultimedia(String multimediaId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimediaId);
        return validateMultimediaNotFound(multimediaOptional, multimediaId);
    }


    // Find a User's multimedia
    @Override
    public Multimedia getMultimediaOfAUser(String userId) {
        User user = userService.getUser(userId); // Validate user first
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserId(user.getId());
        return validateMultimediaNotFound(multimediaOptional, user.getId());
    }

    // Find a UserContact's multimedia
    @Override
    public Multimedia getMultimediaOfAUserContact(String userContactId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findByUserContactId(userContactId);
        return validateMultimediaNotFound(multimediaOptional, userContactId);
    }

    // Find a Conversation Group's multimedia
    @Override
    public Multimedia getConversationGroupMultimedia(String conversationGroupId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findGroupPhotoMultimedia(conversationGroupId);
        return validateMultimediaNotFound(multimediaOptional, conversationGroupId);
    }

    // Find a Message's multimedia
    @Override
    public Multimedia getMessageMultimedia(String conversationGroupId, String messageId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findMessageMultimedia(conversationGroupId, messageId);
        return validateMultimediaNotFound(multimediaOptional, messageId);
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

    private Multimedia validateMultimediaNotFound(Optional<Multimedia> multimediaOptional, String multimediaId) {
        if (!multimediaOptional.isPresent()) {
            throw new MultimediaNotFoundException("multimediaId-" + multimediaId);
        } else {
            return multimediaOptional.get();
        }
    }
}
