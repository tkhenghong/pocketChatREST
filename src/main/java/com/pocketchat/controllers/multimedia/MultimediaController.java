package com.pocketchat.controllers.multimedia;

import com.pocketchat.dbRepoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.dbRepoServices.multimedia.MultimediaRepoService;
import com.pocketchat.dbRepoServices.user.UserRepoService;
import com.pocketchat.models.multimedia.Multimedia;
import com.pocketchat.models.user.User;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaRepoService multimediaRepoService;
    private final UserRepoService userRepoService;
    private final ConversationGroupRepoService conversationGroupRepoService;

    public MultimediaController(MultimediaRepoService multimediaRepoService, UserRepoService userRepoService, ConversationGroupRepoService conversationGroupRepoService) {
        this.multimediaRepoService = multimediaRepoService;
        this.userRepoService = userRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addMultimedia(@Valid @RequestBody Multimedia multimedia) {
        Multimedia savedMultimedia = multimediaRepoService.save(multimedia);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMultimedia.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public void editMultimedia(@Valid @RequestBody Multimedia multimedia) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimedia.getId());
        validateMultimediaNotFound(multimediaOptional, multimedia.getId());
        multimediaRepoService.save(multimedia);
    }

    @DeleteMapping("/{multimediaId}")
    public void deleteMultimedia(@PathVariable String multimediaId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimediaId);
        validateMultimediaNotFound(multimediaOptional, multimediaId);
        multimediaRepoService.delete(multimediaOptional.get());
    }

    //
    @GetMapping("/user/{userId}")
    public List<Multimedia> getMultimediaOfAUser(@PathVariable String userId) {
        // Go to the user's conversation groups and get all multimedia, merge them to list and send back to front end
        Optional<User> userOptional = userRepoService.findById(userId);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found. userId:-" + userId);
        }
        // This part related to getConversationForUser in MultimediaController class.
        // Make all implementations of ConversationGroupController into a service class
        // To bring all conversationIds to here, and search through multimedia table for all multimedias
//        multimediaRepoService.findBy
//        userOptional
        return null;
    }

    public void getMultimediaOfAConversation() {

    }

    private void validateMultimediaNotFound(Optional<Multimedia> multimediaOptional, String multimediaId) {
        if (!multimediaOptional.isPresent()) {
            throw new MultimediaNotFoundException("multimediaId-" + multimediaId);
        }
    }
}
