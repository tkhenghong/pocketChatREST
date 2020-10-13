package com.pocketchat.services.unread_message;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.unread_message.UnreadMessage;
import com.pocketchat.db.repo_services.unread_message.UnreadMessageRepoService;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.request.unread_message.UpdateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.server.exceptions.unread_message.UnreadMessageNotFoundException;
import com.pocketchat.services.conversation_group.ConversationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnreadMessageServiceImpl implements UnreadMessageService {

    private final UnreadMessageRepoService unreadMessageRepoService;

    private final ConversationGroupService conversationGroupService;

    @Autowired
    public UnreadMessageServiceImpl(UnreadMessageRepoService unreadMessageRepoService,
                                    ConversationGroupService conversationGroupService) {
        this.unreadMessageRepoService = unreadMessageRepoService;
        this.conversationGroupService = conversationGroupService;
    }

    @Override
    @Transactional
    public UnreadMessage addUnreadMessage(CreateUnreadMessageRequest createUnreadMessageRequest) {
        UnreadMessage unreadMessage = createUnreadMessageRequestToUnreadMessageMapper(createUnreadMessageRequest);

        // Check ConversationGroup's existence.
        conversationGroupService.getSingleConversation(unreadMessage.getConversationId());

        // Check any UnreadMessage with same ConversationGroup ID.
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findByConversationGroupId(unreadMessage.getConversationId());

        return unreadMessageOptional.orElseGet(() -> unreadMessageRepoService.save(unreadMessage));
    }

    @Override
    @Transactional
    public UnreadMessage editUnreadMessage(UpdateUnreadMessageRequest updateUnreadMessageRequest) {
        // Check ConversationGroup's existence.
        conversationGroupService.getSingleConversation(updateUnreadMessageRequest.getConversationId());

        return unreadMessageRepoService.save(updateUnreadMessageRequestToUnreadMessageMapper(updateUnreadMessageRequest));
    }

    @Override
    @Transactional
    public void deleteUnreadMessage(String unreadMessageId) {
        unreadMessageRepoService.delete(getSingleUnreadMessage(unreadMessageId));
    }

    @Override
    public UnreadMessage getSingleUnreadMessage(String unreadMessageId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findById(unreadMessageId);
        if (unreadMessageOptional.isEmpty()) {
            throw new UnreadMessageNotFoundException("Unread Message not found, id:-" + unreadMessageId);
        }
        return unreadMessageOptional.get();
    }

    @Override
    public UnreadMessage geUnreadMessageByConversationGroupId(String conversationGroupId) {
        Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findByConversationGroupId(conversationGroupId);
        if (unreadMessageOptional.isEmpty()) {
            throw new UnreadMessageNotFoundException("Unread Message not found using conversationGroupId:-" + conversationGroupId);
        }
        return unreadMessageOptional.get();
    }

    // UserContact --> ConversationGroups --> UnreadMessages
    // Reason to do this is because frontend don't have to resend requests. Just get do all of this in one go.
    @Deprecated
    @Override
    public List<UnreadMessage> getUserOwnUnreadMessages() {
        List<ConversationGroup> conversationGroupList = conversationGroupService.getUserOwnConversationGroups();
        return conversationGroupList.stream()
                .map(ConversationGroup::getId).collect(Collectors.toList())
                .stream().map(unreadMessageRepoService::findByConversationGroupId).collect(Collectors.toList())
                .stream().map(Optional::get).collect(Collectors.toList());
    }

    @Override
    public Page<UnreadMessage> getUnreadMessagesFromConversationGroupsWithPageable(Page<ConversationGroup> conversationGroups) {
        return conversationGroups.map(conversationGroup -> {
            Optional<UnreadMessage> unreadMessageOptional = unreadMessageRepoService.findByConversationGroupId(conversationGroup.getId());
            if (unreadMessageOptional.isEmpty()) {
                return null;
            }
            return unreadMessageOptional.get();
        });
    }

    public UnreadMessage createUnreadMessageRequestToUnreadMessageMapper(CreateUnreadMessageRequest createUnreadMessageRequest) {
        return UnreadMessage.builder()
                .conversationId(createUnreadMessageRequest.getConversationId())
                .count(createUnreadMessageRequest.getCount())
                .lastMessage(createUnreadMessageRequest.getLastMessage())
                .userId(createUnreadMessageRequest.getUserId())
                .build();
    }

    public UnreadMessage updateUnreadMessageRequestToUnreadMessageMapper(UpdateUnreadMessageRequest updateUnreadMessageRequest) {
        return UnreadMessage.builder()
                .id(updateUnreadMessageRequest.getId())
                .conversationId(updateUnreadMessageRequest.getConversationId())
                .count(updateUnreadMessageRequest.getCount())
                .lastMessage(updateUnreadMessageRequest.getLastMessage())
                .userId(updateUnreadMessageRequest.getUserId())
                .build();
    }

    @Override
    public UnreadMessageResponse unreadMessageResponseMapper(UnreadMessage unreadMessage) {
        return UnreadMessageResponse.builder()
                .id(unreadMessage.getId())
                .conversationId(unreadMessage.getConversationId())
                .count(unreadMessage.getCount())
                .lastMessage(unreadMessage.getLastMessage())
                .userId(unreadMessage.getUserId())
                .build();
    }

    @Override
    public Page<UnreadMessageResponse> unreadMessageResponsePageMapper(Page<UnreadMessage> unreadMessages) {
        return unreadMessages.map(this::unreadMessageResponseMapper);
    }
}
