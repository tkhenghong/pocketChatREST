package com.pocketchat.models.controllers.request.rabbitmq;

public class AddMessageToQueueRequest {
    private String queueName;

    private String exchangeName;

    private String routingKey;

    private String message;

    AddMessageToQueueRequest(String queueName, String exchangeName, String routingKey, String message) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.message = message;
    }

    public static AddMessageToQueueRequestBuilder builder() {
        return new AddMessageToQueueRequestBuilder();
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

    public String getMessage() {
        return this.message;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AddMessageToQueueRequest))
            return false;
        final AddMessageToQueueRequest other = (AddMessageToQueueRequest) o;
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
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AddMessageToQueueRequest;
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
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "AddMessageToQueueRequest(queueName=" + this.getQueueName() + ", exchangeName=" + this.getExchangeName() + ", routingKey=" + this.getRoutingKey() + ", message=" + this.getMessage() + ")";
    }

    public static class AddMessageToQueueRequestBuilder {
        private String queueName;
        private String exchangeName;
        private String routingKey;
        private String message;

        AddMessageToQueueRequestBuilder() {
        }

        public AddMessageToQueueRequest.AddMessageToQueueRequestBuilder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public AddMessageToQueueRequest.AddMessageToQueueRequestBuilder exchangeName(String exchangeName) {
            this.exchangeName = exchangeName;
            return this;
        }

        public AddMessageToQueueRequest.AddMessageToQueueRequestBuilder routingKey(String routingKey) {
            this.routingKey = routingKey;
            return this;
        }

        public AddMessageToQueueRequest.AddMessageToQueueRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public AddMessageToQueueRequest build() {
            return new AddMessageToQueueRequest(queueName, exchangeName, routingKey, message);
        }

        public String toString() {
            return "AddMessageToQueueRequest.AddMessageToQueueRequestBuilder(queueName=" + this.queueName + ", exchangeName=" + this.exchangeName + ", routingKey=" + this.routingKey + ", message=" + this.message + ")";
        }
    }
}
