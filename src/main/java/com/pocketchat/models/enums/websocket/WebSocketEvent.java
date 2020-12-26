package com.pocketchat.models.enums.websocket;

public enum WebSocketEvent {
    // Conversation Group Events
    NEW_CONVERSATION_GROUP, // Informs a new conversation group has been created.
    JOINED_CONVERSATION_GROUP, // Informs a group member of the ConversationGroup object has joined.
    LEFT_CONVERSATION_GROUP, // Informs a group member of the ConversationGroup object has left.
    UPLOADED_GROUP_PHOTO,  // Informs the group photo of the ConversationGroup object has been added.
    CHANGED_GROUP_PHOTO,  // Informs the group photo of the ConversationGroup object has been changed.
    DELETED_GROUP_PHOTO, // Informs the group photo of the ConversationGroup object has been removed.
    CHANGED_GROUP_NAME, // Informs the name of the ConversationGroup object has been changed.
    CHANGED_GROUP_DESCRIPTION, // Informs the description of the ConversationGroup object has been changed.
    PROMOTE_GROUP_ADMIN, // Informs a group member of the ConversationGroup object has been promoted from group member to group admin.
    DEMOTE_GROUP_ADMIN, // Informs a group member of the ConversationGroup object has been demoted from group admin to normal group member.
    ADD_GROUP_MEMBER, // Informs a group member of the ConversationGroup object has been added.
    REMOVE_GROUP_MEMBER, // Informs a group member of the ConversationGroup object has been removed.
    CHANGED_PHONE_NUMBER, // Informs a group member of the ConversationGroup object has been changed.

    // Chat Message Events
    ADDED_CHAT_MESSAGE, // Informs an ChatMessage object has been added.
    UPDATED_CHAT_MESSAGE, // Informs an ChatMessage object's content has been edited.
    UPLOADED_CHAT_MESSAGE_MULTIMEDIA, // Informs an ChatMessage object's Multimedia object has been added.
    DELETED_CHAT_MESSAGE, // Informs an ChatMessage object has been deleted.
    DELETED_CHAT_MESSAGE_MULTIMEDIA, // Informs an ChatMessage object's Multimedia object has been deleted.

    // Unread Message Events
    NEW_UNREAD_MESSAGE, // Informs a new UnreadMessage object has been added.
    UPDATED_UNREAD_MESSAGE, // Informs an UnreadMessage object has been updated.
    DELETED_UNREAD_MESSAGE, // Informs an UnreadMessage object has been deleted.

    // User Events
    UPDATED_USER, // Informs updated user info.

    // Settings Events
    UPDATED_SETTINGS, // Informs updated user settings.

    // User Contact Events
    EXISTING_CONTACT_JOINED // Informs a group member of the ConversationGroup object has joined.
}
