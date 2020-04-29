package com.pocketchat.models.controllers.request.conversation_group;

import org.joda.time.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ConversationGroupRequest {

    private String id;

    @NotBlank
    private String creatorUserId;

    @NotNull
    private DateTime createdDate;

    @NotBlank
    private String name;

    @NotBlank
    private String type;

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

    ConversationGroupRequest(String id, @NotBlank String creatorUserId, @NotNull DateTime createdDate, @NotBlank String name, @NotBlank String type, String description, @Valid @NotEmpty @Size(min = 1) List<String> memberIds, @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds, boolean block, DateTime notificationExpireDate) {
        this.id = id;
        this.creatorUserId = creatorUserId;
        this.createdDate = createdDate;
        this.name = name;
        this.type = type;
        this.description = description;
        this.memberIds = memberIds;
        this.adminMemberIds = adminMemberIds;
        this.block = block;
        this.notificationExpireDate = notificationExpireDate;
    }

    public static ConversationGroupRequestBuilder builder() {
        return new ConversationGroupRequestBuilder();
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

    public @NotBlank String getType() {
        return this.type;
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

    public void setType(@NotBlank String type) {
        this.type = type;
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
        if (!(o instanceof ConversationGroupRequest))
            return false;
        final ConversationGroupRequest other = (ConversationGroupRequest) o;
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
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
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
        return other instanceof ConversationGroupRequest;
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
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
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
        return "ConversationGroupRequest(id=" + this.getId() + ", creatorUserId=" + this.getCreatorUserId() + ", createdDate=" + this.getCreatedDate() + ", name=" + this.getName() + ", type=" + this.getType() + ", description=" + this.getDescription() + ", memberIds=" + this.getMemberIds() + ", adminMemberIds=" + this.getAdminMemberIds() + ", block=" + this.isBlock() + ", notificationExpireDate=" + this.getNotificationExpireDate() + ")";
    }

    public static class ConversationGroupRequestBuilder {
        private String id;
        private @NotBlank String creatorUserId;
        private @NotNull DateTime createdDate;
        private @NotBlank String name;
        private @NotBlank String type;
        private String description;
        private @Valid @NotEmpty @Size(min = 1) List<String> memberIds;
        private @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds;
        private boolean block;
        private DateTime notificationExpireDate;

        ConversationGroupRequestBuilder() {
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder creatorUserId(@NotBlank String creatorUserId) {
            this.creatorUserId = creatorUserId;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder createdDate(@NotNull DateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder name(@NotBlank String name) {
            this.name = name;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder type(@NotBlank String type) {
            this.type = type;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder memberIds(@Valid @NotEmpty @Size(min = 1) List<String> memberIds) {
            this.memberIds = memberIds;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder adminMemberIds(@Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds) {
            this.adminMemberIds = adminMemberIds;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder block(boolean block) {
            this.block = block;
            return this;
        }

        public ConversationGroupRequest.ConversationGroupRequestBuilder notificationExpireDate(DateTime notificationExpireDate) {
            this.notificationExpireDate = notificationExpireDate;
            return this;
        }

        public ConversationGroupRequest build() {
            return new ConversationGroupRequest(id, creatorUserId, createdDate, name, type, description, memberIds, adminMemberIds, block, notificationExpireDate);
        }

        public String toString() {
            return "ConversationGroupRequest.ConversationGroupRequestBuilder(id=" + this.id + ", creatorUserId=" + this.creatorUserId + ", createdDate=" + this.createdDate + ", name=" + this.name + ", type=" + this.type + ", description=" + this.description + ", memberIds=" + this.memberIds + ", adminMemberIds=" + this.adminMemberIds + ", block=" + this.block + ", notificationExpireDate=" + this.notificationExpireDate + ")";
        }
    }
}
