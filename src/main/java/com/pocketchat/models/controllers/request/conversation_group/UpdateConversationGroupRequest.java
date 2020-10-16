package com.pocketchat.models.controllers.request.conversation_group;

import javax.validation.constraints.NotBlank;

public class UpdateConversationGroupRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private String description;

    UpdateConversationGroupRequest(@NotBlank String id, @NotBlank String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static UpdateConversationGroupRequestBuilder builder() {
        return new UpdateConversationGroupRequestBuilder();
    }


    public @NotBlank String getId() {
        return this.id;
    }

    public @NotBlank String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setId(@NotBlank String id) {
        this.id = id;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
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
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    public String toString() {
        return "UpdateConversationGroupRequest(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ")";
    }

    public static class UpdateConversationGroupRequestBuilder {
        private @NotBlank String id;
        private @NotBlank String name;
        private String description;

        UpdateConversationGroupRequestBuilder() {
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder id(@NotBlank String id) {
            this.id = id;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder name(@NotBlank String name) {
            this.name = name;
            return this;
        }

        public UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public UpdateConversationGroupRequest build() {
            return new UpdateConversationGroupRequest(id, name, description);
        }

        public String toString() {
            return "UpdateConversationGroupRequest.UpdateConversationGroupRequestBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ")";
        }
    }
}
