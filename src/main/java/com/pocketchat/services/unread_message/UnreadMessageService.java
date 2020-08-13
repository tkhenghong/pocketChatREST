package com.pocketchat.services.unread_message;

import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;

import java.util.List;

public interface UnreadMessageService {
    UnreadMessageResponse addUnreadMessage(CreateUnreadMessageRequest unreadMessage);

    UnreadMessageResponse editUnreadMessage(UpdateUnreadMessageRequest unreadMessage);

    void deleteUnreadMessage(String unreadMessageId);

    UnreadMessage getSingleUnreadMessage(String unreadMessageId);

    List<UnreadMessageResponse> getUserOwnUnreadMessages();

    UnreadMessage createUnreadMessageRequestToUnreadMessageMapper(CreateUnreadMessageRequest createUnreadMessageRequest);

    UnreadMessage updateUnreadMessageRequestToUnreadMessageMapper(UpdateUnreadMessageRequest updateUnreadMessageRequest);

    UnreadMessageResponse unreadMessageResponseMapper(UnreadMessage unreadMessage);
}
