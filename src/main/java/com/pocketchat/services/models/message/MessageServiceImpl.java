package com.pocketchat.services.models.message;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.repoServices.conversationGroup.ConversationGroupRepoService;
import com.pocketchat.db.repoServices.message.MessageRepoService;
import com.pocketchat.server.exceptions.conversationGroup.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.message.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepoService messageRepoService;
    private final ConversationGroupRepoService conversationGroupRepoService;

    @Autowired
    public MessageServiceImpl(MessageRepoService messageRepoService, ConversationGroupRepoService conversationGroupRepoService) {
        this.messageRepoService = messageRepoService;
        this.conversationGroupRepoService = conversationGroupRepoService;
    }

    @Override
    public ChatMessage addMessage(ChatMessage chatMessage) {
        return messageRepoService.save(chatMessage);
    }

    @Override
    public void editMessage(ChatMessage chatMessage) {
        getSingleMessage(chatMessage.getId());
        messageRepoService.save(chatMessage);
    }

    @Override
    public void deleteMessage(String messageId) {
        messageRepoService.delete(getSingleMessage(messageId));
    }

    @Override
    public ChatMessage getSingleMessage(String messageId) {
        Optional<ChatMessage> messageOptional = messageRepoService.findById(messageId);
        return validateMessageNotFound(messageOptional, messageId);
    }

    @Override
    public List<ChatMessage> getMessagesOfAConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId:-" + conversationGroupId);
        }
        List<ChatMessage> chatMessageList = messageRepoService.findAllMessagesByConversationId(conversationGroupId);
        if (chatMessageList.isEmpty()) {
            throw new MessageNotFoundException("No message found for this conversationGroupId:-" + conversationGroupId);
        }
        return chatMessageList;
    }

    private ChatMessage validateMessageNotFound(Optional<ChatMessage> messageOptional, String messageId) {
        if (!messageOptional.isPresent()) {
            throw new MessageNotFoundException("messageId-" + messageId);
        } else {
            return messageOptional.get();
        }
    }
}
