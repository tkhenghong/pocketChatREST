package com.pocketchat.db.repoServices.multimedia;

import com.pocketchat.db.repositories.multimedia.MultimediaRepository;
import com.pocketchat.db.models.multimedia.Multimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultimediaRepoService {

    @Autowired
    MultimediaRepository multimediaRepository;

    public Optional<Multimedia> findById(String multimediaId) {
        return multimediaRepository.findById(multimediaId);
    }

    public Multimedia save(Multimedia conversationGroup) {
        return multimediaRepository.save(conversationGroup);
    }

    public void delete(Multimedia conversationGroup) {
        multimediaRepository.delete(conversationGroup);
    }
}
