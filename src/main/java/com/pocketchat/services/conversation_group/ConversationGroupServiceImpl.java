package com.pocketchat.services.conversation_group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.conversation_group_block.ConversationGroupBlock;
import com.pocketchat.db.models.conversation_group_mute_notification.ConversationGroupMuteNotification;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.user_contact.UserContact;
import com.pocketchat.db.repo_services.conversation_group.ConversationGroupRepoService;
import com.pocketchat.models.controllers.request.chat_message.CreateChatMessageRequest;
import com.pocketchat.models.controllers.request.conversation_group.*;
import com.pocketchat.models.controllers.request.unread_message.CreateUnreadMessageRequest;
import com.pocketchat.models.controllers.response.conversation_group.ConversationGroupResponse;
import com.pocketchat.models.controllers.response.conversation_group.ConversationPageableResponse;
import com.pocketchat.models.controllers.response.conversation_group_block.ConversationGroupBlockResponse;
import com.pocketchat.models.controllers.response.conversation_group_mute_notification.ConversationGroupMuteNotificationResponse;
import com.pocketchat.models.controllers.response.multimedia.MultimediaResponse;
import com.pocketchat.models.controllers.response.unread_message.UnreadMessageResponse;
import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.models.enums.websocket.WebSocketEvent;
import com.pocketchat.models.websocket.WebSocketMessage;
import com.pocketchat.server.exceptions.conversation_group.*;
import com.pocketchat.server.exceptions.conversation_group_block.BlockConversationGroupException;
import com.pocketchat.server.exceptions.conversation_group_block.UnblockConversationGroupException;
import com.pocketchat.server.exceptions.conversation_group_mute_notification.UnmuteConversationGroupException;
import com.pocketchat.server.exceptions.file.UploadFileException;
import com.pocketchat.services.chat_message.ChatMessageService;
import com.pocketchat.services.conversation_group_block.ConversationGroupBlockService;
import com.pocketchat.services.conversation_group_mute_notification.ConversationGroupMuteNotificationService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationGroupServiceImpl implements ConversationGroupService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversationGroupRepoService conversationGroupRepoService;

    private final ConversationGroupBlockService conversationGroupBlockService;

    private final ConversationGroupMuteNotificationService conversationGroupMuteNotificationService;

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
                                        ConversationGroupBlockService conversationGroupBlockService,
                                        ConversationGroupMuteNotificationService conversationGroupMuteNotificationService,
                                        @Lazy ChatMessageService chatMessageService,
                                        @Lazy UserContactService userContactService,
                                        @Lazy UnreadMessageService unreadMessageService,
                                        @Lazy MultimediaService multimediaService,
                                        RabbitMQService rabbitMQService,
                                        FileUtil fileUtil,
                                        ObjectMapper objectMapper) {
        this.conversationGroupRepoService = conversationGroupRepoService;
        this.conversationGroupBlockService = conversationGroupBlockService;
        this.conversationGroupMuteNotificationService = conversationGroupMuteNotificationService;
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
        sendMessage(conversationGroup, creatorUserContact, WebSocketEvent.ADDED_TO_THE_CONVERSATION_GROUP, message);

        return conversationGroup;
    }

    @Override
    public MultimediaResponse uploadConversationGroupGroupPhoto(String conversationGroupId, MultipartFile multipartFile) {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup conversationGroup = getSingleConversation(conversationGroupId);
        boolean groupPhotoReplacement = false;

        if (StringUtils.hasText(conversationGroup.getGroupPhoto())) {
            multimediaService.deleteMultimedia(conversationGroup.getGroupPhoto());
            groupPhotoReplacement = true;
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

        String messageContent = "has uploaded the group photo.";
        WebSocketEvent webSocketEvent = WebSocketEvent.UPLOADED_GROUP_PHOTO;
        if (groupPhotoReplacement) {
            messageContent = "has replaced the group photo.";
            webSocketEvent = WebSocketEvent.CHANGED_GROUP_PHOTO;
        }

        sendMessage(conversationGroup, ownUserContact, webSocketEvent, messageContent);

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
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup conversationGroup = getSingleConversation(conversationGroupId);

        if (StringUtils.hasText(conversationGroup.getGroupPhoto())) {
            multimediaService.deleteMultimedia(conversationGroup.getGroupPhoto());
        }

        String messageContent = "has deleted the group photo.";

        sendMessage(conversationGroup, ownUserContact, WebSocketEvent.DELETED_GROUP_PHOTO, messageContent);
    }

    @Override
    @Transactional
    public ConversationGroup editConversationGroup(UpdateConversationGroupRequest updateConversationGroupRequest) {
        ConversationGroup updatedConversationGroup = null;

        if (StringUtils.hasText(updateConversationGroupRequest.getName())) {
            updatedConversationGroup = conversationGroupRepoService.updateConversationGroupName(updateConversationGroupRequest.getId(), updateConversationGroupRequest.getName());
        }

        if (StringUtils.hasText(updateConversationGroupRequest.getDescription())) {
            updatedConversationGroup = conversationGroupRepoService.updateConversationGroupDescription(updateConversationGroupRequest.getId(), updateConversationGroupRequest.getDescription());
        }

        return updatedConversationGroup;
    }

    @Override
    public ConversationGroup addConversationGroupMember(String conversationGroupId, AddConversationGroupMemberRequest addConversationGroupMemberRequest) {
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);
        checkUserContactsExist(addConversationGroupMemberRequest.getGroupMemberIds());
        List<String> existingGroupMemberIds = existingConversationGroup.getMemberIds();

        existingGroupMemberIds.addAll(addConversationGroupMemberRequest.getGroupMemberIds());

        List<String> updatedGroupMemberIds = existingGroupMemberIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setMemberIds(updatedGroupMemberIds);

        return conversationGroupRepoService.save(existingConversationGroup);
    }

    @Override
    public ConversationGroup removeConversationGroupMember(String conversationGroupId, RemoveConversationGroupMemberRequest removeConversationGroupMemberRequest) {
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);
        checkUserContactsExist(removeConversationGroupMemberRequest.getGroupMemberIds());
        List<String> existingGroupMemberIds = existingConversationGroup.getMemberIds();

        existingGroupMemberIds.removeAll(removeConversationGroupMemberRequest.getGroupMemberIds());

        List<String> updatedGroupMemberIds = existingGroupMemberIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setMemberIds(updatedGroupMemberIds);

        return conversationGroupRepoService.save(existingConversationGroup);
    }

    @Override
    public ConversationGroup addConversationGroupAdmin(String conversationGroupId, AddConversationGroupAdminRequest addConversationGroupAdminRequest) {
        UserContact ownUserContact = userContactService.getOwnUserContact();

        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        boolean userContactIsConversationGroupMember = userContactIsConversationGroupMember(ownUserContact.getId(), existingConversationGroup);
        boolean userContactIsConversationGroupAdmin = userContactIsConversationGroupAdmin(ownUserContact.getId(), existingConversationGroup);

        if (!userContactIsConversationGroupMember || !userContactIsConversationGroupAdmin) {
            throw new ConversationGroupMemberPermissionException("Group member has no permission to remove promote a group admin, id: " + ownUserContact.getId());
        }

        checkUserContactsExist(addConversationGroupAdminRequest.getGroupMemberIds());
        List<String> existingGroupAdminIds = existingConversationGroup.getAdminMemberIds();

        existingGroupAdminIds.addAll(addConversationGroupAdminRequest.getGroupMemberIds());

        List<String> updatedGroupAdminIds = existingGroupAdminIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setAdminMemberIds(updatedGroupAdminIds);

        return conversationGroupRepoService.save(existingConversationGroup);
    }

    @Override
    public ConversationGroup removeConversationGroupAdmin(String conversationGroupId, RemoveConversationGroupAdminRequest removeConversationGroupAdminRequest) {
        UserContact ownUserContact = userContactService.getOwnUserContact();

        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        boolean userContactIsConversationGroupMember = userContactIsConversationGroupMember(ownUserContact.getId(), existingConversationGroup);
        boolean userContactIsConversationGroupAdmin = userContactIsConversationGroupAdmin(ownUserContact.getId(), existingConversationGroup);

        if (!userContactIsConversationGroupMember || !userContactIsConversationGroupAdmin) {
            throw new ConversationGroupMemberPermissionException("Group member has no permission to remove demote a group admin, id: " + ownUserContact.getId());
        }

        checkUserContactsExist(removeConversationGroupAdminRequest.getGroupMemberIds());
        List<String> existingGroupAdminIds = existingConversationGroup.getAdminMemberIds();

        existingGroupAdminIds.removeAll(removeConversationGroupAdminRequest.getGroupMemberIds());

        List<String> updatedGroupAdminIds = existingGroupAdminIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setAdminMemberIds(updatedGroupAdminIds);

        return conversationGroupRepoService.save(existingConversationGroup);
    }

    @Override
    public ConversationGroup leaveConversationGroup(String conversationGroupId) {
        UserContact ownUserContact = userContactService.getOwnUserContact();

        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        boolean userContactIsConversationGroupMember = userContactIsConversationGroupMember(ownUserContact.getId(), existingConversationGroup);
        boolean userContactIsConversationGroupAdmin = userContactIsConversationGroupAdmin(ownUserContact.getId(), existingConversationGroup);

        if (!userContactIsConversationGroupMember) {
            throw new ConversationGroupMemberPermissionException("Current user doesn't have permission to leave the specified group, userContactId: " + ownUserContact.getId() + "conversationGroupId: " + conversationGroupId);
        }

        List<String> existingConversationGroupMembers = existingConversationGroup.getMemberIds();

        existingConversationGroupMembers.remove(ownUserContact.getId());

        List<String> existingConversationGroupAdmins = existingConversationGroup.getAdminMemberIds();

        if (userContactIsConversationGroupAdmin) {
            existingConversationGroupAdmins.remove(ownUserContact.getId());
        }

        // When there's no one in the group left. (probably make a scheduler to clean up empty conversation groups every night)
        if (existingConversationGroup.getMemberIds().isEmpty()) {
            // Do nothing.
        } else {
            // When you're the only group admin.
            // Get the first member of the group to be group admin.
            if (existingConversationGroup.getAdminMemberIds().isEmpty()) {
                UserContact newGroupAdmin = userContactService.getUserContact(existingConversationGroupMembers.get(0));
                existingConversationGroupAdmins.add(newGroupAdmin.getId());
                String messageContent = "has been promoted to group admin.";
                sendMessage(existingConversationGroup, newGroupAdmin, WebSocketEvent.PROMOTE_GROUP_ADMIN, messageContent);
            }
        }

        existingConversationGroup.setMemberIds(existingConversationGroupMembers);
        existingConversationGroup.setAdminMemberIds(existingConversationGroupAdmins);
        String messageContent = "has left the conversation group.";
        sendMessage(existingConversationGroup, ownUserContact, WebSocketEvent.LEAVED_CONVERSATION_GROUP, messageContent);

        return conversationGroupRepoService.save(existingConversationGroup);
    }

    /**
     * Block a conversation group.
     *
     * @param conversationGroupId: ID of a conversation group.
     * @return ConversationGroupBlock object which is a record
     */
    @Override
    public ConversationGroupBlock blockConversationGroup(String conversationGroupId) {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        if (!existingConversationGroup.getConversationGroupType().equals(ConversationGroupType.Personal)) {
            throw new BlockConversationGroupException("Unable to block a conversation group. ID: " + conversationGroupId);
        }

        return conversationGroupBlockService.addConversationGroupBlock(CreateConversationGroupBlockRequest.builder()
                .userContactId(ownUserContact.getId())
                .conversationGroupId(existingConversationGroup.getId())
                .build());
    }

    /**
     * Unblock a conversation group.
     * The following logic has been designed in this way is to perform multiple confirmation from different services,
     * before the conversation group is truly unblocked.
     *
     * @param conversationGroupId:             ID of the conversation group.
     * @param unblockConversationGroupRequest: UnblockConversationGroupRequest object, contains ID of the ConversationGroupBlock object.
     */
    @Override
    public void unblockConversationGroup(String conversationGroupId, UnblockConversationGroupRequest unblockConversationGroupRequest) {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        ConversationGroupBlock conversationGroupBlock = conversationGroupBlockService.getSingleConversationGroupBlock(unblockConversationGroupRequest.getConversationGroupBlockId());

        if (!conversationGroupBlock.getId().equals(unblockConversationGroupRequest.getConversationGroupBlockId()) ||
                !conversationGroupBlock.getConversationGroupId().equals(existingConversationGroup.getId()) ||
                !conversationGroupBlock.getUserContactId().equals(ownUserContact.getId())) {
            throw new UnblockConversationGroupException("Unable to unblock conversation group. conversationGroupId: " + conversationGroupId + ", conversationGroupBlockId: " + unblockConversationGroupRequest.getConversationGroupBlockId());
        }

        conversationGroupBlockService.deleteConversationGroupBlock(unblockConversationGroupRequest.getConversationGroupBlockId());
    }

    @Override
    public ConversationGroupMuteNotification muteConversationGroupNotification(String conversationGroupId, MuteConversationGroupNotificationRequest muteConversationGroupNotificationRequest) {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        return conversationGroupMuteNotificationService.addConversationGroupMuteNotification(
                CreateConversationGroupMuteNotificationRequest.builder()
                        .userContactId(ownUserContact.getId())
                        .conversationGroupId(existingConversationGroup.getId())
                        .notificationBlockExpire(muteConversationGroupNotificationRequest.getBlockNotificationExpireTime())
                        .build());
    }

    @Override
    public void unmuteConversationGroupNotification(String conversationGroupId, UnmuteConversationGroupNotificationRequest unmuteConversationGroupNotificationRequest) {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        ConversationGroupMuteNotification existingConversationGroupMuteNotification =
                conversationGroupMuteNotificationService.getSingleConversationGroupMuteNotification(
                        unmuteConversationGroupNotificationRequest.getConversationGroupMuteNotificationId());

        if (!existingConversationGroupMuteNotification.getUserContactId().equals(ownUserContact.getId()) ||
                !existingConversationGroupMuteNotification.getConversationGroupId().equals(existingConversationGroup.getId()) ||
                !existingConversationGroupMuteNotification.getId().equals(unmuteConversationGroupNotificationRequest.getConversationGroupMuteNotificationId())) {
            throw new UnmuteConversationGroupException("Unable to unmute conversation group ID: " + conversationGroupId);
        }

        conversationGroupMuteNotificationService.deleteConversationGroupMuteNotification(existingConversationGroupMuteNotification.getId());
    }

//    @Override
//    public InviteConversationGroupToken requestShareConversationGroup(String conversationGroupId) {
//
//    }

    @Override
    public ConversationGroup joinConversationGroup(JoinConversationGroupRequest joinConversationGroupRequest) {
        // Analyse the joinConversationGroupRequest content to verify
        // Possibility of using token service to allow normal group member to add other people to the conversation group, without group admin.
        // Possibility of using QR code technology in the front end to share the verification code across people.
        return null;
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
                .name(createConversationGroupRequest.getName())
                .memberIds(createConversationGroupRequest.getMemberIds())
                .adminMemberIds(createConversationGroupRequest.getAdminMemberIds())
                .description(createConversationGroupRequest.getDescription())
                .creatorUserId(null)
                .build();
    }

    @Override
    public ConversationGroup updateConversationGroupRequestToConversationGroupMapper(UpdateConversationGroupRequest updateConversationGroupRequest) {
        return ConversationGroup.builder()
                .id(updateConversationGroupRequest.getId())
                .name(updateConversationGroupRequest.getName())
                .description(updateConversationGroupRequest.getDescription())
                .build();
    }

    @Override
    public ConversationGroupResponse conversationGroupResponseMapper(ConversationGroup conversationGroup) {
        return ConversationGroupResponse.builder()
                .id(conversationGroup.getId())
                .adminMemberIds(conversationGroup.getAdminMemberIds())
                .createdDate(conversationGroup.getCreatedDate())
                .creatorUserId(conversationGroup.getCreatorUserId())
                .description(conversationGroup.getDescription())
                .memberIds(conversationGroup.getMemberIds())
                .name(conversationGroup.getName())
                .conversationGroupType(conversationGroup.getConversationGroupType())
                .groupPhoto(conversationGroup.getGroupPhoto())
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
     * @param userContact:       The group member who has done the action.
     * @param message:           Welcome Message for the conversation group.
     */
    private void sendMessage(ConversationGroup conversationGroup, UserContact userContact, WebSocketEvent webSocketEvent, String message) {
        CreateChatMessageRequest createChatMessageRequest = CreateChatMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .messageContent(message)
                .build();

        ChatMessage chatMessage = chatMessageService.addChatMessage(createChatMessageRequest);

        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .webSocketEvent(webSocketEvent)
                .userContact(userContact)
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

    private boolean userContactIsConversationGroupMember(String userContactId, ConversationGroup conversationGroup) {
        return conversationGroup.getMemberIds().contains(userContactId);
    }

    private boolean userContactIsConversationGroupAdmin(String userContactId, ConversationGroup conversationGroup) {
        return conversationGroup.getAdminMemberIds().contains(userContactId);
    }

    @Override
    public ConversationPageableResponse conversationPageableResponseMapper(Page<ConversationGroupResponse> conversationGroupResponses,
                                                                           Page<UnreadMessageResponse> unreadMessageResponses) {
        return ConversationPageableResponse.builder().conversationGroupResponses(conversationGroupResponses).unreadMessageResponses(unreadMessageResponses).build();
    }

    @Override
    public ConversationGroupBlockResponse conversationGroupBlockResponseMapper(ConversationGroupBlock conversationGroupBlock) {
        return conversationGroupBlockService.conversationGroupBlockResponseMapper(conversationGroupBlock);
    }

    @Override
    public ConversationGroupMuteNotificationResponse conversationGroupMuteNotificationResponseMapper(ConversationGroupMuteNotification conversationGroupMuteNotification) {
        return conversationGroupMuteNotificationService.conversationGroupMuteNotificationResponseMapper(conversationGroupMuteNotification);
    }
}
