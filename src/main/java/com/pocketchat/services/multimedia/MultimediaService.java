package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;

import java.util.List;

public interface MultimediaService {
    Multimedia addMultimedia(Multimedia multimedia);

    void editMultimedia(Multimedia multimedia);

    void deleteMultimedia(String multimediaId);

    List<Multimedia> getMultimediaOfAUser(String userId);

    List<Multimedia> getMultimediaOfAConversation(String conversationId);
}
