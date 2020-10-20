package com.pocketchat.controllers.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.models.controllers.request.conversation_group.*;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.controllers.response.conversation_group.ConversationPageableResponse;
import com.pocketchat.models.controllers.response.conversation_group_block.ConversationGroupBlockResponse;
import com.pocketchat.models.controllers.response.conversation_group_mute_notification.ConversationGroupMuteNotificationResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/conversationGroup")
public class ConversationGroupController {

    private final ConversationGroupService conversationGroupService;
    private final UnreadMessageService unreadMessageService;

    // Avoid Field Injection
    @Autowired
    public ConversationGroupController(ConversationGroupService conversationGroupService,
                                       UnreadMessageService unreadMessageService) {
        this.conversationGroupService = conversationGroupService;
        this.unreadMessageService = unreadMessageService;
    }

    @PostMapping("")
    public ConversationGroupResponse addConversationGroup(@Valid @RequestBody CreateConversationGroupRequest conversationGroup) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.addConversation(conversationGroup));
    }

    @PutMapping("/{conversationGroupId}/groupPhoto")
    public MultimediaResponse uploadConversationGroupGroupPhoto(
            @PathVariable String conversationGroupId,
            @RequestParam("file") MultipartFile multipartFile) {
        return conversationGroupService.uploadConversationGroupGroupPhoto(conversationGroupId, multipartFile);
    }

    @GetMapping("/{conversationGroupId}/groupPhoto")
    public ResponseEntity<Resource> getConversationGroupGroupPhoto(@PathVariable("conversationGroupId") String conversationGroupId,
                                                                   HttpServletRequest httpServletRequest) {
        File file;
        Resource resource;
        try {
            file = conversationGroupService.getConversationGroupGroupPhoto(conversationGroupId);
            resource = new UrlResource(file.toURI());
        } catch (FileNotFoundException | MalformedURLException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(determineFileContentType(httpServletRequest, file)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @DeleteMapping("/{conversationGroupId}/groupPhoto")
    public void deleteConversationGroupProfilePhoto(@PathVariable String conversationGroupId) {
        conversationGroupService.deleteConversationGroupProfilePhoto(conversationGroupId);
    }

    @PutMapping("")
    public ConversationGroupResponse editConversationGroup(@Valid @RequestBody UpdateConversationGroupRequest updateConversationGroupRequest) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.editConversationGroup(updateConversationGroupRequest));
    }

    /**
     * Add a list of group members into the conversation group.
     *
     * @param conversationGroupId:               ID of the ConversationGroup object.
     * @param addConversationGroupMemberRequest: AddConversationGroupMemberRequest. Contains a list of IDs to be added into the conversation group.
     * @return Updated ConversationGroupResponse object.
     */
    @PutMapping("{conversationGroupId}/groupMember/add")
    public ConversationGroupResponse addConversationGroupMember(@PathVariable String conversationGroupId, @Valid @RequestBody AddConversationGroupMemberRequest addConversationGroupMemberRequest) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.addConversationGroupMember(conversationGroupId, addConversationGroupMemberRequest));
    }

    /**
     * Remove a list of group members from the conversation group.
     *
     * @param conversationGroupId:                  ID of the ConversationGroup object.
     * @param removeConversationGroupMemberRequest: RemoveConversationGroupMemberRequest. Contains a list of IDs to be removed from the conversation group.
     * @return Updated ConversationGroupResponse object.
     */
    @PutMapping("{conversationGroupId}/groupMember/remove")
    public ConversationGroupResponse removeConversationGroupMember(@PathVariable String conversationGroupId, @Valid @RequestBody RemoveConversationGroupMemberRequest removeConversationGroupMemberRequest) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.removeConversationGroupMember(conversationGroupId, removeConversationGroupMemberRequest));
    }

    /**
     * Promote a new group admin to the conversation group.
     * NOTE: Only Group admin can promote other to group admin.
     *
     * @param conversationGroupId:              ID of the ConversationGroup object.
     * @param addConversationGroupAdminRequest: AddConversationGroupAdminRequest object. Contains a list of ID to be promoted to group admin.
     * @return Updated ConversationGroupResponse object.
     */
    @PutMapping("{conversationGroupId}/groupAdmin/add")
    public ConversationGroupResponse addConversationGroupAdmin(@PathVariable String conversationGroupId, @Valid @RequestBody AddConversationGroupAdminRequest addConversationGroupAdminRequest) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.addConversationGroupAdmin(conversationGroupId, addConversationGroupAdminRequest));
    }

    /**
     * Demote a group admin in the conversation group.
     *
     * @param conversationGroupId:                 ID of the ConversationGroup object.
     * @param removeConversationGroupAdminRequest: RemoveConversationGroupAdminRequest object. Contains a list of ID to be demoted.
     * @return Updated ConversationGroupResponse object.
     */
    @PutMapping("{conversationGroupId}/groupAdmin/remove")
    public ConversationGroupResponse removeConversationGroupAdmin(@PathVariable String conversationGroupId, @Valid @RequestBody RemoveConversationGroupAdminRequest removeConversationGroupAdminRequest) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.removeConversationGroupAdmin(conversationGroupId, removeConversationGroupAdminRequest));
    }

    /**
     * Leave the conversation.
     * The reason to put ConversationGroupResponse is to give the user the latest updated conversation group state before they leave.
     *
     * @param conversationGroupId: ID of the ConversationGroup object.
     * @return Updated ConversationGroupResponse object.
     */
    @PutMapping("{conversationGroupId}/leave")
    public ConversationGroupResponse leaveConversationGroup(@PathVariable String conversationGroupId) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.leaveConversationGroup(conversationGroupId));
    }

    /**
     * Block a conversation group to prevent other users from sending you messages.
     * NOTE: Only Personal conversation groups.
     *
     * @param conversationGroupId: ID of the ConversationGroup object.
     * @return ConversationGroupBlockResponse for the reference.
     */
    @PutMapping("{conversationGroupId}/block")
    public ConversationGroupBlockResponse blockConversationGroup(@PathVariable String conversationGroupId) {
        return conversationGroupService.conversationGroupBlockResponseMapper(conversationGroupService.blockConversationGroup(conversationGroupId));
    }

    /**
     * Unblock a conversation group.
     *
     * @param conversationGroupId:             ID of the ConversationGroup object.
     * @param unblockConversationGroupRequest: UnblockConversationGroupRequest object. Contains ID of the UnblockConversationGroupRequest object.
     */
    @PutMapping("{conversationGroupId}/unblock")
    public void unblockConversationGroup(@PathVariable String conversationGroupId, @Valid @RequestBody UnblockConversationGroupRequest unblockConversationGroupRequest) {
        conversationGroupService.unblockConversationGroup(conversationGroupId, unblockConversationGroupRequest);
    }

    /**
     * Mute a conversation group's notifications.
     *
     * @param conversationGroupId:                      ID of the ConversationGroup object.
     * @param muteConversationGroupNotificationRequest: MuteConversationGroupNotificationRequest object. It states the expiry date of the mute conversation group record.
     * @return ConversationGroupMuteNotificationResponse for reference.
     */
    @PutMapping("{conversationGroupId}/notification/mute")
    public ConversationGroupMuteNotificationResponse muteConversationGroupNotification(@PathVariable String conversationGroupId, @Valid @RequestBody MuteConversationGroupNotificationRequest muteConversationGroupNotificationRequest) {
        return conversationGroupService.conversationGroupMuteNotificationResponseMapper(conversationGroupService.muteConversationGroupNotification(conversationGroupId, muteConversationGroupNotificationRequest));
    }

    /**
     * Unmute a conversation group's notification.
     *
     * @param conversationGroupId:                        ID of the ConversationGroup object.
     * @param unmuteConversationGroupNotificationRequest: UnmuteConversationGroupNotificationRequest object. It contains the ID of the UnmuteConversationGroupNotification object.
     */
    @PutMapping("{conversationGroupId}/notification/unmute")
    public void unmuteConversationGroupNotification(@PathVariable String conversationGroupId, @Valid @RequestBody UnmuteConversationGroupNotificationRequest unmuteConversationGroupNotificationRequest) {
        conversationGroupService.unmuteConversationGroupNotification(conversationGroupId, unmuteConversationGroupNotificationRequest);
    }

    /**
     * TODO: To be implemented.
     * Join a conversation group using a QR code
     *
     * @param joinConversationGroupRequest: JoinConversationGroupRequest object. Contains the details of the conversation group invitation details.
     * @return ConversationGroupBlockResponse for reference.
     */
    @PostMapping("join")
    public ConversationGroupResponse joinConversationGroup(@Valid @RequestBody JoinConversationGroupRequest joinConversationGroupRequest) {
        return null;
    }

    @DeleteMapping("/{conversationGroupId}")
    public void deleteConversation(@PathVariable String conversationGroupId) {
        conversationGroupService.deleteConversation(conversationGroupId);
    }

    @GetMapping("/{conversationGroupId}")
    public ConversationGroupResponse getSingleConversation(@PathVariable String conversationGroupId) {
        return conversationGroupService.conversationGroupResponseMapper(conversationGroupService.getSingleConversation(conversationGroupId));
    }

    @PostMapping("/user")
    public ConversationPageableResponse getUserOwnConversationGroups(@Valid @RequestBody GetConversationGroupsRequest getConversationGroupsRequest) {
        Page<ConversationGroup> conversationGroupList = conversationGroupService.getUserOwnConversationGroups(getConversationGroupsRequest.getPageable());
        Page<ConversationGroupResponse> conversationGroupResponses = conversationGroupService.conversationGroupResponsePageMapper(conversationGroupList);
        Page<UnreadMessage> unreadMessagePage = unreadMessageService.getUnreadMessagesFromConversationGroupsWithPageable(conversationGroupList);
        Page<UnreadMessageResponse> unreadMessageResponses = unreadMessageService.unreadMessageResponsePageMapper(unreadMessagePage);

        return conversationGroupService.conversationPageableResponseMapper(conversationGroupResponses, unreadMessageResponses);
    }

    /**
     * Determine the content type based on the File object.
     *
     * @return content type in String. For example: application/octet-stream
     */
    // https://dzone.com/articles/java-springboot-rest-api-to-uploaddownload-file-on
    private String determineFileContentType(HttpServletRequest httpServletRequest, File file) {
        String contentType = httpServletRequest.getServletContext().getMimeType(file.getAbsolutePath());

        if (StringUtils.isEmpty(contentType)) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
