package com.pocketchat.models.controllers.request.conversation_group;

import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateConversationGroupRequest extends ConversationGroupRequest {

    UpdateConversationGroupRequest(String id, @NotBlank String creatorUserId, @NotNull DateTime createdDate, @NotBlank String name, @NotBlank String type, String description, @Valid @NotEmpty @Size(min = 1) List<String> memberIds, @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds, boolean block, @NotEmpty DateTime notificationExpireDate) {
        super(id, creatorUserId, createdDate, name, type, description, memberIds, adminMemberIds, block, notificationExpireDate);
    }
}
