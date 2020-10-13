package com.pocketchat.db.models.user_role;

import com.pocketchat.db.models.user_privilege.UserPrivilege;
import com.pocketchat.server.configurations.auditing.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "user_role")
public class UserRole extends Auditable {
    @Id
    private String id;

    private String name;

    private Collection<UserPrivilege> userPrivileges;

    UserRole(String id, String name, Collection<UserPrivilege> userPrivileges) {
        this.id = id;
        this.name = name;
        this.userPrivileges = userPrivileges;
    }

    public static UserRoleBuilder builder() {
        return new UserRoleBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Collection<UserPrivilege> getUserPrivileges() {
        return this.userPrivileges;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserPrivileges(Collection<UserPrivilege> userPrivileges) {
        this.userPrivileges = userPrivileges;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserRole)) return false;
        final UserRole other = (UserRole) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$userPrivileges = this.getUserPrivileges();
        final Object other$userPrivileges = other.getUserPrivileges();
        if (this$userPrivileges == null ? other$userPrivileges != null : !this$userPrivileges.equals(other$userPrivileges))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserRole;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $userPrivileges = this.getUserPrivileges();
        result = result * PRIME + ($userPrivileges == null ? 43 : $userPrivileges.hashCode());
        return result;
    }

    public String toString() {
        return "UserRole(id=" + this.getId() + ", name=" + this.getName() + ", userPrivileges=" + this.getUserPrivileges() + ")";
    }

    public static class UserRoleBuilder {
        private String id;
        private String name;
        private Collection<UserPrivilege> userPrivileges;

        UserRoleBuilder() {
        }

        public UserRole.UserRoleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserRole.UserRoleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserRole.UserRoleBuilder userPrivileges(Collection<UserPrivilege> userPrivileges) {
            this.userPrivileges = userPrivileges;
            return this;
        }

        public UserRole build() {
            return new UserRole(id, name, userPrivileges);
        }

        public String toString() {
            return "UserRole.UserRoleBuilder(id=" + this.id + ", name=" + this.name + ", userPrivileges=" + this.userPrivileges + ")";
        }
    }
}
