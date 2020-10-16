package com.pocketchat.db.repo_services.conversation_group_mute_notification;

import com.pocketchat.db.models.conversation_group_mute_notification.ConversationGroupMuteNotification;
import com.pocketchat.db.repositories.conversation_group_mute_notification.ConversationGroupMuteNotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationGroupMuteNotificationRepoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConversationGroupMuteNotificationRepository conversationGroupMuteNotificationRepository;

    @Autowired
    public ConversationGroupMuteNotificationRepoService(ConversationGroupMuteNotificationRepository conversationGroupMuteNotificationRepository) {
        this.conversationGroupMuteNotificationRepository = conversationGroupMuteNotificationRepository;
    }

    public Optional<ConversationGroupMuteNotification> findById(String conversationGroupMuteNotificationId) {
        return conversationGroupMuteNotificationRepository.findById(conversationGroupMuteNotificationId);
    }

    public boolean existUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId) {
        return conversationGroupMuteNotificationRepository.existsByUserContactIdAndConversationGroupId(userContactId, conversationGroupId);
    }

    /**
     * To find a conversation group mute notification record by sending full command to MongoDB, instead of taking the records out,
     * and find the conversation group using Java .contains() method.
     *
     * @param userContactId:       ID of the UserContact object.
     * @param conversationGroupId: ID of the ConversationGroup object.
     * @return An Optional object which probably has ConversationGroupMuteNotification object in it.
     */
    public Optional<ConversationGroupMuteNotification> findByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId) {
        return conversationGroupMuteNotificationRepository.findByUserContactIdAndConversationGroupId(userContactId, conversationGroupId);
    }

    public void deleteById(String conversationGroupMuteNotificationId) {
        conversationGroupMuteNotificationRepository.deleteById(conversationGroupMuteNotificationId);
    }

    public ConversationGroupMuteNotification save(ConversationGroupMuteNotification conversationGroupMuteNotification) {
        return conversationGroupMuteNotificationRepository.save(conversationGroupMuteNotification);
    }
}
