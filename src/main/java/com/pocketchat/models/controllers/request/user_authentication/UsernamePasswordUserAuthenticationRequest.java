package com.pocketchat.models.controllers.request.user_authentication;

import javax.validation.constraints.NotBlank;

// Model for accepting user credentials
public class UsernamePasswordUserAuthenticationRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public @NotBlank String getUsername() {
        return this.username;
    }

    public @NotBlank String getPassword() {
        return this.password;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}
