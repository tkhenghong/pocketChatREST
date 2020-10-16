package com.pocketchat.models.controllers.request.conversation_group;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * This is for the Controller layer object
 */
public class MuteConversationGroupNotificationRequest {
    @NotNull
    private LocalDateTime blockNotificationExpireTime;

    public MuteConversationGroupNotificationRequest() {
    }

    public @NotNull LocalDateTime getBlockNotificationExpireTime() {
        return this.blockNotificationExpireTime;
    }

    public void setBlockNotificationExpireTime(@NotNull LocalDateTime blockNotificationExpireTime) {
        this.blockNotificationExpireTime = blockNotificationExpireTime;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MuteConversationGroupNotificationRequest))
            return false;
        final MuteConversationGroupNotificationRequest other = (MuteConversationGroupNotificationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$blockNotificationExpireTime = this.getBlockNotificationExpireTime();
        final Object other$blockNotificationExpireTime = other.getBlockNotificationExpireTime();
        if (this$blockNotificationExpireTime == null ? other$blockNotificationExpireTime != null : !this$blockNotificationExpireTime.equals(other$blockNotificationExpireTime))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MuteConversationGroupNotificationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $blockNotificationExpireTime = this.getBlockNotificationExpireTime();
        result = result * PRIME + ($blockNotificationExpireTime == null ? 43 : $blockNotificationExpireTime.hashCode());
        return result;
    }

    public String toString() {
        return "BlockConversationGroupNotificationRequest(blockNotificationExpireTime=" + this.getBlockNotificationExpireTime() + ")";
    }
}
