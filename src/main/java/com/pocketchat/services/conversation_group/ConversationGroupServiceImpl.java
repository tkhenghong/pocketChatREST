package com.pocketchat.services.conversation_group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.conversation_group.CreateConversationGroupRequest;
import com.pocketchat.models.controllers.request.conversation_group.UpdateConversationGroupRequest;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.controllers.response.conversation_group.ConversationPageableResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.exceptions.conversation_group.*;
import com.pocketchat.server.exceptions.file.UploadFileException;
import com.pocketchat.services.chat_message.ChatMessageService;
import com.pocketchat.services.multimedia.MultimediaService;
import com.pocketchat.services.rabbitmq.RabbitMQService;
import com.pocketchat.services.unread_message.UnreadMessageService;
import com.pocketchat.services.user_contact.UserContactService;
import com.pocketchat.utils.file.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private final UnreadMessageService unreadMessageService;

    private final MultimediaService multimediaService;

    private final RabbitMQService rabbitMQService;

    private final FileUtil fileUtil;

    private final ObjectMapper objectMapper;

    private static final String moduleDirectory = "conversationGroup";

    // Avoid Field Injection
    @Autowired
    public ConversationGroupServiceImpl(ConversationGroupRepoService conversationGroupRepoService,
                                        @Lazy ChatMessageService chatMessageService,
                                        @Lazy UserContactService userContactService,
                                        @Lazy UnreadMessageService unreadMessageService,
                                        @Lazy MultimediaService multimediaService,
                                        RabbitMQService rabbitMQService,
                                        FileUtil fileUtil,
                                        ObjectMapper objectMapper) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.chatMessageService = chatMessageService;
        this.userContactService = userContactService;
        this.unreadMessageService = unreadMessageService;
        this.multimediaService = multimediaService;
        this.rabbitMQService = rabbitMQService;
        this.fileUtil = fileUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public ConversationGroup addConversation(CreateConversationGroupRequest createConversationGroupRequest) {
        UserContact creatorUserContact = userContactService.getOwnUserContact();

        boolean creatorIsInCreateConversationGroupRequest =
                createConversationGroupRequest.getAdminMemberIds().contains(creatorUserContact.getId()) &&
                        createConversationGroupRequest.getMemberIds().contains(creatorUserContact.getId());

        if (!creatorIsInCreateConversationGroupRequest) {
            throw new CreatorIsNotConversationGroupMemberException("The creator must be in both member and admins.");
        }

        ConversationGroup conversationGroup = createConversationGroupRequestToConversationGroupMapper(createConversationGroupRequest);

        String message = "You have been added into this conversation by" + creatorUserContact.getDisplayName() + ".";

        conversationGroup.setCreatorUserId(creatorUserContact.getId());

        switch (conversationGroup.getConversationGroupType()) {
            case Group:
            case Broadcast:
                // Group/Broadcast
                conversationGroup = createConversationGroup(conversationGroup);
                break;
            case Personal:
                // 1. Find a list of conversationGroup that has same memberIds
                List<ConversationGroup> conversationGroupList = conversationGroupRepoService.findAllByMemberIds(conversationGroup.getMemberIds());
                // 2. Filter to get the Personal ConversationGroup
                ConversationGroup finalConversationGroup = conversationGroup;
                List<ConversationGroup> personalConversationGroupList = conversationGroupList
                        .stream().filter((ConversationGroup conversationGroup1) ->
                                conversationGroup1.getConversationGroupType().equals(ConversationGroupType.Personal)
                                        && conversationGroup1.getAdminMemberIds().equals(finalConversationGroup.getAdminMemberIds()))
                        .collect(Collectors.toList());
                // 3. Should found the exact group
                if (personalConversationGroupList.size() == 1) {
                    conversationGroup = personalConversationGroupList.iterator().next();
                } else if (personalConversationGroupList.isEmpty()) { // Must be 0 conversationGroup
                    conversationGroup = createConversationGroup(conversationGroup);
                } else {
                    throw new InvalidPersonalConversationGroupException("Found Multiple Personal Conversation Group with" +
                            " same members, which shouldn't be happening. Please contact developer.");
                }
                break;
            default:
                throw new InvalidConversationGroupTypeException("Invalid Conversation Group Type detected.");
        }

        createUnreadMessage(conversationGroup, creatorUserContact);
        sendWelcomeMessage(conversationGroup, message);

        return conversationGroup;
    }

    @Override
    public MultimediaResponse uploadConversationGroupProfilePhoto(String conversationGroupId, MultipartFile multipartFile) {
        ConversationGroup conversationGroup = getSingleConversation(conversationGroupId);

        if (StringUtils.hasText(conversationGroup.getGroupPhoto())) {
            multimediaService.deleteMultimedia(conversationGroup.getGroupPhoto());
        }

        Multimedia savedMultimedia;
        try {
            savedMultimedia = multimediaService.addMultimedia(fileUtil.createMultimedia(multipartFile, moduleDirectory));
        } catch (IOException ioException) {
            throw new UploadFileException("Unable to upload Conversation Group Profile Photo to the server! conversationGroupId: " + conversationGroupId + ", message: " + ioException.getMessage());
        }

        if (ObjectUtils.isEmpty(savedMultimedia)) {
            throw new UploadFileException("Unable to upload Conversation Group Profile Photo to the server due to savedMultimedia object is empty. conversationGroupId: " + conversationGroupId);
        }

        conversationGroup.setGroupPhoto(savedMultimedia.getId());
        conversationGroupRepoService.save(conversationGroup); // Update
        return multimediaService.multimediaResponseMapper(savedMultimedia);
    }

    @Override
    public File getConversationGroupGroupPhoto(String conversationGroupId) throws FileNotFoundException {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup conversationGroup = getSingleConversation(conversationGroupId);

        boolean isConversationGroupMember = conversationGroup.getMemberIds().contains(ownUserContact.getId());

        if (!isConversationGroupMember) {
            return null;
        }

        Multimedia multimedia = multimediaService.getSingleMultimedia(conversationGroup.getGroupPhoto());
        return fileUtil.getFileWithAbsolutePath(moduleDirectory, multimedia.getFileDirectory(), multimedia.getFileName());
    }

    @Override
    public void deleteConversationGroupProfilePhoto(String conversationGroupId) {
        ConversationGroup conversationGroup = getSingleConversation(conversationGroupId);

        if (StringUtils.hasText(conversationGroup.getGroupPhoto())) {
            multimediaService.deleteMultimedia(conversationGroup.getGroupPhoto());
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

    @Override
    public Page<ConversationGroup> getUserOwnConversationGroups(Pageable pageable) {
        UserContact userContact = userContactService.getOwnUserContact();

        return conversationGroupRepoService.findAllByMemberIds(userContact.getId(), pageable);
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

    @Override
    public Page<ConversationGroupResponse> conversationGroupResponsePageMapper(Page<ConversationGroup> conversationGroups) {
        return conversationGroups.map(this::conversationGroupResponseMapper);
    }

    /**
     * Check all user contacts are valid or not.
     * If user contact ID doesn't exist, the UserContactService will throw exception there.
     *
     * @param userContactIds: List of String with UserObject ID.
     */
    private void checkUserContactsExist(List<String> userContactIds) {
        userContactIds.forEach(userContactService::getUserContact);
    }

    /**
     * Save the ConversationGroup object(Creation). Before that check normal member and admin member IDs are logical or not.
     *
     * @param conversationGroup: To be saved ConversationGroup object.
     * @return Saved Conversation Group object.
     */
    private ConversationGroup createConversationGroup(ConversationGroup conversationGroup) {
        checkUserContactsExist(conversationGroup.getMemberIds());
        checkUserContactsExist(conversationGroup.getAdminMemberIds());

        boolean memberIdsHasAllAdminIds = conversationGroup.getMemberIds().containsAll(conversationGroup.getAdminMemberIds());

        if (!memberIdsHasAllAdminIds) {
            throw new ConversationGroupAdminNotInMemberIdListException("Error while creating a conversation group, admin ID is not included in memberId list!");
        }

        return conversationGroupRepoService.save(conversationGroup);
    }

    /**
     * Create UnreadMessage object after created Conversation Group object.
     *
     * @param conversationGroup:  Saved conversationGroup object.
     * @param creatorUserContact: UserContact object of the creator.
     */
    private void createUnreadMessage(ConversationGroup conversationGroup, UserContact creatorUserContact) {
        CreateUnreadMessageRequest createUnreadMessageRequest = CreateUnreadMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .count(0)
                .date(LocalDateTime.now())
                .lastMessage(creatorUserContact.getDisplayName() + " has created this group.")
                .userId(creatorUserContact.getUserId())
                .build();

        unreadMessageService.addUnreadMessage(createUnreadMessageRequest);
    }

    /**
     * Send a ChatMessage object as welcome message, and ConversationGroup object into a WebSocketMessage object
     * to be sent to group members.
     *
     * @param conversationGroup: Saved ConversationGroup object.
     * @param message:           Welcome Message for the conversation group.
     */
    private void sendWelcomeMessage(ConversationGroup conversationGroup, String message) {
        CreateChatMessageRequest createChatMessageRequest = CreateChatMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .messageContent(message)
                .build();

        ChatMessage chatMessage = chatMessageService.addChatMessage(createChatMessageRequest);

        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .conversationGroup(conversationGroup)
                .chatMessage(chatMessage)
                .build();

        try {
            String webSocketMessageString = objectMapper.writeValueAsString(webSocketMessage);

            conversationGroup.getMemberIds().forEach(memberId ->
                    rabbitMQService.addMessageToQueue(memberId, conversationGroup.getId(),
                            conversationGroup.getId(), webSocketMessageString));
        } catch (JsonProcessingException e) {
            throw new WebSocketObjectConversionFailedException("Failed to convert Welcome Chat Message. Message: "
                    + e.getMessage());
        }
    }

    @Override
    public ConversationPageableResponse conversationPageableResponseMapper(Page<ConversationGroupResponse> conversationGroupResponses,
                                                                           Page<UnreadMessageResponse> unreadMessageResponses) {
        return ConversationPageableResponse.builder().conversationGroupResponses(conversationGroupResponses).unreadMessageResponses(unreadMessageResponses).build();
    }
}
