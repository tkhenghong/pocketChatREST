package com.pocketchat.models.otp;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GenerateOTPRequest {

    @NotBlank
    private String userId;

    @NotNull
    @Min(4)
    private Integer otpLength;

    @NotNull
    @Min(3)
    private Integer otpAliveMinutes;

    GenerateOTPRequest(@NotBlank String userId, @NotNull @Min(4) Integer otpLength, @NotNull @Min(3) Integer otpAliveMinutes) {
        this.userId = userId;
        this.otpLength = otpLength;
        this.otpAliveMinutes = otpAliveMinutes;
    }

    public static GenerateOTPRequestBuilder builder() {
        return new GenerateOTPRequestBuilder();
    }

    public @NotBlank String getUserId() {
        return this.userId;
    }

    public @NotNull @Min(4) Integer getOtpLength() {
        return this.otpLength;
    }

    public @NotNull @Min(3) Integer getOtpAliveMinutes() {
        return this.otpAliveMinutes;
    }

    public void setUserId(@NotBlank String userId) {
        this.userId = userId;
    }

    public void setOtpLength(@NotNull @Min(4) Integer otpLength) {
        this.otpLength = otpLength;
    }

    public void setOtpAliveMinutes(@NotNull @Min(3) Integer otpAliveMinutes) {
        this.otpAliveMinutes = otpAliveMinutes;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GenerateOTPRequest)) return false;
        final GenerateOTPRequest other = (GenerateOTPRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$otpLength = this.getOtpLength();
        final Object other$otpLength = other.getOtpLength();
        if (this$otpLength == null ? other$otpLength != null : !this$otpLength.equals(other$otpLength)) return false;
        final Object this$otpAliveMinutes = this.getOtpAliveMinutes();
        final Object other$otpAliveMinutes = other.getOtpAliveMinutes();
        if (this$otpAliveMinutes == null ? other$otpAliveMinutes != null : !this$otpAliveMinutes.equals(other$otpAliveMinutes))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GenerateOTPRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $otpLength = this.getOtpLength();
        result = result * PRIME + ($otpLength == null ? 43 : $otpLength.hashCode());
        final Object $otpAliveMinutes = this.getOtpAliveMinutes();
        result = result * PRIME + ($otpAliveMinutes == null ? 43 : $otpAliveMinutes.hashCode());
        return result;
    }

    public String toString() {
        return "GenerateOTPRequest(userId=" + this.getUserId() + ", otpLength=" + this.getOtpLength() + ", otpAliveMinutes=" + this.getOtpAliveMinutes() + ")";
    }

    public static class GenerateOTPRequestBuilder {
        private @NotBlank String userId;
        private @NotNull @Min(4) Integer otpLength;
        private @NotNull @Min(3) Integer otpAliveMinutes;

        GenerateOTPRequestBuilder() {
        }

        public GenerateOTPRequest.GenerateOTPRequestBuilder userId(@NotBlank String userId) {
            this.userId = userId;
            return this;
        }

        public GenerateOTPRequest.GenerateOTPRequestBuilder otpLength(@NotNull @Min(4) Integer otpLength) {
            this.otpLength = otpLength;
            return this;
        }

        public GenerateOTPRequest.GenerateOTPRequestBuilder otpAliveMinutes(@NotNull @Min(3) Integer otpAliveMinutes) {
            this.otpAliveMinutes = otpAliveMinutes;
            return this;
        }

        public GenerateOTPRequest build() {
            return new GenerateOTPRequest(userId, otpLength, otpAliveMinutes);
        }

        public String toString() {
            return "GenerateOTPRequest.GenerateOTPRequestBuilder(userId=" + this.userId + ", otpLength=" + this.otpLength + ", otpAliveMinutes=" + this.otpAliveMinutes + ")";
        }
    }
}
