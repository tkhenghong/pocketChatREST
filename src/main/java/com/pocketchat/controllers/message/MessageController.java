package com.pocketchat.controllers.message;

import com.pocketchat.models.controllers.request.message.CreateMessageRequest;
import com.pocketchat.models.controllers.request.message.UpdateMessageRequest;
import com.pocketchat.models.controllers.response.message.MessageResponse;
import com.pocketchat.services.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    public ResponseEntity<Object> addMessage(@Valid @RequestBody CreateMessageRequest chatMessage) {
        MessageResponse savedChatMessage = messageService.addMessage(chatMessage);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedChatMessage.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public MessageResponse editMessage(@Valid @RequestBody UpdateMessageRequest chatMessage) {
        return messageService.editMessage(chatMessage);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(messageId);
    }

    @GetMapping("/{messageId}")
    public MessageResponse getSingleMessage(@PathVariable String messageId) {
        return messageService.messageResponseMapper(messageService.getSingleMessage(messageId));
    }

    @GetMapping("/conversation/{conversationGroupId}")
    public List<MessageResponse> getMessagesOfAConversation(String conversationGroupId) {
        return messageService.getMessagesOfAConversation(conversationGroupId);
    }

    // Research it's definition for more usages
    //    public void getMessagesOfAUser() {
    //
    //    }
}
