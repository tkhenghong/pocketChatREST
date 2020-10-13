package com.pocketchat.controllers.unread_message;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.services.unread_message.UnreadMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/unreadMessage")
public class UnreadMessageController {

    private final UnreadMessageService unreadMessageService;

    @Autowired
    public UnreadMessageController(UnreadMessageService unreadMessageService) {
        this.unreadMessageService = unreadMessageService;
    }

    @GetMapping("/{unreadMessageId}")
    public UnreadMessageResponse getSingleUnreadMessage(@PathVariable String unreadMessageId) {
        return unreadMessageService.unreadMessageResponseMapper(unreadMessageService.getSingleUnreadMessage(unreadMessageId));
    }

    @GetMapping("/conversationGroup/{conversationGroupId}")
    public UnreadMessageResponse geUnreadMessageByConversationGroupId(@PathVariable String conversationGroupId) {
        return unreadMessageService.unreadMessageResponseMapper(unreadMessageService.geUnreadMessageByConversationGroupId(conversationGroupId));
    }

    @GetMapping("/user")
    public List<UnreadMessageResponse> getUnreadMessagesOfAUser() {
        return unreadMessageService.getUserOwnUnreadMessages().stream()
                .map(unreadMessageService::unreadMessageResponseMapper).collect(Collectors.toList());
    }
}
