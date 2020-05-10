package com.pocketchat.models.email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SendEmailRequest {

    @NotBlank
    private String emailSubject;

    @NotEmpty
    @Size(min = 1, message = "Receiver Email address must NOT LESS THAN 1.")
    private List<String> receiverList;

    private List<String> ccList;
    private List<String> bccList;

    private String emailContent;

    SendEmailRequest(@NotBlank String emailSubject, @NotEmpty @Min(1) List<String> receiverList, List<String> ccList, List<String> bccList, String emailContent) {
        this.emailSubject = emailSubject;
        this.receiverList = receiverList;
        this.ccList = ccList;
        this.bccList = bccList;
        this.emailContent = emailContent;
    }

    public static SendEmailRequestBuilder builder() {
        return new SendEmailRequestBuilder();
    }

    public @NotBlank String getEmailSubject() {
        return this.emailSubject;
    }

    public @NotEmpty @Min(1) List<String> getReceiverList() {
        return this.receiverList;
    }

    public List<String> getCcList() {
        return this.ccList;
    }

    public List<String> getBccList() {
        return this.bccList;
    }

    public String getEmailContent() {
        return this.emailContent;
    }

    public void setEmailSubject(@NotBlank String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public void setReceiverList(@NotEmpty @Min(1) List<String> receiverList) {
        this.receiverList = receiverList;
    }

    public void setCcList(List<String> ccList) {
        this.ccList = ccList;
    }

    public void setBccList(List<String> bccList) {
        this.bccList = bccList;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SendEmailRequest)) return false;
        final SendEmailRequest other = (SendEmailRequest) o;
        if (!other.canEqual((Object) this)) return false;
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
        final Object this$emailContent = this.getEmailContent();
        final Object other$emailContent = other.getEmailContent();
        if (this$emailContent == null ? other$emailContent != null : !this$emailContent.equals(other$emailContent))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SendEmailRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $emailSubject = this.getEmailSubject();
        result = result * PRIME + ($emailSubject == null ? 43 : $emailSubject.hashCode());
        final Object $receiverList = this.getReceiverList();
        result = result * PRIME + ($receiverList == null ? 43 : $receiverList.hashCode());
        final Object $ccList = this.getCcList();
        result = result * PRIME + ($ccList == null ? 43 : $ccList.hashCode());
        final Object $bccList = this.getBccList();
        result = result * PRIME + ($bccList == null ? 43 : $bccList.hashCode());
        final Object $emailContent = this.getEmailContent();
        result = result * PRIME + ($emailContent == null ? 43 : $emailContent.hashCode());
        return result;
    }

    public String toString() {
        return "SendEmailRequest(emailSubject=" + this.getEmailSubject() + ", receiverList=" + this.getReceiverList() + ", ccList=" + this.getCcList() + ", bccList=" + this.getBccList() + ", emailContent=" + this.getEmailContent() + ")";
    }

    public static class SendEmailRequestBuilder {
        private @NotBlank String emailSubject;
        private @NotEmpty @Min(1) List<String> receiverList;
        private List<String> ccList;
        private List<String> bccList;
        private String emailContent;

        SendEmailRequestBuilder() {
        }

        public SendEmailRequest.SendEmailRequestBuilder emailSubject(@NotBlank String emailSubject) {
            this.emailSubject = emailSubject;
            return this;
        }

        public SendEmailRequest.SendEmailRequestBuilder receiverList(@NotEmpty @Min(1) List<String> receiverList) {
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

        public SendEmailRequest.SendEmailRequestBuilder emailContent(String emailContent) {
            this.emailContent = emailContent;
            return this;
        }

        public SendEmailRequest build() {
            return new SendEmailRequest(emailSubject, receiverList, ccList, bccList, emailContent);
        }

        public String toString() {
            return "SendEmailRequest.SendEmailRequestBuilder(emailSubject=" + this.emailSubject + ", receiverList=" + this.receiverList + ", ccList=" + this.ccList + ", bccList=" + this.bccList + ", emailContent=" + this.emailContent + ")";
        }
    }
}
