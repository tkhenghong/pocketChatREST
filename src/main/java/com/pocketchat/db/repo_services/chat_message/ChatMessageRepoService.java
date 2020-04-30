package com.pocketchat.db.repo_services.chat_message;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.repositories.chat_message.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageRepoService {

    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    ChatMessageRepoService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public Optional<ChatMessage> findById(String messageId) {
        return chatMessageRepository.findById(messageId);
    }

    public List<ChatMessage> findAllMessagesByConversationId (String conversationId) {
        return chatMessageRepository.findAllByConversationId(conversationId);
    }

    public ChatMessage save(ChatMessage conversationGroup) {
        return chatMessageRepository.save(conversationGroup);
    }

    public void delete(ChatMessage chatMessage) {
        chatMessageRepository.delete(chatMessage);
    }
}
