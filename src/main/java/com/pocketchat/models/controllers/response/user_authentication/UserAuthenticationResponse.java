package com.pocketchat.models.controllers.response.user_authentication;

import java.time.LocalDateTime;

public class UserAuthenticationResponse {
    private String jwt;
    private String username;
    private LocalDateTime otpExpirationTime;

    UserAuthenticationResponse(String jwt, String username, LocalDateTime otpExpirationTime) {
        this.jwt = jwt;
        this.username = username;
        this.otpExpirationTime = otpExpirationTime;
    }

    public static UserAuthenticationResponseBuilder builder() {
        return new UserAuthenticationResponseBuilder();
    }


    public String getJwt() {
        return this.jwt;
    }

    public String getUsername() {
        return this.username;
    }

    public LocalDateTime getOtpExpirationTime() {
        return this.otpExpirationTime;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOtpExpirationTime(LocalDateTime otpExpirationTime) {
        this.otpExpirationTime = otpExpirationTime;
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
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$otpExpirationTime = this.getOtpExpirationTime();
        final Object other$otpExpirationTime = other.getOtpExpirationTime();
        if (this$otpExpirationTime == null ? other$otpExpirationTime != null : !this$otpExpirationTime.equals(other$otpExpirationTime))
            return false;
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
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $otpExpirationTime = this.getOtpExpirationTime();
        result = result * PRIME + ($otpExpirationTime == null ? 43 : $otpExpirationTime.hashCode());
        return result;
    }

    public String toString() {
        return "UserAuthenticationResponse(jwt=" + this.getJwt() + ", username=" + this.getUsername() + ", otpExpirationTime=" + this.getOtpExpirationTime() + ")";
    }

    public static class UserAuthenticationResponseBuilder {
        private String jwt;
        private String username;
        private LocalDateTime otpExpirationTime;

        UserAuthenticationResponseBuilder() {
        }

        public UserAuthenticationResponse.UserAuthenticationResponseBuilder jwt(String jwt) {
            this.jwt = jwt;
            return this;
        }

        public UserAuthenticationResponse.UserAuthenticationResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserAuthenticationResponse.UserAuthenticationResponseBuilder otpExpirationTime(LocalDateTime otpExpirationTime) {
            this.otpExpirationTime = otpExpirationTime;
            return this;
        }

        public UserAuthenticationResponse build() {
            return new UserAuthenticationResponse(jwt, username, otpExpirationTime);
        }

        public String toString() {
            return "UserAuthenticationResponse.UserAuthenticationResponseBuilder(jwt=" + this.jwt + ", username=" + this.username + ", otpExpirationTime=" + this.otpExpirationTime + ")";
        }
    }
}
