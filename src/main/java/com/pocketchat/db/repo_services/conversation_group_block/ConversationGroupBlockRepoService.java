package com.pocketchat.db.repo_services.conversation_group_block;

import com.pocketchat.db.models.conversation_group_block.ConversationGroupBlock;
import com.pocketchat.db.repositories.conversation_group_block.ConversationGroupBlockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationGroupBlockRepoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConversationGroupBlockRepository conversationGroupBlockRepository;

    @Autowired
    public ConversationGroupBlockRepoService(ConversationGroupBlockRepository conversationGroupBlockRepository) {
        this.conversationGroupBlockRepository = conversationGroupBlockRepository;
    }

    public Optional<ConversationGroupBlock> findById(String conversationGroupBlockId) {
        return conversationGroupBlockRepository.findById(conversationGroupBlockId);
    }

    public boolean existsByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId) {
        return conversationGroupBlockRepository.existsByUserContactIdAndConversationGroupId(userContactId, conversationGroupId);
    }

    /**
     * To find a conversation group block record by sending full command to MongoDB, instead of taking the records out,
     * and find the conversation group using Java .contains() method.
     *
     * @param userContactId:       ID of the UserContact object.
     * @param conversationGroupId: ID of the ConversationGroup object.
     * @return An Optional object which probably has ConversationGroupBlock object in it.
     */
    public Optional<ConversationGroupBlock> findByUserContactIdAndConversationGroupId(String userContactId, String conversationGroupId) {
        return conversationGroupBlockRepository.findByUserContactIdAndConversationGroupId(userContactId, conversationGroupId);
    }

    public void deleteById(String conversationGroupBlockId) {
        conversationGroupBlockRepository.deleteById(conversationGroupBlockId);
    }

    public ConversationGroupBlock save(ConversationGroupBlock conversationGroupBlock) {
        return conversationGroupBlockRepository.save(conversationGroupBlock);
    }
}
