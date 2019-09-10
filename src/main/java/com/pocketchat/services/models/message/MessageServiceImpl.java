package com.pocketchat.services.message;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.message.Message;
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
    public Message addMessage(Message message) {
        return messageRepoService.save(message);
    }

    @Override
    public void editMessage(Message message) {
        getSingleMessage(message.getId());
        addMessage(message);
    }

    @Override
    public void deleteMessage(String messageId) {
        messageRepoService.delete(getSingleMessage(messageId));
    }

    @Override
    public Message getSingleMessage(String messageId) {
        Optional<Message> messageOptional = messageRepoService.findById(messageId);
        return validateMessageNotFound(messageOptional, messageId);
    }

    @Override
    public List<Message> getMessagesOfAConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId:-" + conversationGroupId);
        }
        List<Message> messageList = messageRepoService.findAllMessagesByConversationId(conversationGroupId);
        if (messageList.isEmpty()) {
            throw new MessageNotFoundException("No message found for this conversationGroupId:-" + conversationGroupId);
        }
        return messageList;
    }

    private Message validateMessageNotFound(Optional<Message> messageOptional, String messageId) {
        if (!messageOptional.isPresent()) {
            throw new MessageNotFoundException("messageId-" + messageId);
        } else {
            return messageOptional.get();
        }
    }
}
