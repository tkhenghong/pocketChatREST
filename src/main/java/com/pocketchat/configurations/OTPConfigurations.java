package com.pocketchat.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server.otp")
public class OTPConfigurations {
    private int length;
    private int maximumAliveMinutes;
    private int maximumRetryAttempt;

    public OTPConfigurations() {
    }

    public int getLength() {
        return this.length;
    }

    public int getMaximumAliveMinutes() {
        return this.maximumAliveMinutes;
    }

    public int getMaximumRetryAttempt() {
        return this.maximumRetryAttempt;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setMaximumAliveMinutes(int maximumAliveMinutes) {
        this.maximumAliveMinutes = maximumAliveMinutes;
    }

    public void setMaximumRetryAttempt(int maximumRetryAttempt) {
        this.maximumRetryAttempt = maximumRetryAttempt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OTPConfigurations)) return false;
        final OTPConfigurations other = (OTPConfigurations) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getLength() != other.getLength()) return false;
        if (this.getMaximumAliveMinutes() != other.getMaximumAliveMinutes()) return false;
        if (this.getMaximumRetryAttempt() != other.getMaximumRetryAttempt()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OTPConfigurations;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getLength();
        result = result * PRIME + this.getMaximumAliveMinutes();
        result = result * PRIME + this.getMaximumRetryAttempt();
        return result;
    }

    public String toString() {
        return "OTPConfigurations(length=" + this.getLength() + ", maximumAliveMinutes=" + this.getMaximumAliveMinutes() + ", maximumRetryAttempt=" + this.getMaximumRetryAttempt() + ")";
    }
}
