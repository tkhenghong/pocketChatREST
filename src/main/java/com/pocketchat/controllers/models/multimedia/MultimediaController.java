package com.pocketchat.controllers.models.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.services.models.multimedia.MultimediaService;
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
    public ResponseEntity<Object> addMultimedia(@Valid @RequestBody Multimedia multimedia) {
        Multimedia savedMultimedia = multimediaService.addMultimedia(multimedia);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMultimedia.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public void editMultimedia(@Valid @RequestBody Multimedia multimedia) {
        multimediaService.editMultimedia(multimedia);
    }

    @DeleteMapping("/{multimediaId}")
    public void deleteMultimedia(@PathVariable String multimediaId) {
        multimediaService.deleteMultimedia(multimediaId);
    }

    @GetMapping("/{multimediaId}")
    public Multimedia getSingleMultimedia(@PathVariable String multimediaId) {
        return multimediaService.getSingleMultimedia(multimediaId);
    }

    @GetMapping("/user/{userId}")
    public Multimedia getMultimediaOfAUser(@PathVariable String userId) {
        return multimediaService.getMultimediaOfAUser(userId);
    }

    @GetMapping("/conversationGroup/{conversationGroupId}")
    public List<Multimedia> getMultimediaOfAConversation(@PathVariable String conversationGroupId) {
        return multimediaService.getMultimediaOfAConversation(conversationGroupId);
    }

    @GetMapping("/userContact/{userContactId}")
    public Multimedia getMultimediaOfAUserContact(@PathVariable String userContactId) {
        return multimediaService.getMultimediaOfAUserContact(userContactId);
    }
}
