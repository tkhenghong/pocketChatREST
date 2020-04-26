package com.pocketchat.db.models.authentication;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "user_authentication")
public class Authentication {

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    Authentication(String id, @NotBlank String username, @NotBlank String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public static AuthenticationBuilder builder() {
        return new AuthenticationBuilder();
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

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Authentication)) return false;
        final Authentication other = (Authentication) o;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Authentication;
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
        return result;
    }

    public String toString() {
        return "Authentication(id=" + this.getId() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ")";
    }

    public static class AuthenticationBuilder {
        private String id;
        private @NotBlank String username;
        private @NotBlank String password;

        AuthenticationBuilder() {
        }

        public Authentication.AuthenticationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public Authentication.AuthenticationBuilder username(@NotBlank String username) {
            this.username = username;
            return this;
        }

        public Authentication.AuthenticationBuilder password(@NotBlank String password) {
            this.password = password;
            return this;
        }

        public Authentication build() {
            return new Authentication(id, username, password);
        }

        public String toString() {
            return "Authentication.AuthenticationBuilder(id=" + this.id + ", username=" + this.username + ", password=" + this.password + ")";
        }
    }
}
