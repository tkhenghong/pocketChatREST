package com.pocketchat.models.controllers.request.user_authentication;

public class VerifyEmailAddressRequest {
    private String emailAddress;

    VerifyEmailAddressRequest(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public static RequestVerifyEmailADdressRequestBuilder builder() {
        return new RequestVerifyEmailADdressRequestBuilder();
    }


    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof VerifyEmailAddressRequest))
            return false;
        final VerifyEmailAddressRequest other = (VerifyEmailAddressRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof VerifyEmailAddressRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        return result;
    }

    public String toString() {
        return "RequestVerifyEmailADdressRequest(emailAddress=" + this.getEmailAddress() + ")";
    }

    public static class RequestVerifyEmailADdressRequestBuilder {
        private String emailAddress;

        RequestVerifyEmailADdressRequestBuilder() {
        }

        public VerifyEmailAddressRequest.RequestVerifyEmailADdressRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public VerifyEmailAddressRequest build() {
            return new VerifyEmailAddressRequest(emailAddress);
        }

        public String toString() {
            return "RequestVerifyEmailADdressRequest.RequestVerifyEmailADdressRequestBuilder(emailAddress=" + this.emailAddress + ")";
        }
    }
}
