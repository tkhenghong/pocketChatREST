package com.pocketchat.models.controllers.request.rabbitmq;

public class AddMessageToQueueRequest {
    private String queueName;

    private String message;

    AddMessageToQueueRequest(String queueName, String message) {
        this.queueName = queueName;
        this.message = message;
    }

    public static AddMessageToQueueRequestBuilder builder() {
        return new AddMessageToQueueRequestBuilder();
    }

    public String getQueueName() {
        return this.queueName;
    }

    public String getMessage() {
        return this.message;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
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
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "AddMessageToQueueRequest(queueName=" + this.getQueueName() + ", message=" + this.getMessage() + ")";
    }

    public static class AddMessageToQueueRequestBuilder {
        private String queueName;
        private String message;

        AddMessageToQueueRequestBuilder() {
        }

        public AddMessageToQueueRequest.AddMessageToQueueRequestBuilder queueName(String queueName) {
            this.queueName = queueName;
            return this;
        }

        public AddMessageToQueueRequest.AddMessageToQueueRequestBuilder message(String message) {
            this.message = message;
            return this;
        }

        public AddMessageToQueueRequest build() {
            return new AddMessageToQueueRequest(queueName, message);
        }

        public String toString() {
            return "AddMessageToQueueRequest.AddMessageToQueueRequestBuilder(queueName=" + this.queueName + ", message=" + this.message + ")";
        }
    }
}
