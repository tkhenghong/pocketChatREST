package com.pocketchat.controllers.response.unreadMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnreadMessageResponse {
    private String id;

    private String conversationId;

    private String userId;

    private String lastMessage;

    private long date;

    private int count;
}
