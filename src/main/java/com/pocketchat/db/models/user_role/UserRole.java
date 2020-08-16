package com.pocketchat.db.models.user_role;

import com.pocketchat.db.models.user_privilege.UserPrivilege;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection = "user_role")
public class UserRole {
    @Id
    private String id;

    private String name;

    private Collection<UserPrivilege> userPrivileges;

    UserRole(String id, String name, Collection<UserPrivilege> userPrivileges) {
        this.id = id;
        this.name = name;
        this.userPrivileges = userPrivileges;
    }

    public static RoleBuilder builder() {
        return new RoleBuilder();
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
        final Object this$privileges = this.getUserPrivileges();
        final Object other$privileges = other.getUserPrivileges();
        if (this$privileges == null ? other$privileges != null : !this$privileges.equals(other$privileges))
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
        final Object $privileges = this.getUserPrivileges();
        result = result * PRIME + ($privileges == null ? 43 : $privileges.hashCode());
        return result;
    }

    public String toString() {
        return "Role(id=" + this.getId() + ", name=" + this.getName() + ", privileges=" + this.getUserPrivileges() + ")";
    }

    public static class RoleBuilder {
        private String id;
        private String name;
        private Collection<UserPrivilege> userPrivileges;

        RoleBuilder() {
        }

        public UserRole.RoleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserRole.RoleBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserRole.RoleBuilder privileges(Collection<UserPrivilege> userPrivileges) {
            this.userPrivileges = userPrivileges;
            return this;
        }

        public UserRole build() {
            return new UserRole(id, name, userPrivileges);
        }

        public String toString() {
            return "Role.RoleBuilder(id=" + this.id + ", name=" + this.name + ", privileges=" + this.userPrivileges + ")";
        }
    }
}
