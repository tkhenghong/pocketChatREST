package com.pocketchat.models.controllers.request.rabbitmq;

public class ListenMessagesFromQueueRequest {
    private String queueName;
    private String exchangeName;
    private String routingKey;
    private String consumerTag;

    // This is an exception commonly happened in Jackson in Spring Boot (https://www.baeldung.com/jackson-exception)
    ListenMessagesFromQueueRequest() {}

    public ListenMessagesFromQueueRequest(String queueName, String exchangeName, String routingKey, String consumerTag) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.consumerTag = consumerTag;
    }

    public static ListenMessagesFromQueueRequestBuilder builder() {
        return new ListenMessagesFromQueueRequestBuilder();
    }

    public String getQueueName() {
        return this.queueName;
    }

    public String getExchangeName() {
        return this.exchangeName;
    }

    public String getRoutingKey() {
        return this.routingKey;
    }

    public String getConsumerTag() {
        return this.consumerTag;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public void setConsumerTag(String consumerTag) {
        this.consumerTag = consumerTag;
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
        final Object this$exchangeName = this.getExchangeName();
        final Object other$exchangeName = other.getExchangeName();
        if (this$exchangeName == null ? other$exchangeName != null : !this$exchangeName.equals(other$exchangeName))
            return false;
        final Object this$routingKey = this.getRoutingKey();
        final Object other$routingKey = other.getRoutingKey();
        if (this$routingKey == null ? other$routingKey != null : !this$routingKey.equals(other$routingKey))
            return false;
        final Object this$consumerTag = this.getConsumerTag();
        final Object other$consumerTag = other.getConsumerTag();
        if (this$consumerTag == null ? other$consumerTag != null : !this$consumerTag.equals(other$consumerTag))
            return false;
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
        final Object $exchangeName = this.getExchangeName();
        result = result * PRIME + ($exchangeName == null ? 43 : $exchangeName.hashCode());
        final Object $routingKey = this.getRoutingKey();
        result = result * PRIME + ($routingKey == null ? 43 : $routingKey.hashCode());
        final Object $consumerTag = this.getConsumerTag();
        result = result * PRIME + ($consumerTag == null ? 43 : $consumerTag.hashCode());
        return result;
    }

    public String toString() {
        return "ListenMessagesFromQueueRequest(queueName=" + this.getQueueName() + ", exchangeName=" + this.getExchangeName() + ", routingKey=" + this.getRoutingKey() + ", consumerTag=" + this.getConsumerTag() + ")";
    }

    public static class ListenMessagesFromQueueRequestBuilder {
        private String queueName;
        private String exchangeName;
        private String routingKey;
        private String consumerTag;

        ListenMessagesFromQueueRequestBuilder() {
        }

        public ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder exchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
            return this;
        }

        public ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder routingKey(String routingKey) {
            this.routingKey = routingKey;
            return this;
        }

        public ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder consumerTag(String consumerTag) {
            this.consumerTag = consumerTag;
            return this;
        }

        public ListenMessagesFromQueueRequest build() {
            return new ListenMessagesFromQueueRequest(queueName, exchangeName, routingKey, consumerTag);
        }

        public String toString() {
            return "ListenMessagesFromQueueRequest.ListenMessagesFromQueueRequestBuilder(queueName=" + this.queueName + ", exchangeName=" + this.exchangeName + ", routingKey=" + this.routingKey + ", consumerTag=" + this.consumerTag + ")";
        }
    }
}
