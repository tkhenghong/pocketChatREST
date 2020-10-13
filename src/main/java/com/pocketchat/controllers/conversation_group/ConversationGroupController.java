package com.pocketchat.controllers.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.controllers.response.conversation_group.ConversationPageableResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
    public MultimediaResponse uploadConversationGroupProfilePhoto(
            @PathVariable String conversationGroupId,
            @RequestParam("file") MultipartFile multipartFile) {
        return conversationGroupService.uploadConversationGroupProfilePhoto(conversationGroupId, multipartFile);
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
    public ConversationPageableResponse getUserOwnConversationGroups(@PageableDefault(page = 0, size = 20)
                                                                     @SortDefault() Pageable pageable) {
        Page<ConversationGroup> conversationGroupList = conversationGroupService.getUserOwnConversationGroups(pageable);
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
    String determineFileContentType(HttpServletRequest httpServletRequest, File file) {
        String contentType = httpServletRequest.getServletContext().getMimeType(file.getAbsolutePath());

        if (StringUtils.isEmpty(contentType)) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
