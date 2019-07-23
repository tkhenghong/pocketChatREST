package com.pocketchat.controllers.conversationGroup;

import com.pocketchat.dbRepoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.dbRepoServices.userContact.UserContactRepoService;
import com.pocketchat.models.conversation_group.ConversationGroup;
import com.pocketchat.models.user_contact.UserContact;
import com.pocketchat.server.exceptions.user.UserNotFoundException;
import com.pocketchat.server.exceptions.userContact.UserContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conversationGroup")
public class ConversationGroupController {

    private final ConversationGroupRepoService conversationGroupRepoService;

    private final UserContactRepoService userContactRepoService;

    // Avoid Field Injection
    @Autowired
    public ConversationGroupController(ConversationGroupRepoService conversationGroupRepoService, UserContactRepoService userContactRepoService) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.userContactRepoService = userContactRepoService;
    }


    // TODO: Should create DTO objects to prevent malicious attack
    // Explanation: https://rules.sonarsource.com/java/tag/spring/RSPEC-4684
    // How to implement: https://auth0.com/blog/automatically-mapping-dto-to-entity-on-spring-boot-apis/
    @PostMapping("")
    public ResponseEntity<Object> addConversation(@Valid @RequestBody ConversationGroup conversationGroup) {
        ConversationGroup savedConversationGroup = conversationGroupRepoService.save(conversationGroup);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedConversationGroup.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "")
    public void editConversation(@Valid @RequestBody ConversationGroup conversationGroup) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroup.getId());

        if (!conversationGroupOptional.isPresent()) {
            throw new UserNotFoundException("conversationId-" + conversationGroup.getId());
        }
        conversationGroupRepoService.save(conversationGroup);
    }

    @DeleteMapping("/{conversationId}")
    public void deleteConversation(@PathVariable String conversationId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationId);
        if (!conversationGroupOptional.isPresent()) {
            throw new UserNotFoundException("conversationId-" + conversationId);
        }

        conversationGroupRepoService.delete(conversationGroupOptional.get());
    }

    @GetMapping("/{conversationId}")
    public ConversationGroup getSingleConversation(@PathVariable String conversationId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationId);

        if (!conversationGroupOptional.isPresent()) {
            throw new UserNotFoundException("conversationId-" + conversationId);
        }

        return conversationGroupOptional.get();
    }

    @GetMapping("/user/{userId}")
    public List<ConversationGroup> getConversationsForUser(@PathVariable String userId) {
        List<UserContact> userContactList = userContactRepoService.findByUserId(userId);

        if (userContactList.isEmpty()) {
            // throws UserContact not Found error
            throw new UserContactNotFoundException("UserContact not found: " + userId);
        }
        List<String> conversationIds = new ArrayList<>();
        userContactList.forEach((UserContact userContact) -> conversationIds.add(userContact.getConversationId()));
        List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllConversationGroupsByGroupMemberId(conversationIds);

        return conversationGroupList;
    }
}
