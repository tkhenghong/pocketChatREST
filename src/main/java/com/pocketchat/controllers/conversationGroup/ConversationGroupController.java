package com.pocketchat.controllers.conversationGroup;

import com.pocketchat.dbRepoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.models.conversation_group.ConversationGroup;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/conversationGroup")
public class ConversationGroupController {

    @Autowired
    ConversationGroupRepoService conversationGroupRepoService;

    @PostMapping("")
    public ResponseEntity<Object> addConversation(@Valid @RequestBody ConversationGroup conversationGroup) {
        ConversationGroup savedConversationGroup = conversationGroupRepoService.getConversationGroupRepository().save(conversationGroup);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedConversationGroup.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "")
    public void editConversation(@Valid @RequestBody ConversationGroup conversationGroup) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.getConversationGroupRepository().findById(conversationGroup.getId());

        if(!conversationGroupOptional.isPresent()) {
            throw new UserNotFoundException("conversationId-" + conversationGroup.getId());
        }
        conversationGroupRepoService.getConversationGroupRepository().save(conversationGroup);
    }

    @DeleteMapping("/{id}")
    public void deleteConversation(@PathVariable String id) {
        conversationGroupRepoService.getConversationGroupRepository().deleteById(id);
    }

    @GetMapping("/{conversationId}")
    public ConversationGroup getSingleConversation(@PathVariable String conversationId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.getConversationGroupRepository().findById(conversationId);

        if(!conversationGroupOptional.isPresent()) {
            throw new UserNotFoundException("conversationId-" + conversationId);
        }

        return conversationGroupOptional.get();
    }

    @GetMapping("/delete/{userId}")
    public void getConversationsForUser(@PathVariable String userId) {
//        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.getConversationGroupRepository().f(userId);



    }
}
