package com.pocketchat.controllers.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
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
import java.util.stream.Collectors;

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
    public ResponseEntity<Object> addConversationGroup(@Valid @RequestBody CreateConversationGroupRequest conversationGroup) {
        ConversationGroup savedConversationGroup = conversationGroupService.addConversation(conversationGroup);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedConversationGroup.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public ConversationGroupResponse editConversationGroup(@Valid @RequestBody UpdateConversationGroupRequest updateConversationGroupRequest) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.editConversation(updateConversationGroupRequest));
    }

    @DeleteMapping("/{conversationGroupId}")
    public void deleteConversation(@PathVariable String conversationGroupId) {
        conversationGroupService.deleteConversation(conversationGroupId);
    }

    @GetMapping("/{conversationGroupId}")
    public ConversationGroupResponse getSingleConversation(@PathVariable String conversationGroupId) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.getSingleConversation(conversationGroupId));
    }

    @GetMapping("/user")
    public List<ConversationGroupResponse> getUserOwnConversationGroups() {
        List<ConversationGroup> conversationGroupList = conversationGroupService.getUserOwnConversationGroups();
        return conversationGroupList.stream().map(conversationGroupService::conversationGroupResponseMapper).collect(Collectors.toList());
    }
}
