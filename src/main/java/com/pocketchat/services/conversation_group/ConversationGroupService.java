package com.pocketchat.services.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.controllers.response.conversation_group.ConversationPageableResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface ConversationGroupService {
    ConversationGroup addConversation(CreateConversationGroupRequest createConversationGroupRequest);

    MultimediaResponse uploadConversationGroupProfilePhoto(String conversationGroupId, MultipartFile multipartFile);

    File getConversationGroupGroupPhoto(String conversationGroupId) throws FileNotFoundException;

    void deleteConversationGroupProfilePhoto(String conversationGroupId);

    ConversationGroup editConversation(UpdateConversationGroupRequest updateConversationGroupRequest);

    void deleteConversation(String conversationId);

    ConversationGroup getSingleConversation(String conversationId);

    List<ConversationGroup> getUserOwnConversationGroups();

    Page<ConversationGroup> getUserOwnConversationGroups(Pageable pageable);

    ConversationGroup createConversationGroupRequestToConversationGroupMapper(CreateConversationGroupRequest createConversationGroupRequest);

    ConversationGroup updateConversationGroupRequestToConversationGroupMapper(UpdateConversationGroupRequest updateConversationGroupRequest);

    ConversationGroupResponse conversationGroupResponseMapper(ConversationGroup conversationGroup);

    Page<ConversationGroupResponse> conversationGroupResponsePageMapper(Page<ConversationGroup> conversationGroups);

    ConversationPageableResponse conversationPageableResponseMapper(Page<ConversationGroupResponse> conversationGroupResponses,
                                                                    Page<UnreadMessageResponse> unreadMessageResponses);
}
