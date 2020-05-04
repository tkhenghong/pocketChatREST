package com.pocketchat.server.configurations.websocket;

public class CustomizedWebSocketMessage {
    private String conversationGroup;
    private String message;
    private String multimedia;
    private String settings;
    private String unreadMessage;
    private String user;
    private String userContact;

    CustomizedWebSocketMessage(String conversationGroup, String message, String multimedia, String settings, String unreadMessage, String user, String userContact) {
        this.conversationGroup = conversationGroup;
        this.message = message;
        this.multimedia = multimedia;
        this.settings = settings;
        this.unreadMessage = unreadMessage;
        this.user = user;
        this.userContact = userContact;
    }

    public static CustomizedWebSocketMessageBuilder builder() {
        return new CustomizedWebSocketMessageBuilder();
    }

    public String getConversationGroup() {
        return this.conversationGroup;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMultimedia() {
        return this.multimedia;
    }

    public String getSettings() {
        return this.settings;
    }

    public String getUnreadMessage() {
        return this.unreadMessage;
    }

    public String getUser() {
        return this.user;
    }

    public String getUserContact() {
        return this.userContact;
    }

    public void setConversationGroup(String conversationGroup) {
        this.conversationGroup = conversationGroup;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public void setUnreadMessage(String unreadMessage) {
        this.unreadMessage = unreadMessage;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CustomizedWebSocketMessage)) return false;
        final CustomizedWebSocketMessage other = (CustomizedWebSocketMessage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$conversationGroup = this.getConversationGroup();
        final Object other$conversationGroup = other.getConversationGroup();
        if (this$conversationGroup == null ? other$conversationGroup != null : !this$conversationGroup.equals(other$conversationGroup))
            return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
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
        return other instanceof CustomizedWebSocketMessage;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $conversationGroup = this.getConversationGroup();
        result = result * PRIME + ($conversationGroup == null ? 43 : $conversationGroup.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
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
        return "CustomizedWebSocketMessage(conversationGroup=" + this.getConversationGroup() + ", message=" + this.getMessage() + ", multimedia=" + this.getMultimedia() + ", settings=" + this.getSettings() + ", unreadMessage=" + this.getUnreadMessage() + ", user=" + this.getUser() + ", userContact=" + this.getUserContact() + ")";
    }

    public static class CustomizedWebSocketMessageBuilder {
        private String conversationGroup;
        private String message;
        private String multimedia;
        private String settings;
        private String unreadMessage;
        private String user;
        private String userContact;

        CustomizedWebSocketMessageBuilder() {
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder conversationGroup(String conversationGroup) {
            this.conversationGroup = conversationGroup;
            return this;
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder multimedia(String multimedia) {
            this.multimedia = multimedia;
            return this;
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder settings(String settings) {
            this.settings = settings;
            return this;
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder unreadMessage(String unreadMessage) {
            this.unreadMessage = unreadMessage;
            return this;
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder user(String user) {
            this.user = user;
            return this;
        }

        public CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder userContact(String userContact) {
            this.userContact = userContact;
            return this;
        }

        public CustomizedWebSocketMessage build() {
            return new CustomizedWebSocketMessage(conversationGroup, message, multimedia, settings, unreadMessage, user, userContact);
        }

        public String toString() {
            return "CustomizedWebSocketMessage.CustomizedWebSocketMessageBuilder(conversationGroup=" + this.conversationGroup + ", message=" + this.message + ", multimedia=" + this.multimedia + ", settings=" + this.settings + ", unreadMessage=" + this.unreadMessage + ", user=" + this.user + ", userContact=" + this.userContact + ")";
        }
    }
}
