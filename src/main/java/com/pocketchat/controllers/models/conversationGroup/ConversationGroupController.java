package com.pocketchat.controllers.models.conversationGroup;

import com.pocketchat.controllers.response.conversationGroup.ConversationGroupResponse;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.services.models.conversationGroup.ConversationGroupService;
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
    public ConversationGroupResponse getSingleConversation(@PathVariable String conversationGroupId) {
        return conversationGroupResponseMapper(conversationGroupService.getSingleConversation(conversationGroupId));
    }

    @GetMapping("/user/mobileNo/{mobileNo}")
    public List<ConversationGroupResponse> getConversationsForUserByMobileNo(@PathVariable String mobileNo) {
        List<ConversationGroup> conversationGroupList = conversationGroupService.getConversationsForUserByMobileNo(mobileNo);
        return conversationGroupList.stream().map(this::conversationGroupResponseMapper).collect(Collectors.toList());
    }

    private ConversationGroupResponse conversationGroupResponseMapper(ConversationGroup conversationGroup) {
        return ConversationGroupResponse.builder()
                .id(conversationGroup.getId())
                .adminMemberIds(conversationGroup.getAdminMemberIds())
                .block(conversationGroup.isBlock())
                .createdDate(conversationGroup.getCreatedDate().getMillis())
                .creatorUserId(conversationGroup.getCreatorUserId())
                .description(conversationGroup.getDescription())
                .memberIds(conversationGroup.getMemberIds())
                .name(conversationGroup.getName())
                .notificationExpireDate(conversationGroup.getNotificationExpireDate().getMillis())
                .type(conversationGroup.getType())
                .build();
    }
}
