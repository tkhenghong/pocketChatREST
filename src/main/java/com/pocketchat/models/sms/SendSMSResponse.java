package com.pocketchat.models.sms;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Map;

public class SendSMSResponse {
    private String mobileNumber;
    private String message;
    private String errorCode;
    private String errorMessage;
    private LocalDateTime dateSent;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    private String uri;
    private Map<String, String> subResourceUris;
    private String accountSid;
    private String sid;
    private String apiVersion;
    private String body;
    private String fromEndPoint;
    private String to;
    private String numMedia;
    private String numSegments;
    private String price;
    private Currency priceCurrency;
    private String direction;

    SendSMSResponse(String mobileNumber, String message, String errorCode, String errorMessage, LocalDateTime dateSent, LocalDateTime dateCreated, LocalDateTime dateUpdated, String uri, Map<String, String> subResourceUris, String accountSid, String sid, String apiVersion, String body, String fromEndPoint, String to, String numMedia, String numSegments, String price, Currency priceCurrency, String direction) {
        this.mobileNumber = mobileNumber;
        this.message = message;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.dateSent = dateSent;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.uri = uri;
        this.subResourceUris = subResourceUris;
        this.accountSid = accountSid;
        this.sid = sid;
        this.apiVersion = apiVersion;
        this.body = body;
        this.fromEndPoint = fromEndPoint;
        this.to = to;
        this.numMedia = numMedia;
        this.numSegments = numSegments;
        this.price = price;
        this.priceCurrency = priceCurrency;
        this.direction = direction;
    }

    public static SendSMSResponseBuilder builder() {
        return new SendSMSResponseBuilder();
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getMessage() {
        return this.message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public LocalDateTime getDateSent() {
        return this.dateSent;
    }

    public LocalDateTime getDateCreated() {
        return this.dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return this.dateUpdated;
    }

    public String getUri() {
        return this.uri;
    }

    public Map<String, String> getSubResourceUris() {
        return this.subResourceUris;
    }

    public String getAccountSid() {
        return this.accountSid;
    }

    public String getSid() {
        return this.sid;
    }

    public String getApiVersion() {
        return this.apiVersion;
    }

    public String getBody() {
        return this.body;
    }

    public String getFromEndPoint() {
        return this.fromEndPoint;
    }

    public String getTo() {
        return this.to;
    }

    public String getNumMedia() {
        return this.numMedia;
    }

    public String getNumSegments() {
        return this.numSegments;
    }

    public String getPrice() {
        return this.price;
    }

    public Currency getPriceCurrency() {
        return this.priceCurrency;
    }

    public String getDirection() {
        return this.direction;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setSubResourceUris(Map<String, String> subResourceUris) {
        this.subResourceUris = subResourceUris;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setFromEndPoint(String fromEndPoint) {
        this.fromEndPoint = fromEndPoint;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setNumMedia(String numMedia) {
        this.numMedia = numMedia;
    }

    public void setNumSegments(String numSegments) {
        this.numSegments = numSegments;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPriceCurrency(Currency priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SendSMSResponse)) return false;
        final SendSMSResponse other = (SendSMSResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$mobileNumber = this.getMobileNumber();
        final Object other$mobileNumber = other.getMobileNumber();
        if (this$mobileNumber == null ? other$mobileNumber != null : !this$mobileNumber.equals(other$mobileNumber))
            return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$errorCode = this.getErrorCode();
        final Object other$errorCode = other.getErrorCode();
        if (this$errorCode == null ? other$errorCode != null : !this$errorCode.equals(other$errorCode)) return false;
        final Object this$errorMessage = this.getErrorMessage();
        final Object other$errorMessage = other.getErrorMessage();
        if (this$errorMessage == null ? other$errorMessage != null : !this$errorMessage.equals(other$errorMessage))
            return false;
        final Object this$dateSent = this.getDateSent();
        final Object other$dateSent = other.getDateSent();
        if (this$dateSent == null ? other$dateSent != null : !this$dateSent.equals(other$dateSent)) return false;
        final Object this$dateCreated = this.getDateCreated();
        final Object other$dateCreated = other.getDateCreated();
        if (this$dateCreated == null ? other$dateCreated != null : !this$dateCreated.equals(other$dateCreated))
            return false;
        final Object this$dateUpdated = this.getDateUpdated();
        final Object other$dateUpdated = other.getDateUpdated();
        if (this$dateUpdated == null ? other$dateUpdated != null : !this$dateUpdated.equals(other$dateUpdated))
            return false;
        final Object this$uri = this.getUri();
        final Object other$uri = other.getUri();
        if (this$uri == null ? other$uri != null : !this$uri.equals(other$uri)) return false;
        final Object this$subResourceUris = this.getSubResourceUris();
        final Object other$subResourceUris = other.getSubResourceUris();
        if (this$subResourceUris == null ? other$subResourceUris != null : !this$subResourceUris.equals(other$subResourceUris))
            return false;
        final Object this$accountSid = this.getAccountSid();
        final Object other$accountSid = other.getAccountSid();
        if (this$accountSid == null ? other$accountSid != null : !this$accountSid.equals(other$accountSid))
            return false;
        final Object this$sid = this.getSid();
        final Object other$sid = other.getSid();
        if (this$sid == null ? other$sid != null : !this$sid.equals(other$sid)) return false;
        final Object this$apiVersion = this.getApiVersion();
        final Object other$apiVersion = other.getApiVersion();
        if (this$apiVersion == null ? other$apiVersion != null : !this$apiVersion.equals(other$apiVersion))
            return false;
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        final Object this$fromEndPoint = this.getFromEndPoint();
        final Object other$fromEndPoint = other.getFromEndPoint();
        if (this$fromEndPoint == null ? other$fromEndPoint != null : !this$fromEndPoint.equals(other$fromEndPoint))
            return false;
        final Object this$to = this.getTo();
        final Object other$to = other.getTo();
        if (this$to == null ? other$to != null : !this$to.equals(other$to)) return false;
        final Object this$numMedia = this.getNumMedia();
        final Object other$numMedia = other.getNumMedia();
        if (this$numMedia == null ? other$numMedia != null : !this$numMedia.equals(other$numMedia)) return false;
        final Object this$numSegments = this.getNumSegments();
        final Object other$numSegments = other.getNumSegments();
        if (this$numSegments == null ? other$numSegments != null : !this$numSegments.equals(other$numSegments))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$priceCurrency = this.getPriceCurrency();
        final Object other$priceCurrency = other.getPriceCurrency();
        if (this$priceCurrency == null ? other$priceCurrency != null : !this$priceCurrency.equals(other$priceCurrency))
            return false;
        final Object this$direction = this.getDirection();
        final Object other$direction = other.getDirection();
        if (this$direction == null ? other$direction != null : !this$direction.equals(other$direction)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SendSMSResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mobileNumber = this.getMobileNumber();
        result = result * PRIME + ($mobileNumber == null ? 43 : $mobileNumber.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $errorCode = this.getErrorCode();
        result = result * PRIME + ($errorCode == null ? 43 : $errorCode.hashCode());
        final Object $errorMessage = this.getErrorMessage();
        result = result * PRIME + ($errorMessage == null ? 43 : $errorMessage.hashCode());
        final Object $dateSent = this.getDateSent();
        result = result * PRIME + ($dateSent == null ? 43 : $dateSent.hashCode());
        final Object $dateCreated = this.getDateCreated();
        result = result * PRIME + ($dateCreated == null ? 43 : $dateCreated.hashCode());
        final Object $dateUpdated = this.getDateUpdated();
        result = result * PRIME + ($dateUpdated == null ? 43 : $dateUpdated.hashCode());
        final Object $uri = this.getUri();
        result = result * PRIME + ($uri == null ? 43 : $uri.hashCode());
        final Object $subResourceUris = this.getSubResourceUris();
        result = result * PRIME + ($subResourceUris == null ? 43 : $subResourceUris.hashCode());
        final Object $accountSid = this.getAccountSid();
        result = result * PRIME + ($accountSid == null ? 43 : $accountSid.hashCode());
        final Object $sid = this.getSid();
        result = result * PRIME + ($sid == null ? 43 : $sid.hashCode());
        final Object $apiVersion = this.getApiVersion();
        result = result * PRIME + ($apiVersion == null ? 43 : $apiVersion.hashCode());
        final Object $body = this.getBody();
        result = result * PRIME + ($body == null ? 43 : $body.hashCode());
        final Object $fromEndPoint = this.getFromEndPoint();
        result = result * PRIME + ($fromEndPoint == null ? 43 : $fromEndPoint.hashCode());
        final Object $to = this.getTo();
        result = result * PRIME + ($to == null ? 43 : $to.hashCode());
        final Object $numMedia = this.getNumMedia();
        result = result * PRIME + ($numMedia == null ? 43 : $numMedia.hashCode());
        final Object $numSegments = this.getNumSegments();
        result = result * PRIME + ($numSegments == null ? 43 : $numSegments.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $priceCurrency = this.getPriceCurrency();
        result = result * PRIME + ($priceCurrency == null ? 43 : $priceCurrency.hashCode());
        final Object $direction = this.getDirection();
        result = result * PRIME + ($direction == null ? 43 : $direction.hashCode());
        return result;
    }

    public String toString() {
        return "SendSMSResponse(mobileNumber=" + this.getMobileNumber() + ", message=" + this.getMessage() + ", errorCode=" + this.getErrorCode() + ", errorMessage=" + this.getErrorMessage() + ", dateSent=" + this.getDateSent() + ", dateCreated=" + this.getDateCreated() + ", dateUpdated=" + this.getDateUpdated() + ", uri=" + this.getUri() + ", subResourceUris=" + this.getSubResourceUris() + ", accountSid=" + this.getAccountSid() + ", sid=" + this.getSid() + ", apiVersion=" + this.getApiVersion() + ", body=" + this.getBody() + ", fromEndPoint=" + this.getFromEndPoint() + ", to=" + this.getTo() + ", numMedia=" + this.getNumMedia() + ", numSegments=" + this.getNumSegments() + ", price=" + this.getPrice() + ", priceCurrency=" + this.getPriceCurrency() + ", direction=" + this.getDirection() + ")";
    }

    public static class SendSMSResponseBuilder {
        private String mobileNumber;
        private String message;
        private String errorCode;
        private String errorMessage;
        private LocalDateTime dateSent;
        private LocalDateTime dateCreated;
        private LocalDateTime dateUpdated;
        private String uri;
        private Map<String, String> subResourceUris;
        private String accountSid;
        private String sid;
        private String apiVersion;
        private String body;
        private String fromEndPoint;
        private String to;
        private String numMedia;
        private String numSegments;
        private String price;
        private Currency priceCurrency;
        private String direction;

        SendSMSResponseBuilder() {
        }

        public SendSMSResponse.SendSMSResponseBuilder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder dateSent(LocalDateTime dateSent) {
            this.dateSent = dateSent;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder dateCreated(LocalDateTime dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder dateUpdated(LocalDateTime dateUpdated) {
            this.dateUpdated = dateUpdated;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder subResourceUris(Map<String, String> subResourceUris) {
            this.subResourceUris = subResourceUris;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder accountSid(String accountSid) {
            this.accountSid = accountSid;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder sid(String sid) {
            this.sid = sid;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder apiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder body(String body) {
            this.body = body;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder fromEndPoint(String fromEndPoint) {
            this.fromEndPoint = fromEndPoint;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder to(String to) {
            this.to = to;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder numMedia(String numMedia) {
            this.numMedia = numMedia;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder numSegments(String numSegments) {
            this.numSegments = numSegments;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder price(String price) {
            this.price = price;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder priceCurrency(Currency priceCurrency) {
            this.priceCurrency = priceCurrency;
            return this;
        }

        public SendSMSResponse.SendSMSResponseBuilder direction(String direction) {
            this.direction = direction;
            return this;
        }

        public SendSMSResponse build() {
            return new SendSMSResponse(mobileNumber, message, errorCode, errorMessage, dateSent, dateCreated, dateUpdated, uri, subResourceUris, accountSid, sid, apiVersion, body, fromEndPoint, to, numMedia, numSegments, price, priceCurrency, direction);
        }

        public String toString() {
            return "SendSMSResponse.SendSMSResponseBuilder(mobileNumber=" + this.mobileNumber + ", message=" + this.message + ", errorCode=" + this.errorCode + ", errorMessage=" + this.errorMessage + ", dateSent=" + this.dateSent + ", dateCreated=" + this.dateCreated + ", dateUpdated=" + this.dateUpdated + ", uri=" + this.uri + ", subResourceUris=" + this.subResourceUris + ", accountSid=" + this.accountSid + ", sid=" + this.sid + ", apiVersion=" + this.apiVersion + ", body=" + this.body + ", fromEndPoint=" + this.fromEndPoint + ", to=" + this.to + ", numMedia=" + this.numMedia + ", numSegments=" + this.numSegments + ", price=" + this.price + ", priceCurrency=" + this.priceCurrency + ", direction=" + this.direction + ")";
        }
    }
}
