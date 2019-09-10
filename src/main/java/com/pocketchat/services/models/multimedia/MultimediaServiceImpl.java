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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        getSingleMultimedia(multimedia.getId());
        addMultimedia(multimedia);
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

    @Override
    public List<Multimedia> getMultimediaOfAUser(String userId) {
        System.out.println("MultimediaServiceImpl.java getMultimediaOfAUser()");
        System.out.println("MultimediaServiceImpl.java userId: " + userId);
        // Validate user first
        User user = userService.getUser(userId);
        // Get all conversations of the user
        List<ConversationGroup> conversationGroupList = conversationGroupService.getConversationsForUser(user.getId());
        System.out.println("MultimediaServiceImpl.java conversationGroupList.size(): " + conversationGroupList.size());
//        List<String> conversationGroupIds = new ArrayList<>();
        // Map conversationGroupList to a bunch of conversation group IDs
//        conversationGroupList.forEach(conversationGroup -> conversationGroupIds.add(conversationGroup.getId()));
        List<String> conversationGroupIds = conversationGroupList.stream()
                .map((ConversationGroup conversationGroup) -> conversationGroup.getId()).collect(Collectors.toList());
        System.out.println("MultimediaServiceImpl.java conversationGroupIds.size(): " + conversationGroupIds.size());
        // Get all multimedia from matching the list of conversationIds
        List<Multimedia> multimediaList = multimediaRepoService.findAllByConversationId(conversationGroupIds);
        System.out.println("MultimediaServiceImpl.java multimediaList.size(): " + multimediaList.size());
        return multimediaList;
    }

    @Override
    public List<Multimedia> getMultimediaOfAConversation(String conversationGroupId) {
        ConversationGroup conversationGroup = conversationGroupService.getSingleConversation(conversationGroupId);
        List<Multimedia> multimediaList = multimediaRepoService.findAllByConversationGroupId(conversationGroup.getId());
        return multimediaList;
    }

    private Multimedia validateMultimediaNotFound(Optional<Multimedia> multimediaOptional, String multimediaId) {
        if (!multimediaOptional.isPresent()) {
            throw new MultimediaNotFoundException("multimediaId-" + multimediaId);
        } else {
            return multimediaOptional.get();
        }
    }
}
