package com.pocketchat.controllers.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.services.multimedia.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

// CUD will not able to be made in MultimediaController. They have to change it through their own components.

    @GetMapping("/{multimediaId}")
    public MultimediaResponse getSingleMultimedia(@PathVariable String multimediaId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getSingleMultimedia(multimediaId));
    }

    @GetMapping("/user/{userId}")
    public MultimediaResponse getMultimediaOfAUser(@PathVariable String userId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getMultimediaOfAUser(userId));
    }

    @GetMapping("/user")
    public MultimediaResponse getUserOwnProfilePictureMultimedia() {
        return multimediaService.multimediaResponseMapper(multimediaService.getUserOwnProfilePictureMultimedia());
    }

    // Get multimedia of the UserContact (One at the time)
    @GetMapping("/userContact/{userContactId}")
    public MultimediaResponse getMultimediaOfAUserContact(@PathVariable String userContactId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getMultimediaOfAUserContact(userContactId));
    }

    // Get the multimedia of the Group Photo (One at the time)
    @GetMapping("/conversationGroup/photo/{conversationGroupId}")
    public MultimediaResponse getConversationGroupPhotoMultimedia(@PathVariable String conversationGroupId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getConversationGroupMultimedia(conversationGroupId));
    }

    // Retrieve the multimedia of the message if frontend user doesn't have it
    @GetMapping("/message/{conversationGroupId}/{messageId}")
    public MultimediaResponse getMessageMultimedia(@PathVariable String conversationGroupId, @PathVariable String messageId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getMessageMultimedia(conversationGroupId, messageId));
    }

    // Retrieve multiple Multimedia objects of a conversationGroup (Not including the group profile photo)
    @GetMapping("/conversationGroup/{conversationGroupId}")
    public List<MultimediaResponse> getMultimediaOfAConversation(@PathVariable String conversationGroupId) {
        List<Multimedia> multimediaList = multimediaService.getMultimediaOfAConversation(conversationGroupId);
        return multimediaList.stream().map(multimediaService::multimediaResponseMapper).collect(Collectors.toList());
    }
}
