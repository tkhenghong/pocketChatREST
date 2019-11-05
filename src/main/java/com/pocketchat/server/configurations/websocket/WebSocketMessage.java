package com.pocketchat.server.configurations.websocket;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.message.Message;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_contact.UserContact;

public class WebSocketMessage {
    private ConversationGroup conversationGroup;
    private Message message;
    private Multimedia multimedia;
    private Settings settings;
    private UnreadMessage unreadMessage;
    private User user;
    private UserContact userContact;

    public ConversationGroup getConversationGroup() {
        return conversationGroup;
    }

    public void setConversationGroup(ConversationGroup conversationGroup) {
        this.conversationGroup = conversationGroup;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public UnreadMessage getUnreadMessage() {
        return unreadMessage;
    }

    public void setUnreadMessage(UnreadMessage unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserContact getUserContact() {
        return userContact;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }
}
