package com.pocketchat.controllers.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.services.chat_message.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/upload/{chatMessageId}")
    public MultimediaResponse uploadChatMessageMultimedia(@PathVariable String chatMessageId,
                                                          @RequestParam("file") MultipartFile multipartFile) {
        return chatMessageService.uploadChatMessageMultimedia(chatMessageId, multipartFile);
    }

    @DeleteMapping("/{messageId}")
    public void deleteChatMessage(@PathVariable String messageId) {
        chatMessageService.deleteChatMessage(messageId);
    }

    @GetMapping("/{messageId}")
    public ChatMessageResponse getSingleChatMessage(@PathVariable String messageId) {
        return chatMessageService.chatMessageResponseMapper(chatMessageService.getSingleChatMessage(messageId));
    }

    @GetMapping("/conversation/{conversationGroupId}")
    public List<ChatMessageResponse> getChatMessagesOfAConversation(@PathVariable String conversationGroupId) {
        List<ChatMessage> chatMessagesList = chatMessageService.getChatMessagesOfAConversation(conversationGroupId);
        return chatMessagesList.stream().map(chatMessageService::chatMessageResponseMapper).collect(Collectors.toList());
    }
}
