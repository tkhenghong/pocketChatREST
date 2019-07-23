package com.pocketchat.dbRepoServices.multimedia;

import com.pocketchat.dbRepositories.multimedia.MultimediaRepository;
import com.pocketchat.models.multimedia.Multimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultimediaRepoService {

    @Autowired
    MultimediaRepository multimediaRepository;

    public MultimediaRepository getMultimediaRepository() {
        return multimediaRepository;
    }

    public Multimedia save(Multimedia conversationGroup) {
        return multimediaRepository.save(conversationGroup);
    }

    public void delete(Multimedia conversationGroup) {
        multimediaRepository.delete(conversationGroup);
    }
}
