package com.pocketchat.dbRepoServices.unreadMessage;

import com.pocketchat.dbRepositories.unreadMessage.UnreadMessageRepository;
import com.pocketchat.models.unread_message.UnreadMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnreadMessageRepoService {
    @Autowired
    private UnreadMessageRepository unreadMessageRepository;

    public UnreadMessage save(UnreadMessage conversationGroup) {
        return unreadMessageRepository.save(conversationGroup);
    }

    public void delete(UnreadMessage conversationGroup) {
        unreadMessageRepository.delete(conversationGroup);
    }
}
