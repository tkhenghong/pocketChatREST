package com.pocketchat.models.controllers.request.authentication;

public class EmailAddressAuthenticationRequest {
    private String emailAddress;

    EmailAddressAuthenticationRequest(String emailAddress) {
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
        if (!(o instanceof EmailAddressAuthenticationRequest))
            return false;
        final EmailAddressAuthenticationRequest other = (EmailAddressAuthenticationRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EmailAddressAuthenticationRequest;
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

        public EmailAddressAuthenticationRequest.EmailAddressAuthenticationRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public EmailAddressAuthenticationRequest build() {
            return new EmailAddressAuthenticationRequest(emailAddress);
        }

        public String toString() {
            return "EmailAddressAuthenticationRequest.EmailAddressAuthenticationRequestBuilder(emailAddress=" + this.emailAddress + ")";
        }
    }
}
