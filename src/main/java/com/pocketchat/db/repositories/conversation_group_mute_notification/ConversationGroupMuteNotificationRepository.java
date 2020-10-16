package com.pocketchat.db.repositories.conversation_group_mute_notification;

import com.pocketchat.db.models.conversation_group_mute_notification.ConversationGroupMuteNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConversationGroupMuteNotificationRepository extends MongoRepository<ConversationGroupMuteNotification, String> {
    boolean existsByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId);
    Optional<ConversationGroupMuteNotification> findByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId);
}
