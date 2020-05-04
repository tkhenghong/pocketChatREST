package com.pocketchat.models.controllers.request.authentication;

// Model for accepting user credentials
public class UsernamePasswordAuthenticationRequest {

    private String username;
    private String password;

    public UsernamePasswordAuthenticationRequest() {
    }

    public UsernamePasswordAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
