package com.pocketchat.models.controllers.request.rabbitmq;

public class ListenMessagesFromQueueRequest {
    private String queueName;

    // This is an exception commonly happened in Jackson in Spring Boot (https://www.baeldung.com/jackson-exception)
    ListenMessagesFromQueueRequest() {}

    ListenMessagesFromQueueRequest(String queueName) {
        this.queueName = queueName;
    }

    public static ListenMessagesFromQueueRequestBuilder builder() {
        return new ListenMessagesFromQueueRequestBuilder();
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ListenMessagesFromQueueRequest))
            return false;
        final ListenMessagesFromQueueRequest other = (ListenMessagesFromQueueRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$queueName = this.getQueueName();
        final Object other$queueName = other.getQueueName();
        if (this$queueName == null ? other$queueName != null : !this$queueName.equals(other$queueName)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ListenMessagesFromQueueRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $queueName = this.getQueueName();
        result = result * PRIME + ($queueName == null ? 43 : $queueName.hashCode());
        return result;
    }

    public String toString() {
        return "ListenMessagesFromQueueRequest(queueName=" + this.getQueueName() + ")";
    }

    public static class ListenMessagesFromQueueRequestBuilder {
        private String queueName;

        ListenMessagesFromQueueRequestBuilder() {
        }

        public ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public ListenMessagesFromQueueRequest build() {
            return new ListenMessagesFromQueueRequest(queueName);
        }

        public String toString() {
            return "ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder(queueName=" + this.queueName + ")";
        }
    }
}
