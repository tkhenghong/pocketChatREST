package com.pocketchat.controllers.message;

import com.pocketchat.dbRepoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.dbRepoServices.message.MessageRepoService;
import com.pocketchat.models.conversation_group.ConversationGroup;
import com.pocketchat.models.message.Message;
import com.pocketchat.server.exceptions.conversationGroup.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.message.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageRepoService messageRepoService;
    private final ConversationGroupRepoService conversationGroupRepoService;

    @Autowired
    public MessageController(MessageRepoService messageRepoService, ConversationGroupRepoService conversationGroupRepoService) {
        this.messageRepoService = messageRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
    }


    @PostMapping("")
    public ResponseEntity<Object> addMessage(@Valid @RequestBody Message message) {
        Message savedMessage = messageRepoService.save(message);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedMessage.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public void editMessage(@Valid @RequestBody Message message) {
        Optional<Message> userContactOptional = messageRepoService.findById(message.getId());

        if (!userContactOptional.isPresent()) {
            throw new MessageNotFoundException("messageId:-" + message.getId());
        }

        messageRepoService.save(message);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable String messageId) {
        Optional<Message> conversationGroupOptional = messageRepoService.findById(messageId);
        if (!conversationGroupOptional.isPresent()) {
            throw new MessageNotFoundException("messageId-" + messageId);
        }

        messageRepoService.delete(conversationGroupOptional.get());
    }

    @GetMapping("/conversation/{conversationGroupId}")
    public void getMessagesOfAConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId:-" + conversationGroupId);
        }
        // List<Message> messageRepoService.findAllMessagesByConversationId(conversationGroupId);
    }

    // Research it's definition for more usages
    public void getMessagesOfAUser() {

    }
}
