package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repoServices.multimedia.MultimediaRepoService;
import com.pocketchat.db.repoServices.user.UserRepoService;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultimediaServiceImpl implements MultimediaService {
    private final MultimediaRepoService multimediaRepoService;
    private final UserRepoService userRepoService;
    private final ConversationGroupRepoService conversationGroupRepoService;

    @Autowired
    public MultimediaServiceImpl(MultimediaRepoService multimediaRepoService, UserRepoService userRepoService, ConversationGroupRepoService conversationGroupRepoService) {
        this.multimediaRepoService = multimediaRepoService;
        this.userRepoService = userRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
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
    public List<Multimedia> getMultimediaOfAUser(String userId) {
        // Go to the user's conversation groups and get all multimedia, merge them to list and send back to front end
        // TODO: Change this part to UserService.getUser****
        Optional<User> userOptional = userRepoService.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found. userId:-" + userId);
        }
        // TDDO: END ***************************

        // This part related to getConversationForUser in MultimediaController class.
        // Make all implementations of ConversationGroupController into a service class
        // To bring all conversationIds to here, and search through multimedia table for all multimedias
//        multimediaRepoService.findBy
//        userOptional
        return null;
    }

    @Override
    public List<Multimedia> getMultimediaOfAConversation(String conversationId) {
        return null;
    }

    private void validateMultimediaNotFound(Optional<Multimedia> multimediaOptional, String multimediaId) {
        if (!multimediaOptional.isPresent()) {
            throw new MultimediaNotFoundException("multimediaId-" + multimediaId);
        }
    }
}
