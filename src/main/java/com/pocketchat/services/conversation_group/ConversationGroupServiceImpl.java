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
import com.pocketchat.server.configurations.websocket.WebSocketMessageSender;
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
import java.util.ArrayList;
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

    private final WebSocketMessageSender webSocketMessageSender;

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
                                        WebSocketMessageSender webSocketMessageSender,
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
        this.webSocketMessageSender = webSocketMessageSender;
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
            UnreadMessage newUnreadMessage = createUnreadMessage(conversationGroup, creatorUserContact, creatorUserContact.getDisplayName() + " has created this group.");

            // WebSocket users.
            String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(conversationGroup, WebSocketEvent.NEW_CONVERSATION_GROUP);
            String unreadMessageWebSocketString = convertUnreadMessageToWebSocketMessageString(newUnreadMessage, WebSocketEvent.NEW_UNREAD_MESSAGE);

            webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, conversationGroup.getMemberIds());
            webSocketMessageSender.sendMessageToWebSocketUsers(unreadMessageWebSocketString, conversationGroup.getMemberIds());

            // RabbitMQ topics for offline clients.
            sendMessageToRabbitMQ(conversationGroupWebSocketString, conversationGroup.getId(), conversationGroup.getMemberIds());
            sendMessageToRabbitMQ(unreadMessageWebSocketString, conversationGroup.getId(), conversationGroup.getMemberIds());

            sendChatMessageToRabbitMQ(conversationGroup, creatorUserContact, "New conversation Group has been created.", WebSocketEvent.NEW_CONVERSATION_GROUP);
            sendChatMessageToRabbitMQ(conversationGroup, creatorUserContact, "You have been added into this conversation by" + creatorUserContact.getDisplayName() + ".", WebSocketEvent.JOINED_CONVERSATION_GROUP);

            // Supported notification providers
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

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(conversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, conversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(conversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, conversationGroup.getId(), conversationGroup.getMemberIds());

        // Supported notification providers

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

        WebSocketEvent webSocketEvent = WebSocketEvent.DELETED_GROUP_PHOTO;

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(conversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, conversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(conversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, conversationGroup.getId(), conversationGroup.getMemberIds());

        // Supported notification providers

    }

    @Override
    @Transactional
    public ConversationGroup editConversationGroup(UpdateConversationGroupRequest updateConversationGroupRequest) {
        ConversationGroup existingConversationGroup = getSingleConversation(updateConversationGroupRequest.getId());
        UserContact ownUserContact = userContactService.getOwnUserContact();
        ConversationGroup updatedConversationGroup = null;

        WebSocketEvent webSocketEvent = null;
        String messageContent = "";

        if (StringUtils.hasText(updateConversationGroupRequest.getName())) {
            updatedConversationGroup = conversationGroupRepoService.updateConversationGroupName(updateConversationGroupRequest.getId(), updateConversationGroupRequest.getName());
            webSocketEvent = WebSocketEvent.CHANGED_GROUP_NAME;
            messageContent = "The group name " + existingConversationGroup.getName() + " has been changed to " + updatedConversationGroup.getName() + " by " + ownUserContact.getDisplayName();
        } else if (StringUtils.hasText(updateConversationGroupRequest.getDescription())) {
            updatedConversationGroup = conversationGroupRepoService.updateConversationGroupDescription(updateConversationGroupRequest.getId(), updateConversationGroupRequest.getDescription());
            webSocketEvent = WebSocketEvent.CHANGED_GROUP_DESCRIPTION;
            messageContent = "The group description " + existingConversationGroup.getDescription() + " has been changed to " + updatedConversationGroup.getDescription() + " by " + ownUserContact.getDisplayName();
        }

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(updatedConversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, updatedConversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(updatedConversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, updatedConversationGroup.getId(), updatedConversationGroup.getMemberIds());

        // Supported notification providers


        return updatedConversationGroup;
    }

    @Override
    public ConversationGroup addConversationGroupMember(String conversationGroupId, AddConversationGroupMemberRequest addConversationGroupMemberRequest) {
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);
        UserContact ownUserContact = userContactService.getOwnUserContact();

        List<UserContact> userContacts = checkUserContactsExist(addConversationGroupMemberRequest.getGroupMemberIds());

        List<String> existingGroupMemberIds = existingConversationGroup.getMemberIds();

        existingGroupMemberIds.addAll(addConversationGroupMemberRequest.getGroupMemberIds());

        // Remove already added group members, if any.
        List<String> updatedGroupMemberIds = existingGroupMemberIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setMemberIds(updatedGroupMemberIds);

        ConversationGroup updatedConversationGroup = conversationGroupRepoService.save(existingConversationGroup);

        WebSocketEvent webSocketEvent = WebSocketEvent.ADD_GROUP_MEMBER;
        // https://www.geeksforgeeks.org/java-8-streams-collectors-joining-method-with-examples/
        String messageContent = ownUserContact.getDisplayName() + " has added " + userContacts.stream().map(UserContact::getDisplayName).collect(Collectors.joining(", ")) + " into the group.";

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(updatedConversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, updatedConversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(updatedConversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, updatedConversationGroup.getId(), updatedConversationGroup.getMemberIds());

        // Supported notification providers

        return updatedConversationGroup;
    }

    @Override
    public ConversationGroup removeConversationGroupMember(String conversationGroupId, RemoveConversationGroupMemberRequest removeConversationGroupMemberRequest) {
        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        UserContact ownUserContact = userContactService.getOwnUserContact();

        List<UserContact> userContacts = checkUserContactsExist(removeConversationGroupMemberRequest.getGroupMemberIds());

        List<String> existingGroupMemberIds = existingConversationGroup.getMemberIds();

        existingGroupMemberIds.removeAll(removeConversationGroupMemberRequest.getGroupMemberIds());

        List<String> updatedGroupMemberIds = existingGroupMemberIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setMemberIds(updatedGroupMemberIds);

        ConversationGroup updatedConversationGroup = conversationGroupRepoService.save(existingConversationGroup);

        WebSocketEvent webSocketEvent = WebSocketEvent.REMOVE_GROUP_MEMBER;
        // https://www.geeksforgeeks.org/java-8-streams-collectors-joining-method-with-examples/
        String messageContent = ownUserContact.getDisplayName() + " has removed " + userContacts.stream().map(UserContact::getDisplayName).collect(Collectors.joining(", ")) + " from the group.";

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(updatedConversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, updatedConversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(updatedConversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, updatedConversationGroup.getId(), updatedConversationGroup.getMemberIds());

        // Supported notification providers

        return updatedConversationGroup;
    }

    @Override
    public ConversationGroup addConversationGroupAdmin(String conversationGroupId, AddConversationGroupAdminRequest addConversationGroupAdminRequest) {
        UserContact ownUserContact = userContactService.getOwnUserContact();

        ConversationGroup existingConversationGroup = getSingleConversation(conversationGroupId);

        // Check current responsible group member is part of the group and group admin or not.
        boolean userContactIsConversationGroupMember = userContactIsConversationGroupMember(ownUserContact.getId(), existingConversationGroup);
        boolean userContactIsConversationGroupAdmin = userContactIsConversationGroupAdmin(ownUserContact.getId(), existingConversationGroup);

        if (!userContactIsConversationGroupMember || !userContactIsConversationGroupAdmin) {
            throw new ConversationGroupMemberPermissionException("Group member has no permission to remove promote a group admin, id: " + ownUserContact.getId());
        }

        List<UserContact> userContacts = checkUserContactsExist(addConversationGroupAdminRequest.getGroupMemberIds());

        List<String> existingGroupAdminIds = existingConversationGroup.getAdminMemberIds();

        existingGroupAdminIds.addAll(addConversationGroupAdminRequest.getGroupMemberIds());

        List<String> updatedGroupAdminIds = existingGroupAdminIds.stream().distinct().collect(Collectors.toList());

        existingConversationGroup.setAdminMemberIds(updatedGroupAdminIds);

        ConversationGroup updatedConversationGroup = conversationGroupRepoService.save(existingConversationGroup);

        WebSocketEvent webSocketEvent = WebSocketEvent.PROMOTE_GROUP_ADMIN;
        // https://www.geeksforgeeks.org/java-8-streams-collectors-joining-method-with-examples/
        String messageContent = ownUserContact.getDisplayName() + " has promoted " + userContacts.stream().map(UserContact::getDisplayName).collect(Collectors.joining(", ")) + " as group admin(s).";

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(updatedConversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, updatedConversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(updatedConversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, updatedConversationGroup.getId(), updatedConversationGroup.getMemberIds());

        // Supported notification providers

        return updatedConversationGroup;
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

        List<UserContact> userContacts = checkUserContactsExist(removeConversationGroupAdminRequest.getGroupMemberIds());

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

        ConversationGroup updatedConversationGroup = conversationGroupRepoService.save(existingConversationGroup);

        WebSocketEvent webSocketEvent = WebSocketEvent.DEMOTE_GROUP_ADMIN;
        // https://www.geeksforgeeks.org/java-8-streams-collectors-joining-method-with-examples/
        String messageContent = ownUserContact.getDisplayName() + " has demoted " + userContacts.stream().map(UserContact::getDisplayName).collect(Collectors.joining(", ")) + " to group members.";

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(updatedConversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, updatedConversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(updatedConversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, updatedConversationGroup.getId(), updatedConversationGroup.getMemberIds());

        // Supported notification providers

        return updatedConversationGroup;
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
            if (existingConversationGroup.getAdminMemberIds().size() == 1 && existingConversationGroup.getAdminMemberIds().get(0).equals(ownUserContact.getId())) {
                throw new NoConversationGroupAdminException("No group admins left. Promote someone else first before remove yourself.");
            }
        }

        existingConversationGroup.setMemberIds(existingConversationGroupMembers);
        existingConversationGroup.setAdminMemberIds(existingConversationGroupAdmins);

        ConversationGroup updatedConversationGroup = conversationGroupRepoService.save(existingConversationGroup);

        WebSocketEvent webSocketEvent = WebSocketEvent.LEFT_CONVERSATION_GROUP;
        String messageContent = ownUserContact.getDisplayName() + " has left the conversation group.";

        // WebSocket users.
        String conversationGroupWebSocketString = convertConversationGroupToWebSocketMessageString(updatedConversationGroup, webSocketEvent);

        webSocketMessageSender.sendMessageToWebSocketUsers(conversationGroupWebSocketString, updatedConversationGroup.getMemberIds());

        // RabbitMQ topics for offline clients.
        sendChatMessageToRabbitMQ(updatedConversationGroup, ownUserContact, messageContent, webSocketEvent);
        sendMessageToRabbitMQ(conversationGroupWebSocketString, updatedConversationGroup.getId(), updatedConversationGroup.getMemberIds());

        // Supported notification providers

        return updatedConversationGroup;
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
    private List<UserContact> checkUserContactsExist(List<String> userContactIds) {
        List<UserContact> userContacts = new ArrayList<>();
        userContactIds.forEach(userContactId -> userContacts.add(userContactService.getUserContact(userContactId)));
        return userContacts;
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
     * Create and save a Chat Message object.
     *
     * @param conversationGroup:  Saved conversationGroup object.
     * @param creatorUserContact: UserContact object of the creator.
     */
    private UnreadMessage createUnreadMessage(ConversationGroup conversationGroup, UserContact creatorUserContact, String lastMessage) {
        CreateUnreadMessageRequest createUnreadMessageRequest = CreateUnreadMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .count(0)
                .date(LocalDateTime.now())
                .lastMessage(lastMessage)
                .userId(creatorUserContact.getUserId())
                .build();

        return unreadMessageService.addUnreadMessage(createUnreadMessageRequest);
    }

    /**
     * Create and save a Chat Message object.
     *
     * @param message:           Message content.
     * @param conversationGroup: ConversationGroup object for CreateChatMessageRequest object.
     * @return ChatMessage object.
     */
    private ChatMessage createChatMessage(String message, ConversationGroup conversationGroup) {
        CreateChatMessageRequest createChatMessageRequest = CreateChatMessageRequest.builder()
                .conversationId(conversationGroup.getId())
                .messageContent(message)
                .build();

        return chatMessageService.addChatMessage(createChatMessageRequest);
    }

    /**
     * Convert ConversationGroup object to WebSocketMessage object string.
     *
     * @param conversationGroup: ConversationGroup object
     * @param webSocketEvent:    WebSocketEvent object to tell what event has happened to the object.
     * @return WebSocketMessage JSON string.
     */
    private String convertConversationGroupToWebSocketMessageString(ConversationGroup conversationGroup, WebSocketEvent webSocketEvent) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .conversationGroup(conversationGroup)
                .webSocketEvent(webSocketEvent)
                .build();
        return convertWebSocketMessageToString(webSocketMessage);
    }

    /**
     * Convert UnreadMessage object to WebSocketMessage object string.
     *
     * @param unreadMessage:  UnreadMessage object
     * @param webSocketEvent: WebSocketEvent object to tell what event has happened to the object.
     * @return WebSocketMessage JSON string.
     */
    private String convertUnreadMessageToWebSocketMessageString(UnreadMessage unreadMessage, WebSocketEvent webSocketEvent) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .unreadMessage(unreadMessage)
                .webSocketEvent(webSocketEvent)
                .build();
        return convertWebSocketMessageToString(webSocketMessage);
    }

    /**
     * Convert ChatMessage object to WebSocketMessage object string.
     *
     * @return WebSocketMessage object string.
     */
    private String convertChatMessageToWebSocketMessageString(ChatMessage chatMessage, UserContact responsibleUserContact, WebSocketEvent webSocketEvent) {
        WebSocketMessage webSocketMessage = WebSocketMessage.builder()
                .webSocketEvent(webSocketEvent)
                .chatMessage(chatMessage)
                .userContact(responsibleUserContact)
                .build();

        return convertWebSocketMessageToString(webSocketMessage);
    }

    /**
     * Convert WebSocketMessage object to WebSocketMessage object string.
     *
     * @param webSocketMessage: WebSocketMessage object
     * @return WebSocketMessage JSON string.
     */
    private String convertWebSocketMessageToString(WebSocketMessage webSocketMessage) {
        try {
            return objectMapper.writeValueAsString(webSocketMessage);
        } catch (JsonProcessingException e) {
            throw new WebSocketObjectConversionFailedException("Failed to convert WebSocket message to JSON string. Message: "
                    + e.getMessage());
        }
    }

    /**
     * Send Chat Message to the conversationGroup, along with it's members.
     *
     * @param conversationGroup: The ConversationGroup object.
     * @param message:           Message content.
     * @param webSocketEvent:    WebSocketEvent enum object to indicate what event of the message is belonged to, to allow the frontend client to analyse the message and determine actions.
     */
    private void sendChatMessageToRabbitMQ(ConversationGroup conversationGroup, UserContact responsibleUserContact, String message, WebSocketEvent webSocketEvent) {
        ChatMessage chatMessage = createChatMessage(message, conversationGroup);

        String chatMessageWebSocketString = convertChatMessageToWebSocketMessageString(chatMessage, responsibleUserContact, webSocketEvent);

        sendMessageToRabbitMQ(chatMessageWebSocketString, conversationGroup.getId(), conversationGroup.getMemberIds());
    }

    /**
     * @param webSocketMessageString: JSON string of WebSocketMessage object.
     * @param conversationGroupId:    Conversation Group ID used for exchange in RabbitMQ.
     * @param userIds:                UserContact ID list for RabbitMQ destination topics.
     */
    private void sendMessageToRabbitMQ(String webSocketMessageString, String conversationGroupId, List<String> userIds) {
        userIds.forEach(userId ->
                rabbitMQService.addMessageToQueue(userId, conversationGroupId,
                        conversationGroupId, webSocketMessageString));
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
