package com.pocketchat.services.unreadMessage;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.models.controllers.request.unreadMessage.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unreadMessage.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unreadMessage.UnreadMessageResponse;

import java.util.List;

public interface UnreadMessageService {
    UnreadMessageResponse addUnreadMessage(CreateUnreadMessageRequest unreadMessage);

    UnreadMessageResponse editUnreadMessage(UpdateUnreadMessageRequest unreadMessage);

    void deleteUnreadMessage(String unreadMessageId);

    UnreadMessage getSingleUnreadMessage(String unreadMessageId);

    List<UnreadMessageResponse> getUnreadMessagesOfAUser(String userId);

    UnreadMessage createUnreadMessageRequestToUnreadMessageMapper(CreateUnreadMessageRequest createUnreadMessageRequest);

    UnreadMessage updateUnreadMessageRequestToUnreadMessageMapper(UpdateUnreadMessageRequest updateUnreadMessageRequest);

    UnreadMessageResponse unreadMessageResponseMapper(UnreadMessage unreadMessage);
}
