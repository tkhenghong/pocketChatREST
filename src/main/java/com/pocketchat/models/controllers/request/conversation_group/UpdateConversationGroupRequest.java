package com.pocketchat.models.controllers.request.conversation_group;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class UpdateConversationGroupRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String creatorUserId;

    @NotNull
    private LocalDateTime createdDate;

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

    private LocalDateTime notificationExpireDate;

    UpdateConversationGroupRequest(@NotBlank String id, @NotBlank String creatorUserId, @NotNull LocalDateTime createdDate, @NotBlank String name, @NotBlank String type, String description, @Valid @NotEmpty @Size(min = 1) List<String> memberIds, @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds, boolean block, LocalDateTime notificationExpireDate) {
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

    public static UpdateConversationGroupRequestBuilder builder() {
        return new UpdateConversationGroupRequestBuilder();
    }


    public @NotBlank String getId() {
        return this.id;
    }

    public @NotBlank String getCreatorUserId() {
        return this.creatorUserId;
    }

    public @NotNull LocalDateTime getCreatedDate() {
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

    public LocalDateTime getNotificationExpireDate() {
        return this.notificationExpireDate;
    }

    public void setId(@NotBlank String id) {
        this.id = id;
    }

    public void setCreatorUserId(@NotBlank String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public void setCreatedDate(@NotNull LocalDateTime createdDate) {
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

    public void setNotificationExpireDate(LocalDateTime notificationExpireDate) {
        this.notificationExpireDate = notificationExpireDate;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UpdateConversationGroupRequest))
            return false;
        final UpdateConversationGroupRequest other = (UpdateConversationGroupRequest) o;
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
        return other instanceof UpdateConversationGroupRequest;
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
        return "UpdateConversationGroupRequest(id=" + this.getId() + ", creatorUserId=" + this.getCreatorUserId() + ", createdDate=" + this.getCreatedDate() + ", name=" + this.getName() + ", type=" + this.getType() + ", description=" + this.getDescription() + ", memberIds=" + this.getMemberIds() + ", adminMemberIds=" + this.getAdminMemberIds() + ", block=" + this.isBlock() + ", notificationExpireDate=" + this.getNotificationExpireDate() + ")";
    }

    public static class UpdateConversationGroupRequestBuilder {
        private @NotBlank String id;
        private @NotBlank String creatorUserId;
        private @NotNull LocalDateTime createdDate;
        private @NotBlank String name;
        private @NotBlank String type;
        private String description;
        private @Valid @NotEmpty @Size(min = 1) List<String> memberIds;
        private @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds;
        private boolean block;
        private LocalDateTime notificationExpireDate;

        UpdateConversationGroupRequestBuilder() {
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder id(@NotBlank String id) {
            this.id = id;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder creatorUserId(@NotBlank String creatorUserId) {
            this.creatorUserId = creatorUserId;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder createdDate(@NotNull LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder name(@NotBlank String name) {
            this.name = name;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder type(@NotBlank String type) {
            this.type = type;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder memberIds(@Valid @NotEmpty @Size(min = 1) List<String> memberIds) {
            this.memberIds = memberIds;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder adminMemberIds(@Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds) {
            this.adminMemberIds = adminMemberIds;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder block(boolean block) {
            this.block = block;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder notificationExpireDate(LocalDateTime notificationExpireDate) {
            this.notificationExpireDate = notificationExpireDate;
            return this;
        }

        public UpdateConversationGroupRequest build() {
            return new UpdateConversationGroupRequest(id, creatorUserId, createdDate, name, type, description, memberIds, adminMemberIds, block, notificationExpireDate);
        }

        public String toString() {
            return "UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder(id=" + this.id + ", creatorUserId=" + this.creatorUserId + ", createdDate=" + this.createdDate + ", name=" + this.name + ", type=" + this.type + ", description=" + this.description + ", memberIds=" + this.memberIds + ", adminMemberIds=" + this.adminMemberIds + ", block=" + this.block + ", notificationExpireDate=" + this.notificationExpireDate + ")";
        }
    }
}
