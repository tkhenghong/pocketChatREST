package com.pocketchat.dbRepoServices.unreadMessage;

import com.pocketchat.dbRepositories.unreadMessage.UnreadMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnreadMessageRepoService {
    @Autowired
    private UnreadMessageRepository unreadMessageRepository;

    public UnreadMessageRepository getUnreadMessageRepository() {
        return unreadMessageRepository;
    }
}
