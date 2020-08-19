package com.pocketchat.models.controllers.request.conversation_group;

import com.pocketchat.models.enums.conversation_group.ConversationGroupType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateConversationGroupRequest {

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

    CreateConversationGroupRequest(@NotBlank String name, @NotBlank ConversationGroupType conversationGroupType, String description, @Valid @NotEmpty @Size(min = 1) List<String> memberIds, @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds) {
        this.name = name;
        this.conversationGroupType = conversationGroupType;
        this.description = description;
        this.memberIds = memberIds;
        this.adminMemberIds = adminMemberIds;
    }

    public static CreateConversationGroupRequestBuilder builder() {
        return new CreateConversationGroupRequestBuilder();
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CreateConversationGroupRequest))
            return false;
        final CreateConversationGroupRequest other = (CreateConversationGroupRequest) o;
        if (!other.canEqual((Object) this)) return false;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CreateConversationGroupRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
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
        return result;
    }

    public String toString() {
        return "CreateConversationGroupRequest(name=" + this.getName() + ", conversationGroupType=" + this.getConversationGroupType() + ", description=" + this.getDescription() + ", memberIds=" + this.getMemberIds() + ", adminMemberIds=" + this.getAdminMemberIds() + ")";
    }

    public static class CreateConversationGroupRequestBuilder {
        private @NotBlank String name;
        private @NotBlank ConversationGroupType conversationGroupType;
        private String description;
        private @Valid @NotEmpty @Size(min = 1) List<String> memberIds;
        private @Valid @NotEmpty @Size(min = 1) List<String> adminMemberIds;

        CreateConversationGroupRequestBuilder() {
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

        public CreateConversationGroupRequest build() {
            return new CreateConversationGroupRequest(name, conversationGroupType, description, memberIds, adminMemberIds);
        }

        public String toString() {
            return "CreateConversationGroupRequest.CreateConversationGroupRequestBuilder(name=" + this.name + ", conversationGroupType=" + this.conversationGroupType + ", description=" + this.description + ", memberIds=" + this.memberIds + ", adminMemberIds=" + this.adminMemberIds + ")";
        }
    }
}
