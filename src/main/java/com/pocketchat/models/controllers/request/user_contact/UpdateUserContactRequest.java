package com.pocketchat.models.controllers.request.user_contact;

import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateUserContactRequest extends UserContactRequest {
    UpdateUserContactRequest(String id, @NotBlank String displayName, @NotBlank String realName, String about, @Valid @NotEmpty @Size(min = 1) List<String> userIds, String userId, @NotBlank String mobileNo, DateTime lastSeenDate, boolean block, String multimediaId) {
        super(id, displayName, realName, about, userIds, userId, mobileNo, lastSeenDate, block, multimediaId);
    }
}
