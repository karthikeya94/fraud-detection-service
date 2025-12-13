package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.Instant;

public class Resolution {
    private String action;
    private String reason;
    private String resolvedBy;
    private Instant resolvedAt;

    public Resolution() {
    }

    public Resolution(String action, String reason, String resolvedBy, Instant resolvedAt) {
        this.action = action;
        this.reason = reason;
        this.resolvedBy = resolvedBy;
        this.resolvedAt = resolvedAt;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Instant resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public static ResolutionBuilder builder() {
        return new ResolutionBuilder();
    }

    public static class ResolutionBuilder {
        private String action;
        private String reason;
        private String resolvedBy;
        private Instant resolvedAt;

        ResolutionBuilder() {
        }

        public ResolutionBuilder action(String action) {
            this.action = action;
            return this;
        }

        public ResolutionBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public ResolutionBuilder resolvedBy(String resolvedBy) {
            this.resolvedBy = resolvedBy;
            return this;
        }

        public ResolutionBuilder resolvedAt(Instant resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public Resolution build() {
            return new Resolution(action, reason, resolvedBy, resolvedAt);
        }
    }
}