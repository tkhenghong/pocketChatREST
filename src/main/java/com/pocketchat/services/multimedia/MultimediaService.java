package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;

public interface MultimediaService {
    Multimedia addMultimedia(Multimedia multimedia);

    Multimedia editMultimedia(Multimedia multimedia);

    void deleteMultimedia(String multimediaId);

    Multimedia getSingleMultimedia(String multimediaId);

    MultimediaResponse multimediaResponseMapper(Multimedia multimedia);
}
