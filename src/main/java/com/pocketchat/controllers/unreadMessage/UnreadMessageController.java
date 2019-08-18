package com.pocketchat.controllers.unreadMessage;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.services.unreadMessage.UnreadMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/unreadMessage")
public class UnreadMessageController {

    private final UnreadMessageService unreadMessageService;

    public UnreadMessageController(UnreadMessageService unreadMessageService) {
        this.unreadMessageService = unreadMessageService;
    }

    @PostMapping("")
    public ResponseEntity<Object> addUnreadMessage(@Valid @RequestBody UnreadMessage unreadMessage) {
        UnreadMessage savedUnreadMessage = unreadMessageService.addUnreadMessage(unreadMessage);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUnreadMessage.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("")
    public void editUnreadMessage(@Valid @RequestBody UnreadMessage unreadMessage) {
        unreadMessageService.editUnreadMessage(unreadMessage);
    }

    @DeleteMapping("/{unreadMessageId}")
    public void deleteUnreadMessage(@PathVariable String unreadMessageId) {
        unreadMessageService.deleteUnreadMessage(unreadMessageId);
    }

    @GetMapping("/{unreadMessageId}")
    public UnreadMessage getSingleMultimedia(@PathVariable String unreadMessageId) {
        return unreadMessageService.getSingleMultimedia(unreadMessageId);
    }

    @GetMapping("/user/{userId}")
    public List<UnreadMessage> getUnreadMessagesOfAUser(String userId) {
        return unreadMessageService.getUnreadMessagesOfAUser(userId);
    }
}
