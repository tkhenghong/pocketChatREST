package com.pocketchat.controllers.response.conversationGroup;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ConversationGroupResponse {
    private String id;

    private String creatorUserId;

    private long createdDate;

    private String name;

    private String type;

    String description;

    private List<String> memberIds;

    private List<String> adminMemberIds;

    private boolean block;

    private long notificationExpireDate;
}
