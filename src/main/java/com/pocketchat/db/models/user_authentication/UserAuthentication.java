package com.pocketchat.db.models.user_authentication;

import com.pocketchat.db.models.user_role.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Document(collection = "user_authentication")
public class UserAuthentication {

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Collection<UserRole> userRoles;

    UserAuthentication(String id, @NotBlank String username, @NotBlank String password, Collection<UserRole> userRoles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRoles = userRoles;
    }

    public static UserAuthenticationBuilder builder() {
        return new UserAuthenticationBuilder();
    }

    public String getId() {
        return this.id;
    }

    public @NotBlank String getUsername() {
        return this.username;
    }

    public @NotBlank String getPassword() {
        return this.password;
    }

    public Collection<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserAuthentication)) return false;
        final UserAuthentication other = (UserAuthentication) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$userRoles = this.getUserRoles();
        final Object other$userRoles = other.getUserRoles();
        if (this$userRoles == null ? other$userRoles != null : !this$userRoles.equals(other$userRoles)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserAuthentication;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $userRoles = this.getUserRoles();
        result = result * PRIME + ($userRoles == null ? 43 : $userRoles.hashCode());
        return result;
    }

    public String toString() {
        return "UserAuthentication(id=" + this.getId() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ", userRoles=" + this.getUserRoles() + ")";
    }

    public static class UserAuthenticationBuilder {
        private String id;
        private @NotBlank String username;
        private @NotBlank String password;
        private Collection<UserRole> userUserRoles;

        UserAuthenticationBuilder() {
        }

        public UserAuthentication.UserAuthenticationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserAuthentication.UserAuthenticationBuilder username(@NotBlank String username) {
            this.username = username;
            return this;
        }

        public UserAuthentication.UserAuthenticationBuilder password(@NotBlank String password) {
            this.password = password;
            return this;
        }

        public UserAuthentication.UserAuthenticationBuilder userRoles(Collection<UserRole> userUserRoles) {
            this.userUserRoles = userUserRoles;
            return this;
        }

        public UserAuthentication build() {
            return new UserAuthentication(id, username, password, userUserRoles);
        }

        public String toString() {
            return "UserAuthentication.UserAuthenticationBuilder(id=" + this.id + ", username=" + this.username + ", password=" + this.password + ", userRoles=" + this.userUserRoles + ")";
        }
    }
}
