package com.pocketchat.db.repo_services.message;

import com.pocketchat.db.repositories.message.MessageRepository;
import com.pocketchat.db.models.chat_message.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageRepoService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<ChatMessage> findById(String messageId) {
        return messageRepository.findById(messageId);
    }

    public List<ChatMessage> findAllMessagesByConversationId (String conversationId) {
        return messageRepository.findAllByConversationId(conversationId);
    }

    public ChatMessage save(ChatMessage conversationGroup) {
        return messageRepository.save(conversationGroup);
    }

    public void delete(ChatMessage chatMessage) {
        messageRepository.delete(chatMessage);
    }

}
