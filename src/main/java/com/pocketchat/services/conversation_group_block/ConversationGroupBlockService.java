package com.pocketchat.services.conversation_group_block;

import com.pocketchat.db.models.conversation_group_block.ConversationGroupBlock;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupBlockRequest;
import com.pocketchat.models.controllers.response.conversation_group_block.ConversationGroupBlockResponse;

public interface ConversationGroupBlockService {

    ConversationGroupBlock addConversationGroupBlock(CreateConversationGroupBlockRequest createConversationGroupBlockRequest);

    boolean isConversationGroupBlocked(String userContactId, String conversationGroupId);

    ConversationGroupBlock getSingleConversationGroupBlock(String conversationGroupBlockId);

    void deleteConversationGroupBlock(String conversationGroupBlockId);

    ConversationGroupBlockResponse conversationGroupBlockResponseMapper(ConversationGroupBlock conversationGroupBlock);
}
