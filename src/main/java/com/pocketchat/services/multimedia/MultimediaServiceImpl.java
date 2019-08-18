package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.multimedia.MultimediaRepoService;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.services.conversationGroup.ConversationGroupService;
import com.pocketchat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return multimediaRepoService.save(multimedia);
    }

    @Override
    public void editMultimedia(Multimedia multimedia) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimedia.getId());
        validateMultimediaNotFound(multimediaOptional, multimedia.getId());
        multimediaRepoService.save(multimedia);
    }

    @Override
    public void deleteMultimedia(String multimediaId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimediaId);
        validateMultimediaNotFound(multimediaOptional, multimediaId);
        multimediaRepoService.delete(multimediaOptional.get());
    }

    @Override
    public Multimedia getSingleMultimedia(String multimediaId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimediaId);
        validateMultimediaNotFound(multimediaOptional, multimediaId);
        return multimediaOptional.get();
    }

    @Override
    public List<Multimedia> getMultimediaOfAUser(String userId) {
        // Validate user first
        User user = userService.getUser(userId);
        // Get all conversations of the user
        List<ConversationGroup> conversationGroupList = conversationGroupService.getConversationsForUser(user.getId());
        List<String> conversationGroupIds = new ArrayList<>();
        // Map conversationGroupList to a bunch of conversation group IDs
        conversationGroupList.forEach(conversationGroup -> conversationGroupIds.add(conversationGroup.getId()));
        // Get all multimedia from matching the list of conversationIds
        List<Multimedia> multimediaList = multimediaRepoService.findAllByConversationId(conversationGroupIds);
        return multimediaList;
    }

    @Override
    public List<Multimedia> getMultimediaOfAConversation(String conversationGroupId) {
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(conversationGroupId);
        List<Multimedia> multimediaList = multimediaRepoService.findAllByConversationGroupId(conversationGroup.getId());
        return multimediaList;
    }

    private void validateMultimediaNotFound(Optional<Multimedia> multimediaOptional, String multimediaId) {
        if (!multimediaOptional.isPresent()) {
            throw new MultimediaNotFoundException("multimediaId-" + multimediaId);
        }
    }
}
