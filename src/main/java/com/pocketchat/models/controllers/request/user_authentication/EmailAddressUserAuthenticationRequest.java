package com.pocketchat.models.controllers.request.user_authentication;

public class EmailAddressUserAuthenticationRequest {
    private String emailAddress;

    EmailAddressUserAuthenticationRequest(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public static EmailAddressAuthenticationRequestBuilder builder() {
        return new EmailAddressAuthenticationRequestBuilder();
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EmailAddressUserAuthenticationRequest))
            return false;
        final EmailAddressUserAuthenticationRequest other = (EmailAddressUserAuthenticationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EmailAddressUserAuthenticationRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        return result;
    }

    public String toString() {
        return "EmailAddressAuthenticationRequest(emailAddress=" + this.getEmailAddress() + ")";
    }

    public static class EmailAddressAuthenticationRequestBuilder {
        private String emailAddress;

        EmailAddressAuthenticationRequestBuilder() {
        }

        public EmailAddressUserAuthenticationRequest.EmailAddressAuthenticationRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public EmailAddressUserAuthenticationRequest build() {
            return new EmailAddressUserAuthenticationRequest(emailAddress);
        }

        public String toString() {
            return "EmailAddressAuthenticationRequest.EmailAddressAuthenticationRequestBuilder(emailAddress=" + this.emailAddress + ")";
        }
    }
}
