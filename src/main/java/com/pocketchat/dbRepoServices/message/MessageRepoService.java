package com.pocketchat.dbRepoServices.message;

import com.pocketchat.dbRepositories.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageRepoService {

    @Autowired
    private MessageRepository messageRepository;

    public MessageRepository getMessageRepository() {
        return messageRepository;
    }
}
