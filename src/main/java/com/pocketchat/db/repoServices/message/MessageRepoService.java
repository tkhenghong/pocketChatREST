package com.pocketchat.db.repoServices.message;

import com.pocketchat.db.repositories.message.MessageRepository;
import com.pocketchat.db.models.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageRepoService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> findById(String messageId) {
        return messageRepository.findById(messageId);
    }

    public List<Message> findAllMessagesByConversationId (String conversationId) {
        return messageRepository.findAllByConversationId(conversationId);
    }

    public Message save(Message conversationGroup) {
        return messageRepository.save(conversationGroup);
    }

    public void delete(Message message) {
        messageRepository.delete(message);
    }

}
