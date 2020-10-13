package com.pocketchat.db.models.user_privilege;

import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_privilege")
public class UserPrivilege extends Auditable {

    @Id
    private String id;

    private String name;

    UserPrivilege(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserPrivilegeBuilder builder() {
        return new UserPrivilegeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserPrivilege)) return false;
        final UserPrivilege other = (UserPrivilege) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserPrivilege;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "UserPrivilege(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public static class UserPrivilegeBuilder {
        private String id;
        private String name;

        UserPrivilegeBuilder() {
        }

        public UserPrivilege.UserPrivilegeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserPrivilege.UserPrivilegeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserPrivilege build() {
            return new UserPrivilege(id, name);
        }

        public String toString() {
            return "UserPrivilege.UserPrivilegeBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
