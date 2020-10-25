package com.pocketchat.db.repo_services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.repositories.multimedia.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    public List<Multimedia> findByIdList(List<String> multimediaIds) {

        // List to Iterable.
        // https://stackoverflow.com/questions/10335662
        Iterable<Multimedia> multimediaIterable = multimediaRepository.findAllById(multimediaIds);

        // Iterable<?> to List with Java 8 Stream.
        // https://www.baeldung.com/java-iterable-to-collection
        return StreamSupport.stream(multimediaIterable.spliterator(), false).collect(Collectors.toList());
    }

    public Multimedia save(Multimedia conversationGroup) {
        return multimediaRepository.save(conversationGroup);
    }

    public void delete(Multimedia conversationGroup) {
        multimediaRepository.delete(conversationGroup);
    }
}
