package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;

import java.util.List;

public interface MultimediaService {
    Multimedia addMultimedia(Multimedia multimedia);

    Multimedia editMultimedia(Multimedia multimedia);

    void deleteMultimedia(String multimediaId);

    Multimedia getSingleMultimedia(String multimediaId);

    Multimedia getMultimediaOfAUser(String userId);

    Multimedia getUserOwnProfilePictureMultimedia();

    Multimedia getMultimediaOfAUserContact(String userContactId);

    Multimedia getConversationGroupMultimedia(String conversationGroupId);

    Multimedia getMessageMultimedia(String conversationGroupId, String messageId);

    List<Multimedia> getMultimediaOfAConversation(String conversationGroupId);

    MultimediaResponse multimediaResponseMapper(Multimedia multimedia);
}
