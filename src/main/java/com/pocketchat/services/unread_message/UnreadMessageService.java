package com.pocketchat.services.unread_message;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UnreadMessageService {
    UnreadMessage addUnreadMessage(CreateUnreadMessageRequest unreadMessage);

    UnreadMessage editUnreadMessage(UpdateUnreadMessageRequest unreadMessage);

    void deleteUnreadMessage(String unreadMessageId);

    UnreadMessage getSingleUnreadMessage(String unreadMessageId);

    UnreadMessage geUnreadMessageByConversationGroupId(String conversationGroupId);

    @Deprecated
    List<UnreadMessage> getUserOwnUnreadMessages();

    Page<UnreadMessage> getUnreadMessagesFromConversationGroupsWithPageable(Page<ConversationGroup> conversationGroups);

    UnreadMessage createUnreadMessageRequestToUnreadMessageMapper(CreateUnreadMessageRequest createUnreadMessageRequest);

    UnreadMessage updateUnreadMessageRequestToUnreadMessageMapper(UpdateUnreadMessageRequest updateUnreadMessageRequest);

    UnreadMessageResponse unreadMessageResponseMapper(UnreadMessage unreadMessage);

    Page<UnreadMessageResponse> unreadMessageResponsePageMapper(Page<UnreadMessage> unreadMessages);
}
