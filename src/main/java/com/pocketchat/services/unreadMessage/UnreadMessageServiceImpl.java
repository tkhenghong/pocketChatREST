package com.pocketchat.services.unreadMessage;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.repoServices.unreadMessage.UnreadMessageRepoService;
import com.pocketchat.server.exceptions.unreadMessage.UnreadMessageNotFoundException;
import com.pocketchat.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnreadMessageServiceImpl implements UnreadMessageService {
    private final UnreadMessageRepoService unreadMessageRepoService;
    private final UserService userService;

    @Autowired
    public UnreadMessageServiceImpl(UnreadMessageRepoService unreadMessageRepoService, UserService userService) {
        this.unreadMessageRepoService = unreadMessageRepoService;
        this.userService = userService;
    }

    @Override
    public UnreadMessage addUnreadMessage(UnreadMessage unreadMessage) {
        return unreadMessageRepoService.save(unreadMessage);
    }

    @Override
    public void editUnreadMessage(UnreadMessage unreadMessage) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessage.getId());
        validateUnreadMessageNotFound(unreadMessageOptional, unreadMessage.getId());
        addUnreadMessage(unreadMessage);
    }

    @Override
    public void deleteUnreadMessage(String unreadMessageId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessageId);
        validateUnreadMessageNotFound(unreadMessageOptional, unreadMessageId);
        unreadMessageRepoService.delete(unreadMessageOptional.get());
    }

    @Override
    public UnreadMessage getSingleMultimedia(String unreadMessageId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessageId);
        validateUnreadMessageNotFound(unreadMessageOptional, unreadMessageId);
        return unreadMessageOptional.get();
    }

    @Override
    public List<UnreadMessage> getUnreadMessagesOfAUser(String userId) {
        User user = userService.getUser(userId);
        List<UnreadMessage> unreadMessageList = unreadMessageRepoService.findAllByUserId(user.getId());
        return unreadMessageList;
    }

    private void validateUnreadMessageNotFound(Optional<UnreadMessage> unreadMessageOptional, String unreadMessageId) {
        if (!unreadMessageOptional.isPresent()) {
            throw new UnreadMessageNotFoundException("Unread Message not found, id:-" + unreadMessageId);
        }
    }
}
