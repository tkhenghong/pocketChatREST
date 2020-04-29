package com.pocketchat.services.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;

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
