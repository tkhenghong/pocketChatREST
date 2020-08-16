package com.pocketchat.controllers.unread_message;

import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.services.unread_message.UnreadMessageService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/unreadMessage")
public class UnreadMessageController {

    private final UnreadMessageService unreadMessageService;

    public UnreadMessageController(UnreadMessageService unreadMessageService) {
        this.unreadMessageService = unreadMessageService;
    }

//    @PostMapping("")
//    public ResponseEntity<Object> addUnreadMessage(@Valid @RequestBody CreateUnreadMessageRequest unreadMessage) {
//        UnreadMessage savedUnreadMessage = unreadMessageService.addUnreadMessage(unreadMessage);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUnreadMessage.getId())
//                .toUri();
//        return ResponseEntity.created(location).build();
//    }

    @PutMapping("")
    public UnreadMessageResponse editUnreadMessage(@Valid @RequestBody UpdateUnreadMessageRequest unreadMessage) {
        return unreadMessageService.unreadMessageResponseMapper(unreadMessageService.editUnreadMessage(unreadMessage));
    }

//    @DeleteMapping("/{unreadMessageId}")
//    public void deleteUnreadMessage(@PathVariable String unreadMessageId) {
//        unreadMessageService.deleteUnreadMessage(unreadMessageId);
//    }

    @GetMapping("/{unreadMessageId}")
    public UnreadMessageResponse getSingleUnreadMessage(@PathVariable String unreadMessageId) {
        return unreadMessageService.unreadMessageResponseMapper(unreadMessageService.getSingleUnreadMessage(unreadMessageId));
    }

    @GetMapping("/user")
    public List<UnreadMessageResponse> getUnreadMessagesOfAUser() {
        return unreadMessageService.getUserOwnUnreadMessages().stream()
                .map(unreadMessageService::unreadMessageResponseMapper).collect(Collectors.toList());
    }
}
