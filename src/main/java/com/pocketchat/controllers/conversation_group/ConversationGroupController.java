package com.pocketchat.controllers.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    public ConversationGroupResponse addConversationGroup(@Valid @RequestBody CreateConversationGroupRequest conversationGroup) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.addConversation(conversationGroup));
    }

    @PutMapping("/{conversationGroupId}/upload/profilePhoto")
    public void uploadConversationGroupProfilePhoto(
            @PathVariable String conversationGroupId,
            @RequestParam("file") MultipartFile multipartFile) {
        conversationGroupService.uploadConversationGroupProfilePhoto(conversationGroupId, multipartFile);
    }

    @DeleteMapping("/{conversationGroupId}/profilePhoto")
    public void deleteConversationGroupProfilePhoto(@PathVariable String conversationGroupId) {
        conversationGroupService.deleteConversationGroupProfilePhoto(conversationGroupId);
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
