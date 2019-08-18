package com.pocketchat.controllers.message;

import com.pocketchat.db.models.message.Message;
import com.pocketchat.services.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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
    public Message getSingleMessage(@PathVariable String messageId) {
        return messageService.getSingleMessage(messageId);
    }

    @GetMapping("/conversation/{conversationGroupId}")
    public List<Message> getMessagesOfAConversation(String conversationGroupId) {
        return messageService.getMessagesOfAConversation(conversationGroupId);
    }

    // Research it's definition for more usages
//    public void getMessagesOfAUser() {
//
//    }
}
