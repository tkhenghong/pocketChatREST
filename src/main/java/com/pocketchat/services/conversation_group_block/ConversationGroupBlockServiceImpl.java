package com.pocketchat.services.conversation_group_block;

import com.pocketchat.db.models.conversation_group_block.ConversationGroupBlock;
import com.pocketchat.db.repo_services.conversation_group_block.ConversationGroupBlockRepoService;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupBlockRequest;
import com.pocketchat.models.controllers.response.conversation_group_block.ConversationGroupBlockResponse;
import com.pocketchat.server.exceptions.conversation_group_block.ConversationGroupBlockNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationGroupBlockServiceImpl implements ConversationGroupBlockService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversationGroupBlockRepoService conversationGroupBlockRepoService;

    @Autowired
    public ConversationGroupBlockServiceImpl(ConversationGroupBlockRepoService conversationGroupBlockRepoService) {
        this.conversationGroupBlockRepoService = conversationGroupBlockRepoService;
    }

    @Override
    public ConversationGroupBlock addConversationGroupBlock(CreateConversationGroupBlockRequest createConversationGroupBlockRequest) {
        return conversationGroupBlockRepoService.save(createConversationGroupBlockRequestToConversationGroupBlock(createConversationGroupBlockRequest));
    }

    @Override
    public boolean isConversationGroupBlocked(String userContactId, String conversationGroupId) {
        return conversationGroupBlockRepoService.existsByUserContactIdAndConversationGroupId(userContactId, conversationGroupId);
    }

    @Override
    public ConversationGroupBlock getSingleConversationGroupBlock(String conversationGroupBlockId) {
        Optional<ConversationGroupBlock> conversationGroupBlockOptional = conversationGroupBlockRepoService.findById(conversationGroupBlockId);
        if (conversationGroupBlockOptional.isEmpty()) {
            throw new ConversationGroupBlockNotFoundException("Unable to find any block record from the conversationGroupBlockId: " + conversationGroupBlockId);
        }
        return conversationGroupBlockOptional.get();
    }

    @Override
    public void deleteConversationGroupBlock(String conversationGroupBlockId) {
        conversationGroupBlockRepoService.deleteById(conversationGroupBlockId);
    }

    ConversationGroupBlock createConversationGroupBlockRequestToConversationGroupBlock(CreateConversationGroupBlockRequest createConversationGroupBlockRequest) {
        return ConversationGroupBlock.builder()
                .userContactId(createConversationGroupBlockRequest.getUserContactId())
                .conversationGroupId(createConversationGroupBlockRequest.getConversationGroupId())
                .build();
    }

    @Override
    public ConversationGroupBlockResponse conversationGroupBlockResponseMapper(ConversationGroupBlock conversationGroupBlock) {
        return ConversationGroupBlockResponse.builder()
                .id(conversationGroupBlock.getId())
                .userContactId(conversationGroupBlock.getUserContactId())
                .conversationGroupId(conversationGroupBlock.getConversationGroupId())
                .build();
    }
}
