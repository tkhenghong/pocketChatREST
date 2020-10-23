package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;

import java.util.List;

public interface MultimediaService {
    Multimedia addMultimedia(Multimedia multimedia);

    Multimedia editMultimedia(Multimedia multimedia);

    void deleteMultimedia(String multimediaId);

    Multimedia getSingleMultimedia(String multimediaId);

    List<Multimedia> getMultipleMultimedia(List<String> multimediaList);

    MultimediaResponse multimediaResponseMapper(Multimedia multimedia);
}
