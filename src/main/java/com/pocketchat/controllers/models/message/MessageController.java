package com.pocketchat.controllers.models.message;

import com.pocketchat.controllers.response.message.MessageResponse;
import com.pocketchat.db.models.message.Message;
import com.pocketchat.services.models.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public ResponseEntity<Object> addMessage(@Valid @RequestBody Message message) {
        Message savedMessage = messageService.addMessage(message);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMessage.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public void editMessage(@Valid @RequestBody Message message) {
        messageService.editMessage(message);
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
        List<Message> messageList = messageService.getMessagesOfAConversation(conversationGroupId);
        return messageList.stream().map(this::messageResponseMapper).collect(Collectors.toList());
    }

    // Research it's definition for more usages
//    public void getMessagesOfAUser() {
//
//    }

    private MessageResponse messageResponseMapper(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .conversationId(message.getConversationId())
                .messageContent(message.getMessageContent())
                .multimediaId(message.getMultimediaId())
                .receiverId(message.getReceiverId())
                .receiverMobileNo(message.getReceiverMobileNo())
                .receiverName(message.getReceiverName())
                .senderId(message.getSenderId())
                .senderMobileNo(message.getSenderMobileNo())
                .senderName(message.getSenderName())
                .status(message.getStatus())
                .timestamp(message.getTimestamp().getMillis())
                .build();
    }
}
