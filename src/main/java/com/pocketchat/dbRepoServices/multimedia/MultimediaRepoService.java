package com.pocketchat.dbRepoServices.multimedia;

import com.pocketchat.dbRepositories.multimedia.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaRepoService {

    @Autowired
    MultimediaRepository multimediaRepository;

    public MultimediaRepository getMultimediaRepository() {
        return multimediaRepository;
    }
}
