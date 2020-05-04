package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.request.multimedia.CreateMultimediaRequest;
import com.pocketchat.models.controllers.request.multimedia.UpdateMultimediaRequest;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;

import java.util.List;

public interface MultimediaService {
    MultimediaResponse addMultimedia(CreateMultimediaRequest multimedia);

    MultimediaResponse editMultimedia(UpdateMultimediaRequest multimedia);

    void deleteMultimedia(String multimediaId);

    Multimedia getSingleMultimedia(String multimediaId);

    MultimediaResponse getMultimediaOfAUser(String userId);

    MultimediaResponse getMultimediaOfAUserContact(String userContactId);

    MultimediaResponse getConversationGroupMultimedia(String conversationGroupId);

    MultimediaResponse getMessageMultimedia(String conversationGroupId, String messageId);

    List<MultimediaResponse> getMultimediaOfAConversation(String conversationGroupId);

    Multimedia createMultimediaRequestToMultimediaMapper(CreateMultimediaRequest createMultimediaRequest);

    Multimedia updateMultimediaRequestToMultimediaMapper(UpdateMultimediaRequest updateMultimediaRequest);

    MultimediaResponse multimediaResponseMapper(Multimedia multimedia);
}
