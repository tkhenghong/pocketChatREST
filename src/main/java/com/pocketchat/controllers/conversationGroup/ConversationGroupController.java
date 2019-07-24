package com.pocketchat.controllers.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.services.conversationGroup.ConversationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/conversationGroup")
public class ConversationGroupController {

    private final ConversationGroupService conversationGroupService;

    // Avoid Field Injection
    @Autowired
    public ConversationGroupController(ConversationGroupService conversationGroupService) {
        this.conversationGroupService = conversationGroupService;
    }


    // TODO: Should create DTO objects to prevent malicious attack
    // Explanation: https://rules.sonarsource.com/java/tag/spring/RSPEC-4684
    // How to implement: https://auth0.com/blog/automatically-mapping-dto-to-entity-on-spring-boot-apis/
    @PostMapping("")
    public ResponseEntity<Object> addConversation(@Valid @RequestBody ConversationGroup conversationGroup) {
        ConversationGroup savedConversationGroup = conversationGroupService.addConversation(conversationGroup);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedConversationGroup.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "")
    public void editConversation(@Valid @RequestBody ConversationGroup conversationGroup) {
        conversationGroupService.editConversation(conversationGroup);
    }

    @DeleteMapping("/{conversationGroupId}")
    public void deleteConversation(@PathVariable String conversationGroupId) {
        conversationGroupService.deleteConversation(conversationGroupId);
    }

    @GetMapping("/{conversationGroupId}")
    public ConversationGroup getSingleConversation(@PathVariable String conversationGroupId) {
        return conversationGroupService.getSingleConversation(conversationGroupId);
    }

    @GetMapping("/user/{userId}")
    public List<ConversationGroup> getConversationsForUser(@PathVariable String userId) {
        return conversationGroupService.getConversationsForUser(userId);
    }
}
