package com.pocketchat.services.models.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;

import java.util.List;

public interface ConversationGroupService {
    ConversationGroup addConversation(ConversationGroup conversationGroup);

    void editConversation(ConversationGroup conversationGroup);

    void deleteConversation(String conversationId);

    ConversationGroup getSingleConversation(String conversationId);

    List<ConversationGroup> getConversationsForUserByMobileNo(String mobileNo);

    List<ConversationGroup> findAllByMemberIds(String userContactId);
}
