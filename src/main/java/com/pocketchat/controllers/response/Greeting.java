package com.pocketchat.controllers.response;

public class Greeting {

    // Input object
    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
