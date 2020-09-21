package com.pocketchat.models.enums.conversation_group;

public enum ConversationGroupType {
    Personal("Personal"),
    Group("Group"),
    Broadcast("Broadcast")
    ;

    private final String conversationGroupType;

    ConversationGroupType(String conversationGroupType) {
        this.conversationGroupType = conversationGroupType;
    }

    public String getConversationGroupType() {
        return conversationGroupType;
    }
}
