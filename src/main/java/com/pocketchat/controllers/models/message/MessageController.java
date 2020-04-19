package com.pocketchat.controllers.models.message;

import com.pocketchat.controllers.response.message.MessageResponse;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.services.models.message.MessageService;
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
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @PostMapping("")
    public ResponseEntity<Object> addMessage(@Valid @RequestBody ChatMessage chatMessage) {
        ChatMessage savedChatMessage = messageService.addMessage(chatMessage);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedChatMessage.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public void editMessage(@Valid @RequestBody ChatMessage chatMessage) {
        messageService.editMessage(chatMessage);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(messageId);
    }

    @GetMapping("/{messageId}")
    public MessageResponse getSingleMessage(@PathVariable String messageId) {
        return messageResponseMapper(messageService.getSingleMessage(messageId));
    }

    @GetMapping("/conversation/{conversationGroupId}")
    public List<MessageResponse> getMessagesOfAConversation(String conversationGroupId) {
        List<ChatMessage> chatMessageList = messageService.getMessagesOfAConversation(conversationGroupId);
        return chatMessageList.stream().map(this::messageResponseMapper).collect(Collectors.toList());
    }

    // Research it's definition for more usages
//    public void getMessagesOfAUser() {
//
//    }

    private MessageResponse messageResponseMapper(ChatMessage chatMessage) {
        return MessageResponse.builder()
                .id(chatMessage.getId())
                .conversationId(chatMessage.getConversationId())
                .messageContent(chatMessage.getMessageContent())
                .multimediaId(chatMessage.getMultimediaId())
                .receiverId(chatMessage.getReceiverId())
                .receiverMobileNo(chatMessage.getReceiverMobileNo())
                .receiverName(chatMessage.getReceiverName())
                .senderId(chatMessage.getSenderId())
                .senderMobileNo(chatMessage.getSenderMobileNo())
                .senderName(chatMessage.getSenderName())
                .status(chatMessage.getStatus())
                .timestamp(chatMessage.getCreatedTime().getMillis())
                .build();
    }
}
