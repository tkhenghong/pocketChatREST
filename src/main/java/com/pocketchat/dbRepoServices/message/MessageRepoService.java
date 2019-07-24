package com.pocketchat.dbRepoServices.message;

import com.pocketchat.dbRepositories.message.MessageRepository;
import com.pocketchat.models.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageRepoService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> findById(String messageId) {
        return messageRepository.findById(messageId);
    }

    public Message save(Message conversationGroup) {
        return messageRepository.save(conversationGroup);
    }

    public void delete(Message conversationGroup) {
        messageRepository.delete(conversationGroup);
    }

}
