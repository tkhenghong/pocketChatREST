package com.pocketchat.db.repo_services.multimedia;

import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.repositories.multimedia.MultimediaRepository;
import com.pocketchat.server.exceptions.multimedia.MultimediaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultimediaRepoService {

    @Autowired
    MultimediaRepository multimediaRepository;

    public Optional<Multimedia> findById(String multimediaId) {
        return multimediaRepository.findById(multimediaId);
    }

    public List<Multimedia> findAllByConversationId (List<String> conversationGroupIds) {
        List<Multimedia> multimediaList = multimediaRepository.findAllByConversationId(conversationGroupIds);
        if(multimediaList.isEmpty()) {
            throw new MultimediaNotFoundException("No multimedia found for this list of ids!");
        }
        return multimediaList;
    }

    public List<Multimedia> findAllByConversationGroupId(String conversationGroupId) {
        List<Multimedia> multimediaList = multimediaRepository.findAllByConversationId(conversationGroupId);
        if(multimediaList.isEmpty()) {
            throw new MultimediaNotFoundException("No multimedia found for this id:-" + conversationGroupId);
        }
        return multimediaList;
    }

    public Optional<Multimedia> findByUserId(String userId) {
        return multimediaRepository.findByUserId(userId);
    }

    public Optional<Multimedia> findByUserContactId(String userContactId) {
        return multimediaRepository.findByUserContactId(userContactId);
    }

    public Optional<Multimedia> findGroupPhotoMultimedia(String conversationGroupId) {
        return multimediaRepository.findByConversationIdAndMessageId(conversationGroupId, null);
    }

    public Optional<Multimedia> findMessageMultimedia(String conversationGroupId, String messageId) {
        return multimediaRepository.findByConversationIdAndMessageId(conversationGroupId, messageId);
    }

    public Multimedia save(Multimedia conversationGroup) {
        return multimediaRepository.save(conversationGroup);
    }

    public void delete(Multimedia conversationGroup) {
        multimediaRepository.delete(conversationGroup);
    }
}
