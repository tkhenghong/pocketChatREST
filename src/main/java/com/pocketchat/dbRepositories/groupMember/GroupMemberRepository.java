package com.pocketchat.dbRepositories.groupMember;

import com.pocketchat.models.groupMember.GroupMember;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupMemberRepository extends MongoRepository<GroupMember, String> {
}
