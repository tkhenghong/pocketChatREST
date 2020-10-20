package com.pocketchat.controllers.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.services.chat_message.ChatMessageService;
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
@RequestMapping("/chatMessage")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("")
    public ChatMessageResponse addMessage(@Valid @RequestBody CreateChatMessageRequest chatMessage) {
        return chatMessageService.chatMessageResponseMapper(chatMessageService.addChatMessage(chatMessage));
    }

    @PostMapping("{chatMessageId}/multimedia")
    public MultimediaResponse uploadChatMessageMultimedia(@PathVariable String chatMessageId,
                                                          @RequestParam("file") MultipartFile multipartFile) {
        return chatMessageService.uploadChatMessageMultimedia(chatMessageId, multipartFile);
    }

    @GetMapping("{chatMessageId}/multimedia")
    public ResponseEntity<Resource> getChatMessageMultimedia(@PathVariable String chatMessageId,
                                                             HttpServletRequest httpServletRequest) {
        File file;
        Resource resource;
        try {
            file = chatMessageService.getChatMessageMultimedia(chatMessageId);
            resource = new UrlResource(file.toURI());
        } catch (FileNotFoundException | MalformedURLException exception) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(determineFileContentType(httpServletRequest, file)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }

    @DeleteMapping("/{messageId}")
    public void deleteChatMessage(@PathVariable String messageId) {
        chatMessageService.deleteChatMessage(messageId);
    }

    @GetMapping("/{messageId}")
    public ChatMessageResponse getSingleChatMessage(@PathVariable String messageId) {
        return chatMessageService.chatMessageResponseMapper(chatMessageService.getSingleChatMessage(messageId));
    }

    // Get messages with Pageable. https://reflectoring.io/spring-boot-paging/
    @GetMapping("/conversation/{conversationGroupId}")
    public Page<ChatMessageResponse> getChatMessagesOfAConversation(@PathVariable String conversationGroupId,
                                                                    @PageableDefault(page = 0, size = 20)
                                                                    @SortDefault() Pageable pageable) {
        Page<ChatMessage> chatMessagesList = chatMessageService.getChatMessagesOfAConversation(conversationGroupId, pageable);
        return chatMessageService.pageChatMessageResponseMapper(chatMessagesList);
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
