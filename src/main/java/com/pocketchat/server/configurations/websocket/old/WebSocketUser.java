package com.pocketchat.server.configurations.websocket.old;

import com.pocketchat.db.models.user.User;
import org.springframework.web.socket.WebSocketSession;

public class WebSocketUser {
    private WebSocketSession webSocketSession;
    private User user;

    WebSocketUser(WebSocketSession webSocketSession, User user) {
        this.webSocketSession = webSocketSession;
        this.user = user;
    }

    WebSocketUser() {
    }

    public static WebSocketUserBuilder builder() {
        return new WebSocketUserBuilder();
    }

    public WebSocketSession getWebSocketSession() {
        return this.webSocketSession;
    }

    public User getUser() {
        return this.user;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof WebSocketUser)) return false;
        final WebSocketUser other = (WebSocketUser) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$webSocketSession = this.getWebSocketSession();
        final Object other$webSocketSession = other.getWebSocketSession();
        if (this$webSocketSession == null ? other$webSocketSession != null : !this$webSocketSession.equals(other$webSocketSession))
            return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WebSocketUser;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $webSocketSession = this.getWebSocketSession();
        result = result * PRIME + ($webSocketSession == null ? 43 : $webSocketSession.hashCode());
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        return result;
    }

    public String toString() {
        return "WebSocketUser(webSocketSession=" + this.getWebSocketSession() + ", user=" + this.getUser() + ")";
    }

    public static class WebSocketUserBuilder {
        private WebSocketSession webSocketSession;
        private User user;

        WebSocketUserBuilder() {
        }

        public WebSocketUser.WebSocketUserBuilder webSocketSession(WebSocketSession webSocketSession) {
            this.webSocketSession = webSocketSession;
            return this;
        }

        public WebSocketUser.WebSocketUserBuilder user(User user) {
            this.user = user;
            return this;
        }

        public WebSocketUser build() {
            return new WebSocketUser(webSocketSession, user);
        }

        public String toString() {
            return "WebSocketUser.WebSocketUserBuilder(webSocketSession=" + this.webSocketSession + ", user=" + this.user + ")";
        }
    }
}
