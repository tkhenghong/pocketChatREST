package com.pocketchat.services.unreadMessage;

import com.pocketchat.db.models.unread_message.UnreadMessage;

import java.util.List;

public interface UnreadMessageService {
    UnreadMessage addUnreadMessage(UnreadMessage unreadMessage);

    void editUnreadMessage(UnreadMessage unreadMessage);

    void deleteUnreadMessage(String unreadMessageId);

    UnreadMessage getSingleMultimedia(String unreadMessageId);

    List<UnreadMessage> getUnreadMessagesOfAUser(String userId);
}
