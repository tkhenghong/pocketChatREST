package com.pocketchat.models.controllers.response.authentication;

public class AuthenticationResponse {
    private String jwt;

    AuthenticationResponse(String jwt) {
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
        if (!(o instanceof AuthenticationResponse))
            return false;
        final AuthenticationResponse other = (AuthenticationResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$jwt = this.getJwt();
        final Object other$jwt = other.getJwt();
        if (this$jwt == null ? other$jwt != null : !this$jwt.equals(other$jwt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AuthenticationResponse;
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

        public AuthenticationResponse.AuthenticationResponseBuilder jwt(String jwt) {
            this.jwt = jwt;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(jwt);
        }

        public String toString() {
            return "AuthenticationResponse.AuthenticationResponseBuilder(jwt=" + this.jwt + ")";
        }
    }
}
