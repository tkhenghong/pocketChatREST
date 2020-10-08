package com.pocketchat.services.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConversationGroupService {
    ConversationGroup addConversation(CreateConversationGroupRequest createConversationGroupRequest);

    void uploadConversationGroupProfilePhoto(String conversationGroupId, MultipartFile multipartFile);

    ConversationGroup editConversation(UpdateConversationGroupRequest updateConversationGroupRequest);

    void deleteConversation(String conversationId);

    ConversationGroup getSingleConversation(String conversationId);

    List<ConversationGroup> getUserOwnConversationGroups();

    List<ConversationGroup> findAllByMemberIds(String userContactId);

    ConversationGroup createConversationGroupRequestToConversationGroupMapper(CreateConversationGroupRequest createConversationGroupRequest);

    ConversationGroup updateConversationGroupRequestToConversationGroupMapper(UpdateConversationGroupRequest updateConversationGroupRequest);

    ConversationGroupResponse conversationGroupResponseMapper(ConversationGroup conversationGroup);
}
