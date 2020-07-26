package com.pocketchat.server.exceptions;

import java.util.Date;
public class ExceptionResponse {
    private String exceptionName;
    private String path;
    private String message;
    private Date timestamp;
    private String trace;

    ExceptionResponse(String exceptionName, String path, String message, Date timestamp, String trace) {
        this.exceptionName = exceptionName;
        this.path = path;
        this.message = message;
        this.timestamp = timestamp;
        this.trace = trace;
    }

    public static ExceptionResponseBuilder builder() {
        return new ExceptionResponseBuilder();
    }

    public String getExceptionName() {
        return this.exceptionName;
    }

    public String getPath() {
        return this.path;
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getTrace() {
        return this.trace;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ExceptionResponse)) return false;
        final ExceptionResponse other = (ExceptionResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$exceptionName = this.getExceptionName();
        final Object other$exceptionName = other.getExceptionName();
        if (this$exceptionName == null ? other$exceptionName != null : !this$exceptionName.equals(other$exceptionName))
            return false;
        final Object this$path = this.getPath();
        final Object other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        final Object this$trace = this.getTrace();
        final Object other$trace = other.getTrace();
        if (this$trace == null ? other$trace != null : !this$trace.equals(other$trace)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ExceptionResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $exceptionName = this.getExceptionName();
        result = result * PRIME + ($exceptionName == null ? 43 : $exceptionName.hashCode());
        final Object $path = this.getPath();
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        final Object $trace = this.getTrace();
        result = result * PRIME + ($trace == null ? 43 : $trace.hashCode());
        return result;
    }

    public String toString() {
        return "ExceptionResponse(exceptionName=" + this.getExceptionName() + ", path=" + this.getPath() + ", message=" + this.getMessage() + ", timestamp=" + this.getTimestamp() + ", trace=" + this.getTrace() + ")";
    }

    public static class ExceptionResponseBuilder {
        private String exceptionName;
        private String path;
        private String message;
        private Date timestamp;
        private String trace;

        ExceptionResponseBuilder() {
        }

        public ExceptionResponse.ExceptionResponseBuilder exceptionName(String exceptionName) {
            this.exceptionName = exceptionName;
            return this;
        }

        public ExceptionResponse.ExceptionResponseBuilder path(String path) {
            this.path = path;
            return this;
        }

        public ExceptionResponse.ExceptionResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ExceptionResponse.ExceptionResponseBuilder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ExceptionResponse.ExceptionResponseBuilder trace(String trace) {
            this.trace = trace;
            return this;
        }

        public ExceptionResponse build() {
            return new ExceptionResponse(exceptionName, path, message, timestamp, trace);
        }

        public String toString() {
            return "ExceptionResponse.ExceptionResponseBuilder(exceptionName=" + this.exceptionName + ", path=" + this.path + ", message=" + this.message + ", timestamp=" + this.timestamp + ", trace=" + this.trace + ")";
        }
    }
}
