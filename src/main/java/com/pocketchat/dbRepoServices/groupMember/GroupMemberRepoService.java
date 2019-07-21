package com.pocketchat.dbRepoServices.groupMember;

import com.pocketchat.dbRepositories.groupMember.GroupMemberRepository;
import com.pocketchat.models.groupMember.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberRepoService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    // TODO: Ask Wesley to know how to connect Spring Boot to MongoDB database
    private final MongoTemplate mongoTemplate = null;

    public GroupMemberRepository getGroupMemberRepository() {
        return groupMemberRepository;
    }

    // To find all groupMember object's ID as a List from using User's object ID
    public List<GroupMember> getGroupMembersIdListByUserId (String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));

        return mongoTemplate.find(query, GroupMember.class);
    }
}
