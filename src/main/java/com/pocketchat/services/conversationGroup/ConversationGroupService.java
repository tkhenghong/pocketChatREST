package com.pocketchat.services.conversationGroup;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.controllers.request.conversationGroup.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversationGroup.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversationGroup.ConversationGroupResponse;

import java.util.List;

public interface ConversationGroupService {
    ConversationGroupResponse addConversation(CreateConversationGroupRequest conversationGroup);

    ConversationGroupResponse editConversation(UpdateConversationGroupRequest conversationGroup);

    void deleteConversation(String conversationId);

    ConversationGroup getSingleConversation(String conversationId);

    List<ConversationGroupResponse> getConversationsForUserByMobileNo(String mobileNo);

    List<ConversationGroup> findAllByMemberIds(String userContactId);

    ConversationGroup createConversationGroupRequestToConversationGroupMapper(CreateConversationGroupRequest createConversationGroupRequest);

    ConversationGroup updateConversationGroupRequestToConversationGroupMapper(UpdateConversationGroupRequest updateConversationGroupRequest);

    ConversationGroupResponse conversationGroupResponseMapper(ConversationGroup conversationGroup);
}
