package com.pocketchat.db.repoServices.unreadMessage;

import com.pocketchat.db.repositories.unreadMessage.UnreadMessageRepository;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnreadMessageRepoService {
    @Autowired
    private UnreadMessageRepository unreadMessageRepository;

    public Optional<UnreadMessage> findById(String unreadMessageId) {
        return unreadMessageRepository.findById(unreadMessageId);
    }

    public List<UnreadMessage> findAllByUserId(String userId) {
        return unreadMessageRepository.findAllByUserId(userId);
    }

    public Optional<UnreadMessage> findByConversationGroupId(String conversationGroupId) {
        return unreadMessageRepository.findByConversationId(conversationGroupId);
    }

    public UnreadMessage save(UnreadMessage conversationGroup) {
        return unreadMessageRepository.save(conversationGroup);
    }

    public void delete(UnreadMessage conversationGroup) {
        unreadMessageRepository.delete(conversationGroup);
    }
}
