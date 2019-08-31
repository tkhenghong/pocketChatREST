package com.pocketchat.services.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;

import java.util.List;
import java.util.Optional;

public interface ConversationGroupService {
    ConversationGroup addConversation(ConversationGroup conversationGroup);

    void editConversation(ConversationGroup conversationGroup);

    void deleteConversation(String conversationId);

    ConversationGroup getSingleConversation(String conversationId);

    List<ConversationGroup> getConversationsForUser(String userId);
}
