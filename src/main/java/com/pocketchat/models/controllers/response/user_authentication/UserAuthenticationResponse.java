package com.pocketchat.models.controllers.response.user_authentication;

public class UserAuthenticationResponse {
    private String jwt;

    UserAuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserAuthenticationResponse))
            return false;
        final UserAuthenticationResponse other = (UserAuthenticationResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$jwt = this.getJwt();
        final Object other$jwt = other.getJwt();
        if (this$jwt == null ? other$jwt != null : !this$jwt.equals(other$jwt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserAuthenticationResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $jwt = this.getJwt();
        result = result * PRIME + ($jwt == null ? 43 : $jwt.hashCode());
        return result;
    }

    public String toString() {
        return "AuthenticationResponse(jwt=" + this.getJwt() + ")";
    }

    public static class AuthenticationResponseBuilder {
        private String jwt;

        AuthenticationResponseBuilder() {
        }

        public UserAuthenticationResponse.AuthenticationResponseBuilder jwt(String jwt) {
            this.jwt = jwt;
            return this;
        }

        public UserAuthenticationResponse build() {
            return new UserAuthenticationResponse(jwt);
        }

        public String toString() {
            return "AuthenticationResponse.AuthenticationResponseBuilder(jwt=" + this.jwt + ")";
        }
    }
}
