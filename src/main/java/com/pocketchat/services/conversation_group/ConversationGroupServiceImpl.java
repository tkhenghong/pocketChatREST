package com.pocketchat.services.conversation_group;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.enums.chat_message.ChatMessageType;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupAdminNotInMemberIdListException;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.services.chat_message.ChatMessageService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.user_contact.UserContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationGroupServiceImpl implements ConversationGroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversationGroupRepoService conversationGroupRepoService;

    private final ChatMessageService chatMessageService;

    private final UserContactService userContactService;

    private final RabbitMQService rabbitMQService;

    // Avoid Field Injection
    @Autowired
    public ConversationGroupServiceImpl(ConversationGroupRepoService conversationGroupRepoService,
                                        ChatMessageService chatMessageService,
                                        UserContactService userContactService,
                                        RabbitMQService rabbitMQService) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.chatMessageService = chatMessageService;
        this.userContactService = userContactService;
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    @Transactional
    public ConversationGroup addConversation(CreateConversationGroupRequest createConversationGroupRequest) {
        ConversationGroup conversationGroup = createConversationGroupRequestToConversationGroupMapper(createConversationGroupRequest);

        UserContact creatorUserContact = userContactService.getOwnUserContact();

        String message = "You have been added into this conversation by" + creatorUserContact.getDisplayName() + " on " + conversationGroup.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        switch (conversationGroup.getConversationGroupType()) {
            case Group:
            case Broadcast:
                // Group/Broadcast
                return createAndSendMessage(conversationGroup, message);
            case Single:
                // 1. Find a list of conversationGroup that has same memberIds
                List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllByMemberIds(conversationGroup.getMemberIds());
                // 2. Filter to get the Personal ConversationGroup
                List<ConversationGroup> personalConversationGroupList = conversationGroupList
                        .stream().filter((ConversationGroup conversationGroup1) -> conversationGroup1.getConversationGroupType().equals(ConversationGroupType.Single)
                                && conversationGroup1.getAdminMemberIds().equals(conversationGroup.getAdminMemberIds())).collect(Collectors.toList());
                // 3. Should found the exact group
                if (personalConversationGroupList.size() == 1) {
                    return personalConversationGroupList.iterator().next();
                } else if (personalConversationGroupList.isEmpty()) { // Must be 0 conversationGroup
                    return createAndSendMessage(conversationGroup, message);
                } else {
                    return null;
                }
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public ConversationGroup editConversation(UpdateConversationGroupRequest updateConversationGroupRequest) {
        ConversationGroup conversationGroup = updateConversationGroupRequestToConversationGroupMapper(updateConversationGroupRequest);
        getSingleConversation(conversationGroup.getId());
        return conversationGroupRepoService.save(conversationGroup);
    }

    @Override
    @Transactional
    public void deleteConversation(String conversationGroupId) {
        conversationGroupRepoService.delete(getSingleConversation(conversationGroupId));
    }

    @Override
    public ConversationGroup getSingleConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (conversationGroupOptional.isEmpty()) {
            throw new ConversationGroupNotFoundException("Unable to find the conversation group using conversationGroupId: " + conversationGroupId);
        }
        return conversationGroupOptional.get();
    }

    @Override
    public List<ConversationGroup> getUserOwnConversationGroups() {
        UserContact userContact = userContactService.getOwnUserContact();
        return conversationGroupRepoService.findAllByMemberIds(userContact.getId());
    }

    // Not throwing exception if no conversation groups were found
    @Override
    public List<ConversationGroup> findAllByMemberIds(String userContactId) {
        return conversationGroupRepoService.findAllByMemberIds(userContactId);
    }

    @Override
    public ConversationGroup createConversationGroupRequestToConversationGroupMapper(CreateConversationGroupRequest createConversationGroupRequest) {

        return ConversationGroup.builder()
                .conversationGroupType(createConversationGroupRequest.getConversationGroupType())
                .notificationExpireDate(null)
                .name(createConversationGroupRequest.getName())
                .memberIds(createConversationGroupRequest.getMemberIds())
                .adminMemberIds(createConversationGroupRequest.getAdminMemberIds())
                .description(createConversationGroupRequest.getDescription())
                .createdDate(LocalDateTime.now())
                .creatorUserId(null)
                .block(false)
                .build();
    }

    @Override
    public ConversationGroup updateConversationGroupRequestToConversationGroupMapper(UpdateConversationGroupRequest updateConversationGroupRequest) {
        return ConversationGroup.builder()
                .id(updateConversationGroupRequest.getId())
                .conversationGroupType(ConversationGroupType.valueOf(updateConversationGroupRequest.getType()))
                .notificationExpireDate(updateConversationGroupRequest.getNotificationExpireDate())
                .name(updateConversationGroupRequest.getName())
                .creatorUserId(updateConversationGroupRequest.getCreatorUserId())
                .memberIds(updateConversationGroupRequest.getMemberIds())
                .description(updateConversationGroupRequest.getDescription())
                .createdDate(updateConversationGroupRequest.getCreatedDate())
                .block(updateConversationGroupRequest.isBlock())
                .adminMemberIds(updateConversationGroupRequest.getAdminMemberIds())
                .build();
    }

    @Override
    public ConversationGroupResponse conversationGroupResponseMapper(ConversationGroup conversationGroup) {
        return ConversationGroupResponse.builder()
                .id(conversationGroup.getId())
                .adminMemberIds(conversationGroup.getAdminMemberIds())
                .block(conversationGroup.isBlock())
                .createdDate(conversationGroup.getCreatedDate())
                .creatorUserId(conversationGroup.getCreatorUserId())
                .description(conversationGroup.getDescription())
                .memberIds(conversationGroup.getMemberIds())
                .name(conversationGroup.getName())
                .notificationExpireDate(conversationGroup.getNotificationExpireDate())
                .conversationGroupType(conversationGroup.getConversationGroupType())
                .build();
    }

    private void checkUserContactsExist(List<String> userContactIds) {
        userContactIds.forEach(userContactService::getUserContact);
    }

    ConversationGroup createAndSendMessage(ConversationGroup conversationGroup, String message) {
        checkUserContactsExist(conversationGroup.getMemberIds());
        checkUserContactsExist(conversationGroup.getAdminMemberIds());

        boolean memberIdsHasAllAdminIds = conversationGroup.getMemberIds().containsAll(conversationGroup.getAdminMemberIds());

        if (!memberIdsHasAllAdminIds) {
            throw new ConversationGroupAdminNotInMemberIdListException("Error while creating a conversation group, admin ID is not included in memberId list!");
        }

        ConversationGroup newConversationGroup = conversationGroupRepoService.save(conversationGroup);
        sendWelcomeMessage(newConversationGroup, message);

        return newConversationGroup;
    }

    private void sendWelcomeMessage(ConversationGroup conversationGroup, String message) {
        CreateChatMessageRequest createChatMessageRequest = CreateChatMessageRequest.builder()
                .chatMessageType(ChatMessageType.Text)
                .conversationId(conversationGroup.getId())
                .messageContent(message)
                .multimediaId(null)
                .build();

        ChatMessage chatMessage = chatMessageService.addChatMessage(createChatMessageRequest);

        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .chatMessage(chatMessage)
                .build();

        conversationGroup.getMemberIds().forEach(memberId ->
                this.rabbitMQService.addMessageToQueue(memberId, conversationGroup.getId(),
                        conversationGroup.getId(), webSocketMessage.toString()));
    }
}
