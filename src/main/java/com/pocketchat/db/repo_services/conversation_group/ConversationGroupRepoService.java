package com.pocketchat.db.repo_services.conversation_group;

import com.pocketchat.db.models.conversation_group.ConversationGroup;
import com.pocketchat.db.repositories.conversation_group.ConversationGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationGroupRepoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversationGroupRepository conversationGroupRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ConversationGroupRepoService(ConversationGroupRepository conversationGroupRepository, MongoTemplate mongoTemplate) {
        this.conversationGroupRepository = conversationGroupRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<ConversationGroup> findAllConversationGroupsByIds(List<String> ids) {
        Iterable<ConversationGroup> conversationGroupIterable = conversationGroupRepository.findAllById(ids);

        // From Iterable to Collection List
        // https://stackoverflow.com/questions/6416706/easy-way-to-convert-iterable-to-collection
        List<ConversationGroup> conversationGroupList = new ArrayList<>();
        conversationGroupIterable.forEach(conversationGroupList::add);
        return conversationGroupList;
    }

    public Optional<ConversationGroup> findById(String conversationId) {
        return conversationGroupRepository.findById(conversationId);
    }

    /**
     * Find all conversation groups that has the userContactID.
     *
     * @param userContactId : UserContact ID
     * @return List of Conversation Group.
     */
    public List<ConversationGroup> findAllByMemberIds(String userContactId) {
        return conversationGroupRepository.findAllByMemberIds(userContactId);
    }

    /**
     * Find all conversation groups that has the userContactID
     *
     * @param userContactId : UserContact ID
     * @param pageable:     Pageable object for pagination.
     * @return Page object of Conversation Group with pagination details.
     */
    public Page<ConversationGroup> findAllByMemberIds(String userContactId, Pageable pageable) {
        if (ObjectUtils.isEmpty(pageable)) {
            pageable = Pageable.unpaged();
        }
        return conversationGroupRepository.findAllByMemberIds(userContactId, pageable);
    }

    /**
     * Used for finding conversationGroup that has exact same member.
     * Used during creation of private conversation group.
     *
     * @param memberIds: List of UserContact ID which represents group members.
     * @return List of conversation groups.
     */
    public List<ConversationGroup> findAllByMemberIds(List<String> memberIds) {
        return conversationGroupRepository.findAllByMemberIds(memberIds);
    }

    public ConversationGroup updateConversationGroupName(String conversationGroupId, String name) {
        Query query = new Query(Criteria.where("id").is(conversationGroupId));

        Update update = new Update();
        update.set("name", name);
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, ConversationGroup.class);
    }

    public ConversationGroup updateConversationGroupDescription(String conversationGroupId, String description) {
        Query query = new Query(Criteria.where("id").is(conversationGroupId));

        Update update = new Update();
        update.set("description", description);
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, ConversationGroup.class);
    }

    public ConversationGroup save(ConversationGroup conversationGroup) {
        return conversationGroupRepository.save(conversationGroup);
    }

    public void delete(ConversationGroup conversationGroup) {
        conversationGroupRepository.delete(conversationGroup);
    }
}
