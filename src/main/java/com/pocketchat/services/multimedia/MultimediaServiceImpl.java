package com.pocketchat.services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.repo_services.multimedia.MultimediaRepoService;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.server.exceptions.general.StringNotEmptyException;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.Optional;

@Service
public class MultimediaServiceImpl implements MultimediaService {

    private final MultimediaRepoService multimediaRepoService;

    @Autowired
    public MultimediaServiceImpl(MultimediaRepoService multimediaRepoService) {
        this.multimediaRepoService = multimediaRepoService;
    }

    @Override
    @Transactional
    public Multimedia addMultimedia(Multimedia multimedia) {
        if (!StringUtils.isEmpty(multimedia.getId())) {
            // Prevent multimedia from updated using the wrong method.
            throw new StringNotEmptyException("Unable to add multimedia that has already exist. multimediaId: " + multimedia.getId());
        }
        return multimediaRepoService.save(multimedia);
    }

    @Override
    @Transactional
    public Multimedia editMultimedia(Multimedia multimedia) {
        getSingleMultimedia(multimedia.getId());
        return multimediaRepoService.save(multimedia); // Straight save
    }

    @Override
    @Transactional
    public void deleteMultimedia(String multimediaId) {
        multimediaRepoService.delete(getSingleMultimedia(multimediaId));
    }

    @Override
    public Multimedia getSingleMultimedia(String multimediaId) {
        Optional<Multimedia> multimediaOptional = multimediaRepoService.findById(multimediaId);
        if (multimediaOptional.isEmpty()) {
            throw new MultimediaNotFoundException("Unable to find multimedia with multimediaId: " + multimediaId);
        }
        return multimediaOptional.get();
    }

    @Override
    public MultimediaResponse multimediaResponseMapper(Multimedia multimedia) {
        return MultimediaResponse.builder()
                .id(multimedia.getId())
                .fileDirectory(multimedia.getFileDirectory())
                .fileName(multimedia.getFileName())
                .contentType(multimedia.getContentType())
                .fileExtension(multimedia.getFileExtension())
                .fileSize(multimedia.getFileSize())
                .multimediaType(multimedia.getMultimediaType())
                .createdBy(multimedia.getCreatedBy())
                .createdDate(multimedia.getCreatedDate())
                .lastModifiedBy(multimedia.getLastModifiedBy())
                .lastModifiedDate(multimedia.getLastModifiedDate())
                .version(multimedia.getVersion())
                .build();
    }
}
