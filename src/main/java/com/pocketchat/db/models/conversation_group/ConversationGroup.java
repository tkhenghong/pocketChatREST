package com.pocketchat.db.models.conversation_group;

import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Document(collection = "conversation_group")
public class ConversationGroup extends Auditable {

    @Id
    private String id;

    @NotBlank
    private String creatorUserId;

    @NotBlank
    private String name;

    @NotBlank
    private ConversationGroupType conversationGroupType;

    private String description;

    @NotEmpty
    @Size(min = 1) // If 1 of 2 persons group left, that person still can have this group
    private List<String> memberIds;

    @NotEmpty
    @Size(min = 1)
    private List<String> adminMemberIds;

    // Using Multimedia ID.
    private String groupPhoto;

    ConversationGroup(String id, @NotBlank String creatorUserId, @NotBlank String name, @NotBlank ConversationGroupType conversationGroupType, String description, List<String> memberIds, List<String> adminMemberIds, String groupPhoto) {
        this.id = id;
        this.creatorUserId = creatorUserId;
        this.name = name;
        this.conversationGroupType = conversationGroupType;
        this.description = description;
        this.memberIds = memberIds;
        this.adminMemberIds = adminMemberIds;
        this.groupPhoto = groupPhoto;
    }

    public static ConversationGroupBuilder builder() {
        return new ConversationGroupBuilder();
    }

    public String getId() {
        return this.id;
    }

    public @NotBlank String getCreatorUserId() {
        return this.creatorUserId;
    }

    public @NotBlank String getName() {
        return this.name;
    }

    public @NotBlank ConversationGroupType getConversationGroupType() {
        return this.conversationGroupType;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getMemberIds() {
        return this.memberIds;
    }

    public List<String> getAdminMemberIds() {
        return this.adminMemberIds;
    }

    public String getGroupPhoto() {
        return this.groupPhoto;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorUserId(@NotBlank String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setConversationGroupType(@NotBlank ConversationGroupType conversationGroupType) {
        this.conversationGroupType = conversationGroupType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public void setAdminMemberIds(List<String> adminMemberIds) {
        this.adminMemberIds = adminMemberIds;
    }

    public void setGroupPhoto(String groupPhoto) {
        this.groupPhoto = groupPhoto;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConversationGroup)) return false;
        final ConversationGroup other = (ConversationGroup) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$creatorUserId = this.getCreatorUserId();
        final Object other$creatorUserId = other.getCreatorUserId();
        if (this$creatorUserId == null ? other$creatorUserId != null : !this$creatorUserId.equals(other$creatorUserId))
            return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$conversationGroupType = this.getConversationGroupType();
        final Object other$conversationGroupType = other.getConversationGroupType();
        if (this$conversationGroupType == null ? other$conversationGroupType != null : !this$conversationGroupType.equals(other$conversationGroupType))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$memberIds = this.getMemberIds();
        final Object other$memberIds = other.getMemberIds();
        if (this$memberIds == null ? other$memberIds != null : !this$memberIds.equals(other$memberIds)) return false;
        final Object this$adminMemberIds = this.getAdminMemberIds();
        final Object other$adminMemberIds = other.getAdminMemberIds();
        if (this$adminMemberIds == null ? other$adminMemberIds != null : !this$adminMemberIds.equals(other$adminMemberIds))
            return false;
        final Object this$groupPhoto = this.getGroupPhoto();
        final Object other$groupPhoto = other.getGroupPhoto();
        if (this$groupPhoto == null ? other$groupPhoto != null : !this$groupPhoto.equals(other$groupPhoto))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConversationGroup;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $creatorUserId = this.getCreatorUserId();
        result = result * PRIME + ($creatorUserId == null ? 43 : $creatorUserId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $conversationGroupType = this.getConversationGroupType();
        result = result * PRIME + ($conversationGroupType == null ? 43 : $conversationGroupType.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $memberIds = this.getMemberIds();
        result = result * PRIME + ($memberIds == null ? 43 : $memberIds.hashCode());
        final Object $adminMemberIds = this.getAdminMemberIds();
        result = result * PRIME + ($adminMemberIds == null ? 43 : $adminMemberIds.hashCode());
        final Object $groupPhoto = this.getGroupPhoto();
        result = result * PRIME + ($groupPhoto == null ? 43 : $groupPhoto.hashCode());
        return result;
    }

    public String toString() {
        return "ConversationGroup(id=" + this.getId() + ", creatorUserId=" + this.getCreatorUserId() + ", name=" + this.getName() + ", conversationGroupType=" + this.getConversationGroupType() + ", description=" + this.getDescription() + ", memberIds=" + this.getMemberIds() + ", adminMemberIds=" + this.getAdminMemberIds() + ", groupPhoto=" + this.getGroupPhoto() + ")";
    }

    public static class ConversationGroupBuilder {
        private String id;
        private @NotBlank String creatorUserId;
        private @NotBlank String name;
        private @NotBlank ConversationGroupType conversationGroupType;
        private String description;
        private List<String> memberIds;
        private List<String> adminMemberIds;
        private String groupPhoto;

        ConversationGroupBuilder() {
        }

        public ConversationGroup.ConversationGroupBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder creatorUserId(@NotBlank String creatorUserId) {
            this.creatorUserId = creatorUserId;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder name(@NotBlank String name) {
            this.name = name;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder conversationGroupType(@NotBlank ConversationGroupType conversationGroupType) {
            this.conversationGroupType = conversationGroupType;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder memberIds(List<String> memberIds) {
            this.memberIds = memberIds;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder adminMemberIds(List<String> adminMemberIds) {
            this.adminMemberIds = adminMemberIds;
            return this;
        }

        public ConversationGroup.ConversationGroupBuilder groupPhoto(String groupPhoto) {
            this.groupPhoto = groupPhoto;
            return this;
        }

        public ConversationGroup build() {
            return new ConversationGroup(id, creatorUserId, name, conversationGroupType, description, memberIds, adminMemberIds, groupPhoto);
        }

        public String toString() {
            return "ConversationGroup.ConversationGroupBuilder(id=" + this.id + ", creatorUserId=" + this.creatorUserId + ", name=" + this.name + ", conversationGroupType=" + this.conversationGroupType + ", description=" + this.description + ", memberIds=" + this.memberIds + ", adminMemberIds=" + this.adminMemberIds + ", groupPhoto=" + this.groupPhoto + ")";
        }
    }
}
