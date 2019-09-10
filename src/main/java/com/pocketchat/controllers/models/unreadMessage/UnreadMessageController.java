package com.pocketchat.controllers.models.unreadMessage;

import com.pocketchat.controllers.response.unreadMessage.UnreadMessageResponse;
import com.pocketchat.controllers.response.userContact.UserContactResponse;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.services.models.unreadMessage.UnreadMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
    public UnreadMessageResponse getSingleMultimedia(@PathVariable String unreadMessageId) {
        return unreadMessageResponseMapper(unreadMessageService.getSingleMultimedia(unreadMessageId));
    }

    @GetMapping("/user/{userId}")
    public List<UnreadMessageResponse> getUnreadMessagesOfAUser(String userId) {
        List<UnreadMessage> unreadMessageList = unreadMessageService.getUnreadMessagesOfAUser(userId);
        return unreadMessageList.stream().map(this::unreadMessageResponseMapper).collect(Collectors.toList());
    }

    private UnreadMessageResponse unreadMessageResponseMapper(UnreadMessage unreadMessage) {
        return UnreadMessageResponse.builder()
                .id(unreadMessage.getId())
                .lastMessage(unreadMessage.getLastMessage())
                .count(unreadMessage.getCount())
                .userId(unreadMessage.getUserId())
                .conversationId(unreadMessage.getConversationId())
                .date(unreadMessage.getDate().getMillis())
                .build();
    }
}
