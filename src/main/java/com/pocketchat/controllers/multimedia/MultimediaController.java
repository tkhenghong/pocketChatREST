package com.pocketchat.controllers.multimedia;

import com.pocketchat.models.controllers.request.multimedia.CreateMultimediaRequest;
import com.pocketchat.models.controllers.request.multimedia.UpdateMultimediaRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.services.multimedia.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addMultimedia(@Valid @RequestBody CreateMultimediaRequest multimedia) {
        MultimediaResponse savedMultimedia = multimediaService.addMultimedia(multimedia);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMultimedia.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public MultimediaResponse editMultimedia(@Valid @RequestBody UpdateMultimediaRequest multimedia) {
        return multimediaService.editMultimedia(multimedia);
    }

    @DeleteMapping("/{multimediaId}")
    public void deleteMultimedia(@PathVariable String multimediaId) {
        multimediaService.deleteMultimedia(multimediaId);
    }

    @GetMapping("/{multimediaId}")
    public MultimediaResponse getSingleMultimedia(@PathVariable String multimediaId) {
        return multimediaService.multimediaResponseMapper(multimediaService.getSingleMultimedia(multimediaId));
    }

    @GetMapping("/user/{userId}")
    public MultimediaResponse getMultimediaOfAUser(@PathVariable String userId) {
        return multimediaService.getMultimediaOfAUser(userId);
    }

    // Get multimedia of the UserContact (One at the time)
    @GetMapping("/userContact/{userContactId}")
    public MultimediaResponse getMultimediaOfAUserContact(@PathVariable String userContactId) {
        return multimediaService.getMultimediaOfAUserContact(userContactId);
    }

    // Get multimedia of the Group Photo (One at the time)
    @GetMapping("/conversationGroup/photo/{conversationGroupId}")
    public MultimediaResponse getConversationGroupPhotoMultimedia(@PathVariable String conversationGroupId) {
        return multimediaService.getConversationGroupMultimedia(conversationGroupId);
    }

    // Retrieve the multimedia of the message if frontend user doesn't have it
    @GetMapping("/message/{conversationGroupId}/{messageId}")
    public MultimediaResponse getMessageMultimedia(@PathVariable String conversationGroupId, @PathVariable String messageId) {
        return multimediaService.getMessageMultimedia(conversationGroupId, messageId);
    }

    // Retrieve multiple Multimedia object of a conversationGroup (Not including the group profile photo)
    @GetMapping("/conversationGroup/{conversationGroupId}")
    public List<MultimediaResponse> getMultimediaOfAConversation(@PathVariable String conversationGroupId) {
        return multimediaService.getMultimediaOfAConversation(conversationGroupId);
    }
}
