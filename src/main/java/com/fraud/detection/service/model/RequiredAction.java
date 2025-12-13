package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class RequiredAction {
    private String type;
    private String channel;
    private Integer expirySeconds;

    public RequiredAction() {
    }

    public RequiredAction(String type, String channel, Integer expirySeconds) {
        this.type = type;
        this.channel = channel;
        this.expirySeconds = expirySeconds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getExpirySeconds() {
        return expirySeconds;
    }

    public void setExpirySeconds(Integer expirySeconds) {
        this.expirySeconds = expirySeconds;
    }

    public static RequiredActionBuilder builder() {
        return new RequiredActionBuilder();
    }

    public static class RequiredActionBuilder {
        private String type;
        private String channel;
        private Integer expirySeconds;

        RequiredActionBuilder() {
        }

        public RequiredActionBuilder type(String type) {
            this.type = type;
            return this;
        }

        public RequiredActionBuilder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public RequiredActionBuilder expirySeconds(Integer expirySeconds) {
            this.expirySeconds = expirySeconds;
            return this;
        }

        public RequiredAction build() {
            return new RequiredAction(type, channel, expirySeconds);
        }
    }
}