package com.pocketchat.db.repo_services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.repositories.multimedia.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MultimediaRepoService {

    private final MultimediaRepository multimediaRepository;

    @Autowired
    MultimediaRepoService(MultimediaRepository multimediaRepository) {
        this.multimediaRepository = multimediaRepository;
    }

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
