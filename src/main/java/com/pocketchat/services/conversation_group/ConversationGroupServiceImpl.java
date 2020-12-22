package com.pocketchat.services.conversation_group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocketchat.db.models.chat_message.ChatMessage;
import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.models.conversation_group_block.ConversationGroupBlock;
import com.pocketchat.db.models.conversation_group_mute_notification.ConversationGroupMuteNotification;
import com.pocketchat.db.models.multimedia.Multimedia;
import com.pocketchat.db.models.unread_message.UnreadMessage;
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

        conversationGroup.setCreatorUserId(creatorUserContact.getId());

        boolean conversationGroupIsCreated = false;

        switch (conversationGroup.getConversationGroupType()) {
            case Group:
            case Broadcast:
                // Group/Broadcast
                conversationGroup = createConversationGroup(conversationGroup);
                conversationGroupIsCreated = true;
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
                    conversationGroupIsCreated = true;
                } else {
                    throw new InvalidPersonalConversationGroupException("Found Multiple Personal Conversation Group with" +
                            " same members, which shouldn't be happening. Please contact developer.");
                }
                break;
            default:
                throw new InvalidConversationGroupTypeException("Invalid Conversation Group Type detected.");
        }

        if (conversationGroupIsCreated) {
            UnreadMessage newUnreadMessage = createUnreadMessage(conversationGroup, creatorUserContact);

            // Send message and notification to WebSocket, notification service providers.
            sendConversationGroupToRabbitMQ(conversationGroup, WebSocketEvent.NEW_CONVERSATION_GROUP);
            sendUnreadMessageToRabbitMQ(newUnreadMessage, conversationGroup, WebSocketEvent.NEW_UNREAD_MESSAGE);

            sendChatMessageToRabbitMQ(conversationGroup, creatorUserContact, "New conversation Group has been created.", WebSocketEvent.NEW_CONVERSATION_GROUP);
            sendChatMessageToRabbitMQ(conversationGroup, creatorUserContact, "You have been added into this conversation by" + creatorUserContact.getDisplayName() + ".", WebSocketEvent.JOINED_CONVERSATION_GROUP);
        }

        return conversationGroup;
    }

    @Override
    public MultimediaResponse uploadConversationGroupGroupPhoto(String conversationGroupId, MultipartFile multipartFile) {
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup conversationGroup = getSingleConversation(conversationGroupId);
        boolean groupPhotoReplacement = false;

        if (StringUtils.hasText(conversationGroup.getGroupPhoto())) {
            multimediaService.deleteMultimedia(conversationGroup.getGroupPhoto(), moduleDirectory);
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

        sendChatMessageToRabbitMQ(conversationGroup, ownUserContact, messageContent, webSocketEvent);

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
            multimediaService.deleteMultimedia(conversationGroup.getGroupPhoto(), moduleDirectory);
        }

        String messageContent = "has deleted the group photo.";

        sendChatMessageToRabbitMQ(conversationGroup, ownUserContact, messageContent, WebSocketEvent.DELETED_GROUP_PHOTO);
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

        // UserContact must be the group member of the conversation group.
        boolean userContactIsConversationGroupMember = userContactIsConversationGroupMember(ownUserContact.getId(), existingConversationGroup);

        // UserContact must be the group admin of the conversation group.
        boolean userContactIsConversationGroupAdmin = userContactIsConversationGroupAdmin(ownUserContact.getId(), existingConversationGroup);

        if (!userContactIsConversationGroupMember || !userContactIsConversationGroupAdmin) {
            throw new ConversationGroupMemberPermissionException("Group member has no permission to remove demote a group admin, id: " + ownUserContact.getId());
        }

        checkUserContactsExist(removeConversationGroupAdminRequest.getGroupMemberIds());

        // If the group admin only has one group admin, we can know he/she is the admin and he/she wants to remove himself/herself.
        if (existingConversationGroup.getAdminMemberIds().size() == 1) {
            throw new NoConversationGroupAdminException("Unable to remove yourself. Please promote someone else to become group admin first.");
        }

        List<String> existingGroupAdminIds = existingConversationGroup.getAdminMemberIds();

        existingGroupAdminIds.removeAll(removeConversationGroupAdminRequest.getGroupMemberIds());

        // UserContact cannot remove all group admins including him/herself in the group. Him/herself must promote more members than demoting members.
        if (existingConversationGroup.getAdminMemberIds().isEmpty()) {
            throw new NoConversationGroupAdminException("No group admins left. Promote someone else first before remove yourself.");
        }

        List<String> updatedGroupAdminIds = existingGroupAdminIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setAdminMemberIds(updatedGroupAdminIds);

        String messageContent = "has been demoted to group member.";
        sendChatMessageToRabbitMQ(existingConversationGroup, ownUserContact, messageContent, WebSocketEvent.DEMOTE_GROUP_ADMIN);

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
                sendChatMessageToRabbitMQ(existingConversationGroup, ownUserContact, messageContent, WebSocketEvent.PROMOTE_GROUP_ADMIN);
            }
        }

        existingConversationGroup.setMemberIds(existingConversationGroupMembers);
        existingConversationGroup.setAdminMemberIds(existingConversationGroupAdmins);
        String messageContent = "has left the conversation group.";
        sendChatMessageToRabbitMQ(existingConversationGroup, ownUserContact, messageContent, WebSocketEvent.LEFT_CONVERSATION_GROUP);

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
                .creatorUserId(conversationGroup.getCreatorUserId())
                .description(conversationGroup.getDescription())
                .memberIds(conversationGroup.getMemberIds())
                .name(conversationGroup.getName())
                .conversationGroupType(conversationGroup.getConversationGroupType())
                .groupPhoto(conversationGroup.getGroupPhoto())
                .createdBy(conversationGroup.getCreatedBy())
                .createdDate(conversationGroup.getCreatedDate())
                .lastModifiedBy(conversationGroup.getLastModifiedBy())
                .lastModifiedDate(conversationGroup.getLastModifiedDate())
                .version(conversationGroup.getVersion())
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
    private UnreadMessage createUnreadMessage(ConversationGroup conversationGroup, UserContact creatorUserContact) {
        CreateUnreadMessageRequest createUnreadMessageRequest = CreateUnreadMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .count(0)
                .date(LocalDateTime.now())
                .lastMessage(creatorUserContact.getDisplayName() + " has created this group.")
                .userId(creatorUserContact.getUserId())
                .build();

        return unreadMessageService.addUnreadMessage(createUnreadMessageRequest);
    }

    /**
     * Send Chat Message to the conversationGroup, along with it's members.
     *
     * @param conversationGroup: The ConversationGroup object.
     * @param message:           Message content.
     * @param webSocketEvent:    WebSocketEvent enum object to indicate what event of the message is belonged to, to allow the frontend client to analyse the message and determine actions.
     */
    private void sendChatMessageToRabbitMQ(ConversationGroup conversationGroup, UserContact responsibleUserContact, String message, WebSocketEvent webSocketEvent) {
        CreateChatMessageRequest createChatMessageRequest = CreateChatMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .messageContent(message)
                .build();

        ChatMessage chatMessage = chatMessageService.addChatMessage(createChatMessageRequest);

        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .webSocketEvent(webSocketEvent)
                .conversationGroup(conversationGroup)
                .chatMessage(chatMessage)
                .userContact(responsibleUserContact)
                .build();

        sendMessageToRabbitMQ(webSocketMessage, conversationGroup.getId(), conversationGroup.getMemberIds());
    }

    /**
     * Send ConversationGroup object to the conversationGroup, along with it's members.
     *
     * @param conversationGroup: The ConversationGroup object.
     * @param webSocketEvent:    WebSocketEvent enum object to indicate what event of the message is belonged to, to allow the frontend client to analyse the message and determine actions.
     */
    private void sendConversationGroupToRabbitMQ(ConversationGroup conversationGroup, WebSocketEvent webSocketEvent) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .webSocketEvent(webSocketEvent)
                .conversationGroup(conversationGroup)
                .build();

        sendMessageToRabbitMQ(webSocketMessage, conversationGroup.getId(), conversationGroup.getMemberIds());
    }

    /**
     * Send UnreadMessage object to the conversationGroup, along with it's members.
     *
     * @param unreadMessage:     The UnreadMessage object.
     * @param conversationGroup: The ConversationGroup object.
     * @param webSocketEvent:    WebSocketEvent enum object to indicate what event of the message is belonged to, to allow the frontend client to analyse the message and determine actions.
     */
    private void sendUnreadMessageToRabbitMQ(UnreadMessage unreadMessage, ConversationGroup conversationGroup, WebSocketEvent webSocketEvent) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .webSocketEvent(webSocketEvent)
                .unreadMessage(unreadMessage)
                .build();

        sendMessageToRabbitMQ(webSocketMessage, conversationGroup.getId(), conversationGroup.getMemberIds());
    }

    private void sendMessageToRabbitMQ(WebSocketMessage webSocketMessage, String conversationGroupId, List<String> userIds) {
        String webSocketMessageString = convertWebSocketMessageToString(webSocketMessage);

        userIds.forEach(userId ->
                rabbitMQService.addMessageToQueue(userId, conversationGroupId,
                        conversationGroupId, webSocketMessageString));
    }

    private String convertWebSocketMessageToString(WebSocketMessage webSocketMessage) {
        try {
            return objectMapper.writeValueAsString(webSocketMessage);
        } catch (JsonProcessingException e) {
            throw new WebSocketObjectConversionFailedException("Failed to convert WebSocket message to JSON string. Message: "
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
