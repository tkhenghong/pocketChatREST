package com.pocketchat.controllers.conversation_group;

import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.services.conversation_group.ConversationGroupService;
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

    @PostMapping("")
    public ResponseEntity<Object> addConversation(@Valid @RequestBody CreateConversationGroupRequest conversationGroup) {
        ConversationGroupResponse savedConversationGroup = conversationGroupService.addConversation(conversationGroup);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedConversationGroup.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public ConversationGroupResponse editConversation(@Valid @RequestBody UpdateConversationGroupRequest updateConversationGroupRequest) {
        return conversationGroupService.editConversation(updateConversationGroupRequest);
    }

    @DeleteMapping("/{conversationGroupId}")
    public void deleteConversation(@PathVariable String conversationGroupId) {
        conversationGroupService.deleteConversation(conversationGroupId);
    }

    @GetMapping("/{conversationGroupId}")
    public ConversationGroupResponse getSingleConversation(@PathVariable String conversationGroupId) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.getSingleConversation(conversationGroupId));
    }

    @GetMapping("/user/mobileNo/{mobileNo}")
    public List<ConversationGroupResponse> getConversationsForUserByMobileNo(@PathVariable String mobileNo) {
        return conversationGroupService.getConversationsForUserByMobileNo(mobileNo);
    }
}