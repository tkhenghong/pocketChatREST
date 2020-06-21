package com.pocketchat.services.conversation_group;

import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.db.repo_services.user_contact.UserContactRepoService;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.enums.chat_message.ChatMessageStatus;
import com.pocketchat.models.enums.chat_message.ChatMessageType;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.server.configurations.websocket.WebSocketMessage;
import com.pocketchat.server.exceptions.conversation_group.ConversationGroupNotFoundException;
import com.pocketchat.server.exceptions.user_contact.UserContactNotFoundException;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationGroupServiceImpl implements ConversationGroupService {

    private final ConversationGroupRepoService conversationGroupRepoService;

    private final UserContactRepoService userContactRepoService;

    private final RabbitMQService rabbitMQService;

    // Avoid Field Injection
    @Autowired
    public ConversationGroupServiceImpl(ConversationGroupRepoService conversationGroupRepoService,
                                        UserContactRepoService userContactRepoService,
                                        RabbitMQService rabbitMQService) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.userContactRepoService = userContactRepoService;
        this.rabbitMQService = rabbitMQService;
    }

    @Override
    public ConversationGroupResponse addConversation(CreateConversationGroupRequest createConversationGroupRequest) {
        Optional<UserContact> userContactOptional = this.userContactRepoService.findById(createConversationGroupRequest.getCreatorUserId());

        if (userContactOptional.isEmpty()) {
            throw new UserContactNotFoundException("Not able to find the userContact during creation of conversation group. userContactId: " + createConversationGroupRequest.getCreatorUserId());
        }

        UserContact creatorUserContact = userContactOptional.get();

        createConversationGroupRequest.setCreatedDate(new DateTime());

        ConversationGroup conversationGroup = createConversationGroupRequestToConversationGroupMapper(createConversationGroupRequest);
        if (conversationGroup.getConversationGroupType().equals(ConversationGroupType.Single)) {
            // 1. Find a list of conversationGroup that has same memberIds
            List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllByMemberIds(conversationGroup.getMemberIds());
            // 2. Filter to get the Personal ConversationGroup
            List<ConversationGroup> personalConversationGroupList = conversationGroupList
                    .stream().filter((ConversationGroup conversationGroup1) -> conversationGroup1.getConversationGroupType().equals(ConversationGroupType.Single)
                            && conversationGroup1.getAdminMemberIds().equals(conversationGroup.getAdminMemberIds())).collect(Collectors.toList());
            // 3. Should found the exact group
            if (personalConversationGroupList.size() == 1) {
                return conversationGroupResponseMapper(personalConversationGroupList.iterator().next());
            } else if (personalConversationGroupList.isEmpty()) { // Must be 0 conversationGroup
                return conversationGroupResponseMapper(conversationGroupRepoService.save(conversationGroup));
            } else {
                return null;
            }
        } else {
            // Group/Broadcast
            ConversationGroup conversationGroup1 = conversationGroupRepoService.save(conversationGroup);

            String message = "You have been added into this group by" + creatorUserContact.getDisplayName() + " on " + createConversationGroupRequest.getCreatedDate().toString("dd/mm/yyyy HH:mm:ss");

            ChatMessage chatMessage =
                    ChatMessage.builder()
                            .type(ChatMessageType.Text)
                            .conversationId(conversationGroup1.getId())
                            .createdTime(new DateTime())
                            .messageContent(message)
                            .status(ChatMessageStatus.Sent)
                            .sentTime(new DateTime())
                            .build();

            WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                    .chatMessage(chatMessage)
                    .build();

            // TODO: Create queues based on userContactIds, exchange based on conversationGroupId and Binding between 2 of them using conversationGroupID(for now)
            conversationGroup1.getMemberIds().forEach(memberId -> {
                this.rabbitMQService.addMessageToQueue(memberId, conversationGroup1.getId(), conversationGroup1.getId(), webSocketMessage.toString());
            });

            // TODO: Send first message: You have been added to this group, system message.
            return conversationGroupResponseMapper(conversationGroup1);
        }
    }

    @Override
    public ConversationGroupResponse editConversation(UpdateConversationGroupRequest updateConversationGroupRequest) {
        ConversationGroup conversationGroup = updateConversationGroupRequestToConversationGroupMapper(updateConversationGroupRequest);
        getSingleConversation(conversationGroup.getId());
        return conversationGroupResponseMapper(conversationGroupRepoService.save(conversationGroup));
    }

    @Override
    public void deleteConversation(String conversationGroupId) {
        conversationGroupRepoService.delete(getSingleConversation(conversationGroupId));
    }

    @Override
    public ConversationGroup getSingleConversation(String conversationGroupId) {
        Optional<ConversationGroup> conversationGroupOptional = conversationGroupRepoService.findById(conversationGroupId);
        if (!conversationGroupOptional.isPresent()) {
            throw new ConversationGroupNotFoundException("conversationGroupId-" + conversationGroupId);
        }
        return conversationGroupOptional.get();
    }

    @Override
    public List<ConversationGroupResponse> getConversationsForUserByMobileNo(String mobileNo) {
        // Retrieve conversations for the user
        UserContact userContact = userContactRepoService.findByMobileNo(mobileNo);

        if (ObjectUtils.isEmpty(userContact)) {
            throw new UserContactNotFoundException("UserContact not found: " + mobileNo);
        }
        List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllByMemberIds(userContact.getId());
        return conversationGroupList.stream().map(this::conversationGroupResponseMapper).collect(Collectors.toList());
    }

    @Override
    public List<ConversationGroup> findAllByMemberIds(String userContactId) {
        List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllByMemberIds(userContactId);
        if (conversationGroupList.isEmpty()) {
            throw new UserContactNotFoundException("UserContactId not found: " + userContactId);
        }
        return conversationGroupList;
    }

    @Override
    public ConversationGroup createConversationGroupRequestToConversationGroupMapper(CreateConversationGroupRequest createConversationGroupRequest) {
        return ConversationGroup.builder()
                .conversationGroupType(createConversationGroupRequest.getConversationGroupType())
                .notificationExpireDate(createConversationGroupRequest.getNotificationExpireDate())
                .name(createConversationGroupRequest.getName())
                .creatorUserId(createConversationGroupRequest.getCreatorUserId())
                .memberIds(createConversationGroupRequest.getMemberIds())
                .description(createConversationGroupRequest.getDescription())
                .createdDate(createConversationGroupRequest.getCreatedDate())
                .block(createConversationGroupRequest.isBlock())
                .adminMemberIds(createConversationGroupRequest.getAdminMemberIds())
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
                .createdDate(conversationGroup.getCreatedDate().getMillis())
                .creatorUserId(conversationGroup.getCreatorUserId())
                .description(conversationGroup.getDescription())
                .memberIds(conversationGroup.getMemberIds())
                .name(conversationGroup.getName())
                .notificationExpireDate(conversationGroup.getNotificationExpireDate().getMillis())
                .conversationGroupType(conversationGroup.getConversationGroupType())
                .build();
    }
}
