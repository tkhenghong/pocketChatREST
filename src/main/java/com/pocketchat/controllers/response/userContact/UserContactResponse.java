package com.pocketchat.controllers.response.userContact;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class UserContactResponse {

    private String id;

    private String displayName;

    private String realName;

    private String about;

    private List<String> userIds;

    private String userId;

    private String mobileNo;

    private long lastSeenDate;

    private boolean block;

    private String multimediaId;
}
