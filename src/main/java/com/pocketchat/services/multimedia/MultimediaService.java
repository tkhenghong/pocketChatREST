package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.request.multimedia.CreateMultimediaRequest;
import com.pocketchat.models.controllers.request.multimedia.UpdateMultimediaRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;

import java.util.List;

public interface MultimediaService {
    Multimedia addMultimedia(CreateMultimediaRequest multimedia);

    Multimedia editMultimedia(UpdateMultimediaRequest multimedia);

    void deleteMultimedia(String multimediaId);

    Multimedia getSingleMultimedia(String multimediaId);

    Multimedia getMultimediaOfAUser(String userId);

    Multimedia getUserOwnProfilePictureMultimedia();

    Multimedia getMultimediaOfAUserContact(String userContactId);

    Multimedia getConversationGroupMultimedia(String conversationGroupId);

    Multimedia getMessageMultimedia(String conversationGroupId, String messageId);

    List<Multimedia> getMultimediaOfAConversation(String conversationGroupId);

    Multimedia createMultimediaRequestToMultimediaMapper(CreateMultimediaRequest createMultimediaRequest);

    Multimedia updateMultimediaRequestToMultimediaMapper(UpdateMultimediaRequest updateMultimediaRequest);

    MultimediaResponse multimediaResponseMapper(Multimedia multimedia);
}
