package com.pocketchat.models.websocket;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.settings.Settings;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.models.user.User;
import com.pocketchat.db.models.user_contact.UserContact;

public class WebSocketMessage {
    private ConversationGroup conversationGroup;
    private ChatMessage chatMessage;
    private Multimedia multimedia;
    private Settings settings;
    private UnreadMessage unreadMessage;
    private User user;
    private UserContact userContact;

    WebSocketMessage(ConversationGroup conversationGroup, ChatMessage chatMessage, Multimedia multimedia, Settings settings, UnreadMessage unreadMessage, User user, UserContact userContact) {
        this.conversationGroup = conversationGroup;
        this.chatMessage = chatMessage;
        this.multimedia = multimedia;
        this.settings = settings;
        this.unreadMessage = unreadMessage;
        this.user = user;
        this.userContact = userContact;
    }

    public static WebSocketMessageBuilder builder() {
        return new WebSocketMessageBuilder();
    }

    public ConversationGroup getConversationGroup() {
        return this.conversationGroup;
    }

    public ChatMessage getChatMessage() {
        return this.chatMessage;
    }

    public Multimedia getMultimedia() {
        return this.multimedia;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public UnreadMessage getUnreadMessage() {
        return this.unreadMessage;
    }

    public User getUser() {
        return this.user;
    }

    public UserContact getUserContact() {
        return this.userContact;
    }

    public void setConversationGroup(ConversationGroup conversationGroup) {
        this.conversationGroup = conversationGroup;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public void setUnreadMessage(UnreadMessage unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserContact(UserContact userContact) {
        this.userContact = userContact;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof WebSocketMessage)) return false;
        final WebSocketMessage other = (WebSocketMessage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationGroup = this.getConversationGroup();
        final Object other$conversationGroup = other.getConversationGroup();
        if (this$conversationGroup == null ? other$conversationGroup != null : !this$conversationGroup.equals(other$conversationGroup))
            return false;
        final Object this$chatMessage = this.getChatMessage();
        final Object other$chatMessage = other.getChatMessage();
        if (this$chatMessage == null ? other$chatMessage != null : !this$chatMessage.equals(other$chatMessage))
            return false;
        final Object this$multimedia = this.getMultimedia();
        final Object other$multimedia = other.getMultimedia();
        if (this$multimedia == null ? other$multimedia != null : !this$multimedia.equals(other$multimedia))
            return false;
        final Object this$settings = this.getSettings();
        final Object other$settings = other.getSettings();
        if (this$settings == null ? other$settings != null : !this$settings.equals(other$settings)) return false;
        final Object this$unreadMessage = this.getUnreadMessage();
        final Object other$unreadMessage = other.getUnreadMessage();
        if (this$unreadMessage == null ? other$unreadMessage != null : !this$unreadMessage.equals(other$unreadMessage))
            return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$userContact = this.getUserContact();
        final Object other$userContact = other.getUserContact();
        if (this$userContact == null ? other$userContact != null : !this$userContact.equals(other$userContact))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WebSocketMessage;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationGroup = this.getConversationGroup();
        result = result * PRIME + ($conversationGroup == null ? 43 : $conversationGroup.hashCode());
        final Object $chatMessage = this.getChatMessage();
        result = result * PRIME + ($chatMessage == null ? 43 : $chatMessage.hashCode());
        final Object $multimedia = this.getMultimedia();
        result = result * PRIME + ($multimedia == null ? 43 : $multimedia.hashCode());
        final Object $settings = this.getSettings();
        result = result * PRIME + ($settings == null ? 43 : $settings.hashCode());
        final Object $unreadMessage = this.getUnreadMessage();
        result = result * PRIME + ($unreadMessage == null ? 43 : $unreadMessage.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $userContact = this.getUserContact();
        result = result * PRIME + ($userContact == null ? 43 : $userContact.hashCode());
        return result;
    }

    public String toString() {
        return "WebSocketMessage(conversationGroup=" + this.getConversationGroup() + ", chatMessage=" + this.getChatMessage() + ", multimedia=" + this.getMultimedia() + ", settings=" + this.getSettings() + ", unreadMessage=" + this.getUnreadMessage() + ", user=" + this.getUser() + ", userContact=" + this.getUserContact() + ")";
    }

    public static class WebSocketMessageBuilder {
        private ConversationGroup conversationGroup;
        private ChatMessage chatMessage;
        private Multimedia multimedia;
        private Settings settings;
        private UnreadMessage unreadMessage;
        private User user;
        private UserContact userContact;

        WebSocketMessageBuilder() {
        }

        public WebSocketMessage.WebSocketMessageBuilder conversationGroup(ConversationGroup conversationGroup) {
            this.conversationGroup = conversationGroup;
            return this;
        }

        public WebSocketMessage.WebSocketMessageBuilder chatMessage(ChatMessage chatMessage) {
            this.chatMessage = chatMessage;
            return this;
        }

        public WebSocketMessage.WebSocketMessageBuilder multimedia(Multimedia multimedia) {
            this.multimedia = multimedia;
            return this;
        }

        public WebSocketMessage.WebSocketMessageBuilder settings(Settings settings) {
            this.settings = settings;
            return this;
        }

        public WebSocketMessage.WebSocketMessageBuilder unreadMessage(UnreadMessage unreadMessage) {
            this.unreadMessage = unreadMessage;
            return this;
        }

        public WebSocketMessage.WebSocketMessageBuilder user(User user) {
            this.user = user;
            return this;
        }

        public WebSocketMessage.WebSocketMessageBuilder userContact(UserContact userContact) {
            this.userContact = userContact;
            return this;
        }

        public WebSocketMessage build() {
            return new WebSocketMessage(conversationGroup, chatMessage, multimedia, settings, unreadMessage, user, userContact);
        }

        public String toString() {
            return "WebSocketMessage.WebSocketMessageBuilder(conversationGroup=" + this.conversationGroup + ", chatMessage=" + this.chatMessage + ", multimedia=" + this.multimedia + ", settings=" + this.settings + ", unreadMessage=" + this.unreadMessage + ", user=" + this.user + ", userContact=" + this.userContact + ")";
        }
    }
}
