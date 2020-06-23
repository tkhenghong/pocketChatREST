package com.pocketchat.controllers.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.chat_message.UpdateChatMessageRequest;
import com.pocketchat.models.controllers.response.chat_message.ChatMessageResponse;
import com.pocketchat.services.chat_message.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

// TODO: Message Controller may get removed because of Websocket
@RestController
@RequestMapping("/message")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addMessage(@Valid @RequestBody CreateChatMessageRequest chatMessage) {
        ChatMessage savedChatMessage = chatMessageService.addChatMessage(chatMessage);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedChatMessage.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public ChatMessageResponse editMessage(@Valid @RequestBody UpdateChatMessageRequest chatMessage) {
        return chatMessageService.chatMessageResponseMapper(chatMessageService.editChatMessage(chatMessage));
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable String messageId) {
        chatMessageService.deleteChatMessage(messageId);
    }

    @GetMapping("/{messageId}")
    public ChatMessageResponse getSingleMessage(@PathVariable String messageId) {
        return chatMessageService.chatMessageResponseMapper(chatMessageService.getSingleChatMessage(messageId));
    }

    @GetMapping("/conversation/{conversationGroupId}")
    public List<ChatMessageResponse> getMessagesOfAConversation(String conversationGroupId) {
        List<ChatMessage> chatMessagesList = chatMessageService.getChatMessagesOfAConversation(conversationGroupId);
        return chatMessagesList.stream().map(chatMessageService::chatMessageResponseMapper).collect(Collectors.toList());
    }

    // Research it's definition for more usages
    //    public void getMessagesOfAUser() {
    //
    //    }
}
