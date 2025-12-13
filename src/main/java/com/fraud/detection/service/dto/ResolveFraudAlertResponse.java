package com.fraud.detection.service.dto;

import java.time.Instant;

public class ResolveFraudAlertResponse {
    private String fraudAlertId;
    private String status;
    private String action;
    private Instant resolvedAt;
    private String resolvedBy;
    private String refundStatus;
    private String cardBlockStatus;
    private String message;

    public ResolveFraudAlertResponse() {
    }

    public ResolveFraudAlertResponse(String fraudAlertId, String status, String action, Instant resolvedAt,
            String resolvedBy, String refundStatus, String cardBlockStatus, String message) {
        this.fraudAlertId = fraudAlertId;
        this.status = status;
        this.action = action;
        this.resolvedAt = resolvedAt;
        this.resolvedBy = resolvedBy;
        this.refundStatus = refundStatus;
        this.cardBlockStatus = cardBlockStatus;
        this.message = message;
    }

    public String getFraudAlertId() {
        return fraudAlertId;
    }

    public void setFraudAlertId(String fraudAlertId) {
        this.fraudAlertId = fraudAlertId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Instant resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getCardBlockStatus() {
        return cardBlockStatus;
    }

    public void setCardBlockStatus(String cardBlockStatus) {
        this.cardBlockStatus = cardBlockStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResolveFraudAlertResponseBuilder builder() {
        return new ResolveFraudAlertResponseBuilder();
    }

    public static class ResolveFraudAlertResponseBuilder {
        private String fraudAlertId;
        private String status;
        private String action;
        private Instant resolvedAt;
        private String resolvedBy;
        private String refundStatus;
        private String cardBlockStatus;
        private String message;

        ResolveFraudAlertResponseBuilder() {
        }

        public ResolveFraudAlertResponseBuilder fraudAlertId(String fraudAlertId) {
            this.fraudAlertId = fraudAlertId;
            return this;
        }

        public ResolveFraudAlertResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public ResolveFraudAlertResponseBuilder action(String action) {
            this.action = action;
            return this;
        }

        public ResolveFraudAlertResponseBuilder resolvedAt(Instant resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }

        public ResolveFraudAlertResponseBuilder resolvedBy(String resolvedBy) {
            this.resolvedBy = resolvedBy;
            return this;
        }

        public ResolveFraudAlertResponseBuilder refundStatus(String refundStatus) {
            this.refundStatus = refundStatus;
            return this;
        }

        public ResolveFraudAlertResponseBuilder cardBlockStatus(String cardBlockStatus) {
            this.cardBlockStatus = cardBlockStatus;
            return this;
        }

        public ResolveFraudAlertResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResolveFraudAlertResponse build() {
            return new ResolveFraudAlertResponse(fraudAlertId, status, action, resolvedAt, resolvedBy, refundStatus,
                    cardBlockStatus, message);
        }
    }
}