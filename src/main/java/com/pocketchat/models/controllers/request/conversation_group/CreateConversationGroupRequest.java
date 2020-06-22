package com.pocketchat.models.controllers.request.conversation_group;

import com.pocketchat.models.enums.conversation_group.ConversationGroupType;
import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateConversationGroupRequest {

    private String id;

    @NotBlank
    private String creatorUserId;

    @NotNull
    private DateTime createdDate;

    @NotBlank
    private String name;

    @NotBlank
    private ConversationGroupType conversationGroupType;

    private String description;

    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<String> memberIds;

    @Valid
    @NotEmpty
    @Size(min = 1)
    private List<String> adminMemberIds;

    private boolean block;

    private DateTime notificationExpireDate;

    CreateConversationGroupRequest(String id, @NotBlank String creatorUserId, @NotNull DateTime createdDate, @NotBlank String name, @NotBlank ConversationGroupType conversationGroupType, String description, @Valid @NotEmpty @Size(min = 1) List<String> memberIds, @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds, boolean block, DateTime notificationExpireDate) {
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

    public static CreateConversationGroupRequestBuilder builder() {
        return new CreateConversationGroupRequestBuilder();
    }


    public String getId() {
        return this.id;
    }

    public @NotBlank String getCreatorUserId() {
        return this.creatorUserId;
    }

    public @NotNull DateTime getCreatedDate() {
        return this.createdDate;
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

    public @Valid @NotEmpty @Size(min = 1) List<String> getMemberIds() {
        return this.memberIds;
    }

    public @Valid @NotEmpty @Size(min = 1) List<String> getAdminMemberIds() {
        return this.adminMemberIds;
    }

    public boolean isBlock() {
        return this.block;
    }

    public DateTime getNotificationExpireDate() {
        return this.notificationExpireDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreatorUserId(@NotBlank String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public void setCreatedDate(@NotNull DateTime createdDate) {
        this.createdDate = createdDate;
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

    public void setMemberIds(@Valid @NotEmpty @Size(min = 1) List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public void setAdminMemberIds(@Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds) {
        this.adminMemberIds = adminMemberIds;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setNotificationExpireDate(DateTime notificationExpireDate) {
        this.notificationExpireDate = notificationExpireDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateConversationGroupRequest))
            return false;
        final CreateConversationGroupRequest other = (CreateConversationGroupRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$creatorUserId = this.getCreatorUserId();
        final Object other$creatorUserId = other.getCreatorUserId();
        if (this$creatorUserId == null ? other$creatorUserId != null : !this$creatorUserId.equals(other$creatorUserId))
            return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
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
        if (this.isBlock() != other.isBlock()) return false;
        final Object this$notificationExpireDate = this.getNotificationExpireDate();
        final Object other$notificationExpireDate = other.getNotificationExpireDate();
        if (this$notificationExpireDate == null ? other$notificationExpireDate != null : !this$notificationExpireDate.equals(other$notificationExpireDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateConversationGroupRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $creatorUserId = this.getCreatorUserId();
        result = result * PRIME + ($creatorUserId == null ? 43 : $creatorUserId.hashCode());
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
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
        final Object $notificationExpireDate = this.getNotificationExpireDate();
        result = result * PRIME + ($notificationExpireDate == null ? 43 : $notificationExpireDate.hashCode());
        return result;
    }

    public String toString() {
        return "CreateConversationGroupRequest(id=" + this.getId() + ", creatorUserId=" + this.getCreatorUserId() + ", createdDate=" + this.getCreatedDate() + ", name=" + this.getName() + ", conversationGroupType=" + this.getConversationGroupType() + ", description=" + this.getDescription() + ", memberIds=" + this.getMemberIds() + ", adminMemberIds=" + this.getAdminMemberIds() + ", block=" + this.isBlock() + ", notificationExpireDate=" + this.getNotificationExpireDate() + ")";
    }

    public static class CreateConversationGroupRequestBuilder {
        private String id;
        private @NotBlank String creatorUserId;
        private @NotNull DateTime createdDate;
        private @NotBlank String name;
        private @NotBlank ConversationGroupType conversationGroupType;
        private String description;
        private @Valid @NotEmpty @Size(min = 1) List<String> memberIds;
        private @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds;
        private boolean block;
        private DateTime notificationExpireDate;

        CreateConversationGroupRequestBuilder() {
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder creatorUserId(@NotBlank String creatorUserId) {
            this.creatorUserId = creatorUserId;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder createdDate(@NotNull DateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder name(@NotBlank String name) {
            this.name = name;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder conversationGroupType(@NotBlank ConversationGroupType conversationGroupType) {
            this.conversationGroupType = conversationGroupType;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder memberIds(@Valid @NotEmpty @Size(min = 1) List<String> memberIds) {
            this.memberIds = memberIds;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder adminMemberIds(@Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds) {
            this.adminMemberIds = adminMemberIds;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder block(boolean block) {
            this.block = block;
            return this;
        }

        public CreateConversationGroupRequest.CreateConversationGroupRequestBuilder notificationExpireDate(DateTime notificationExpireDate) {
            this.notificationExpireDate = notificationExpireDate;
            return this;
        }

        public CreateConversationGroupRequest build() {
            return new CreateConversationGroupRequest(id, creatorUserId, createdDate, name, conversationGroupType, description, memberIds, adminMemberIds, block, notificationExpireDate);
        }

        public String toString() {
            return "CreateConversationGroupRequest.CreateConversationGroupRequestBuilder(id=" + this.id + ", creatorUserId=" + this.creatorUserId + ", createdDate=" + this.createdDate + ", name=" + this.name + ", conversationGroupType=" + this.conversationGroupType + ", description=" + this.description + ", memberIds=" + this.memberIds + ", adminMemberIds=" + this.adminMemberIds + ", block=" + this.block + ", notificationExpireDate=" + this.notificationExpireDate + ")";
        }
    }
}
