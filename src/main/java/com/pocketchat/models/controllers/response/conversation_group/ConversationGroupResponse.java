package com.pocketchat.models.controllers.response.conversation_group;

import com.pocketchat.models.enums.conversation_group.ConversationGroupType;

import java.util.List;

public class ConversationGroupResponse {
    private String id;

    private String creatorUserId;

    private long createdDate;

    private String name;

    private ConversationGroupType conversationGroupType;

    String description;

    private List<String> memberIds;

    private List<String> adminMemberIds;

    private boolean block;

    private long notificationExpireDate;

    ConversationGroupResponse(String id, String creatorUserId, long createdDate, String name, ConversationGroupType conversationGroupType, String description, List<String> memberIds, List<String> adminMemberIds, boolean block, long notificationExpireDate) {
        this.id = id;
        this.creatorUserId = creatorUserId;
        this.createdDate = createdDate;
        this.name = name;
        this.conversationGroupType = conversationGroupType;
        this.description = description;
        this.memberIds = memberIds;
        this.adminMemberIds = adminMemberIds;
        this.block = block;
        this.notificationExpireDate = notificationExpireDate;
    }

    public static ConversationGroupResponseBuilder builder() {
        return new ConversationGroupResponseBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getCreatorUserId() {
        return this.creatorUserId;
    }

    public long getCreatedDate() {
        return this.createdDate;
    }

    public String getName() {
        return this.name;
    }

    public ConversationGroupType getConversationGroupType() {
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

    public boolean isBlock() {
        return this.block;
    }

    public long getNotificationExpireDate() {
        return this.notificationExpireDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConversationGroupType(ConversationGroupType conversationGroupType) {
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

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setNotificationExpireDate(long notificationExpireDate) {
        this.notificationExpireDate = notificationExpireDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ConversationGroupResponse))
            return false;
        final ConversationGroupResponse other = (ConversationGroupResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$creatorUserId = this.getCreatorUserId();
        final Object other$creatorUserId = other.getCreatorUserId();
        if (this$creatorUserId == null ? other$creatorUserId != null : !this$creatorUserId.equals(other$creatorUserId))
            return false;
        if (this.getCreatedDate() != other.getCreatedDate()) return false;
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
        if (this.isBlock() != other.isBlock()) return false;
        if (this.getNotificationExpireDate() != other.getNotificationExpireDate()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ConversationGroupResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $creatorUserId = this.getCreatorUserId();
        result = result * PRIME + ($creatorUserId == null ? 43 : $creatorUserId.hashCode());
        final long $createdDate = this.getCreatedDate();
        result = result * PRIME + (int) ($createdDate >>> 32 ^ $createdDate);
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
        result = result * PRIME + (this.isBlock() ? 79 : 97);
        final long $notificationExpireDate = this.getNotificationExpireDate();
        result = result * PRIME + (int) ($notificationExpireDate >>> 32 ^ $notificationExpireDate);
        return result;
    }

    public String toString() {
        return "ConversationGroupResponse(id=" + this.getId() + ", creatorUserId=" + this.getCreatorUserId() + ", createdDate=" + this.getCreatedDate() + ", name=" + this.getName() + ", conversationGroupType=" + this.getConversationGroupType() + ", description=" + this.getDescription() + ", memberIds=" + this.getMemberIds() + ", adminMemberIds=" + this.getAdminMemberIds() + ", block=" + this.isBlock() + ", notificationExpireDate=" + this.getNotificationExpireDate() + ")";
    }

    public static class ConversationGroupResponseBuilder {
        private String id;
        private String creatorUserId;
        private long createdDate;
        private String name;
        private ConversationGroupType conversationGroupType;
        private String description;
        private List<String> memberIds;
        private List<String> adminMemberIds;
        private boolean block;
        private long notificationExpireDate;

        ConversationGroupResponseBuilder() {
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder creatorUserId(String creatorUserId) {
            this.creatorUserId = creatorUserId;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder createdDate(long createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder conversationGroupType(ConversationGroupType conversationGroupType) {
            this.conversationGroupType = conversationGroupType;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder memberIds(List<String> memberIds) {
            this.memberIds = memberIds;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder adminMemberIds(List<String> adminMemberIds) {
            this.adminMemberIds = adminMemberIds;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder block(boolean block) {
            this.block = block;
            return this;
        }

        public ConversationGroupResponse.ConversationGroupResponseBuilder notificationExpireDate(long notificationExpireDate) {
            this.notificationExpireDate = notificationExpireDate;
            return this;
        }

        public ConversationGroupResponse build() {
            return new ConversationGroupResponse(id, creatorUserId, createdDate, name, conversationGroupType, description, memberIds, adminMemberIds, block, notificationExpireDate);
        }

        public String toString() {
            return "ConversationGroupResponse.ConversationGroupResponseBuilder(id=" + this.id + ", creatorUserId=" + this.creatorUserId + ", createdDate=" + this.createdDate + ", name=" + this.name + ", conversationGroupType=" + this.conversationGroupType + ", description=" + this.description + ", memberIds=" + this.memberIds + ", adminMemberIds=" + this.adminMemberIds + ", block=" + this.block + ", notificationExpireDate=" + this.notificationExpireDate + ")";
        }
    }
}
