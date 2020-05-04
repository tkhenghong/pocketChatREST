package com.pocketchat.models.email;

import java.util.List;

public class SendEmailRequest {
    private String emailAddress;
    private String emailSubject;
    private List<String> receiverList;
    private List<String> ccList;
    private List<String> bccList;

    SendEmailRequest(String emailAddress, String emailSubject, List<String> receiverList, List<String> ccList, List<String> bccList) {
        this.emailAddress = emailAddress;
        this.emailSubject = emailSubject;
        this.receiverList = receiverList;
        this.ccList = ccList;
        this.bccList = bccList;
    }

    public static SendEmailRequestBuilder builder() {
        return new SendEmailRequestBuilder();
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public String getEmailSubject() {
        return this.emailSubject;
    }

    public List<String> getReceiverList() {
        return this.receiverList;
    }

    public List<String> getCcList() {
        return this.ccList;
    }

    public List<String> getBccList() {
        return this.bccList;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public void setReceiverList(List<String> receiverList) {
        this.receiverList = receiverList;
    }

    public void setCcList(List<String> ccList) {
        this.ccList = ccList;
    }

    public void setBccList(List<String> bccList) {
        this.bccList = bccList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SendEmailRequest)) return false;
        final SendEmailRequest other = (SendEmailRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$emailAddress = this.getEmailAddress();
        final Object other$emailAddress = other.getEmailAddress();
        if (this$emailAddress == null ? other$emailAddress != null : !this$emailAddress.equals(other$emailAddress))
            return false;
        final Object this$emailSubject = this.getEmailSubject();
        final Object other$emailSubject = other.getEmailSubject();
        if (this$emailSubject == null ? other$emailSubject != null : !this$emailSubject.equals(other$emailSubject))
            return false;
        final Object this$receiverList = this.getReceiverList();
        final Object other$receiverList = other.getReceiverList();
        if (this$receiverList == null ? other$receiverList != null : !this$receiverList.equals(other$receiverList))
            return false;
        final Object this$ccList = this.getCcList();
        final Object other$ccList = other.getCcList();
        if (this$ccList == null ? other$ccList != null : !this$ccList.equals(other$ccList)) return false;
        final Object this$bccList = this.getBccList();
        final Object other$bccList = other.getBccList();
        if (this$bccList == null ? other$bccList != null : !this$bccList.equals(other$bccList)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SendEmailRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $emailAddress = this.getEmailAddress();
        result = result * PRIME + ($emailAddress == null ? 43 : $emailAddress.hashCode());
        final Object $emailSubject = this.getEmailSubject();
        result = result * PRIME + ($emailSubject == null ? 43 : $emailSubject.hashCode());
        final Object $receiverList = this.getReceiverList();
        result = result * PRIME + ($receiverList == null ? 43 : $receiverList.hashCode());
        final Object $ccList = this.getCcList();
        result = result * PRIME + ($ccList == null ? 43 : $ccList.hashCode());
        final Object $bccList = this.getBccList();
        result = result * PRIME + ($bccList == null ? 43 : $bccList.hashCode());
        return result;
    }

    public String toString() {
        return "SendEmailRequest(emailAddress=" + this.getEmailAddress() + ", emailSubject=" + this.getEmailSubject() + ", receiverList=" + this.getReceiverList() + ", ccList=" + this.getCcList() + ", bccList=" + this.getBccList() + ")";
    }

    public static class SendEmailRequestBuilder {
        private String emailAddress;
        private String emailSubject;
        private List<String> receiverList;
        private List<String> ccList;
        private List<String> bccList;

        SendEmailRequestBuilder() {
        }

        public SendEmailRequest.SendEmailRequestBuilder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public SendEmailRequest.SendEmailRequestBuilder emailSubject(String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }

        public SendEmailRequest.SendEmailRequestBuilder receiverList(List<String> receiverList) {
            this.receiverList = receiverList;
            return this;
        }

        public SendEmailRequest.SendEmailRequestBuilder ccList(List<String> ccList) {
            this.ccList = ccList;
            return this;
        }

        public SendEmailRequest.SendEmailRequestBuilder bccList(List<String> bccList) {
            this.bccList = bccList;
            return this;
        }

        public SendEmailRequest build() {
            return new SendEmailRequest(emailAddress, emailSubject, receiverList, ccList, bccList);
        }

        public String toString() {
            return "SendEmailRequest.SendEmailRequestBuilder(emailAddress=" + this.emailAddress + ", emailSubject=" + this.emailSubject + ", receiverList=" + this.receiverList + ", ccList=" + this.ccList + ", bccList=" + this.bccList + ")";
        }
    }
}
