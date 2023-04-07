package com.challenge.b4a.resources.exceptions;

import java.io.Serializable;

public class StandardError implements Serializable {

    private long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    StandardError() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static final class Builder {
        private long timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder withError(String error) {
            this.error = error;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public StandardError build() {
            StandardError standardError = new StandardError();
            standardError.setTimestamp(timestamp);
            standardError.setStatus(status);
            standardError.setError(error);
            standardError.setMessage(message);
            standardError.setPath(path);
            return standardError;
        }
    }
}
