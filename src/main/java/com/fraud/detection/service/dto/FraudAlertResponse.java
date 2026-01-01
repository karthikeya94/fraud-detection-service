package com.fraud.detection.service.dto;

import com.riskplatform.common.entity.DetectionResult;

import java.time.Instant;
import java.util.List;

public class FraudAlertResponse {
    private String fraudAlertId;
    private String transactionId;
    private String customerId;
    private String status;
    private Integer overallFraudConfidence;
    private List<DetectionResult> detectionTypes;
    private Instant raisedAt;
    private String raisedBy;
    private String assignedTo;
    private Instant reviewedAt;
    private String actionTaken;
    private Instant createdAt;
    private Instant updatedAt;

    public FraudAlertResponse() {
    }

    public FraudAlertResponse(String fraudAlertId, String transactionId, String customerId, String status,
            Integer overallFraudConfidence, List<DetectionResult> detectionTypes, Instant raisedAt, String raisedBy,
            String assignedTo, Instant reviewedAt, String actionTaken, Instant createdAt, Instant updatedAt) {
        this.fraudAlertId = fraudAlertId;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.status = status;
        this.overallFraudConfidence = overallFraudConfidence;
        this.detectionTypes = detectionTypes;
        this.raisedAt = raisedAt;
        this.raisedBy = raisedBy;
        this.assignedTo = assignedTo;
        this.reviewedAt = reviewedAt;
        this.actionTaken = actionTaken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getFraudAlertId() {
        return fraudAlertId;
    }

    public void setFraudAlertId(String fraudAlertId) {
        this.fraudAlertId = fraudAlertId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOverallFraudConfidence() {
        return overallFraudConfidence;
    }

    public void setOverallFraudConfidence(Integer overallFraudConfidence) {
        this.overallFraudConfidence = overallFraudConfidence;
    }

    public List<DetectionResult> getDetectionTypes() {
        return detectionTypes;
    }

    public void setDetectionTypes(List<DetectionResult> detectionTypes) {
        this.detectionTypes = detectionTypes;
    }

    public Instant getRaisedAt() {
        return raisedAt;
    }

    public void setRaisedAt(Instant raisedAt) {
        this.raisedAt = raisedAt;
    }

    public String getRaisedBy() {
        return raisedBy;
    }

    public void setRaisedBy(String raisedBy) {
        this.raisedBy = raisedBy;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Instant getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(Instant reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public void setActionTaken(String actionTaken) {
        this.actionTaken = actionTaken;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static FraudAlertResponseBuilder builder() {
        return new FraudAlertResponseBuilder();
    }

    public static class FraudAlertResponseBuilder {
        private String fraudAlertId;
        private String transactionId;
        private String customerId;
        private String status;
        private Integer overallFraudConfidence;
        private List<DetectionResult> detectionTypes;
        private Instant raisedAt;
        private String raisedBy;
        private String assignedTo;
        private Instant reviewedAt;
        private String actionTaken;
        private Instant createdAt;
        private Instant updatedAt;

        FraudAlertResponseBuilder() {
        }

        public FraudAlertResponseBuilder fraudAlertId(String fraudAlertId) {
            this.fraudAlertId = fraudAlertId;
            return this;
        }

        public FraudAlertResponseBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public FraudAlertResponseBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public FraudAlertResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public FraudAlertResponseBuilder overallFraudConfidence(Integer overallFraudConfidence) {
            this.overallFraudConfidence = overallFraudConfidence;
            return this;
        }

        public FraudAlertResponseBuilder detectionTypes(List<DetectionResult> detectionTypes) {
            this.detectionTypes = detectionTypes;
            return this;
        }

        public FraudAlertResponseBuilder raisedAt(Instant raisedAt) {
            this.raisedAt = raisedAt;
            return this;
        }

        public FraudAlertResponseBuilder raisedBy(String raisedBy) {
            this.raisedBy = raisedBy;
            return this;
        }

        public FraudAlertResponseBuilder assignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
            return this;
        }

        public FraudAlertResponseBuilder reviewedAt(Instant reviewedAt) {
            this.reviewedAt = reviewedAt;
            return this;
        }

        public FraudAlertResponseBuilder actionTaken(String actionTaken) {
            this.actionTaken = actionTaken;
            return this;
        }

        public FraudAlertResponseBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FraudAlertResponseBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public FraudAlertResponse build() {
            return new FraudAlertResponse(fraudAlertId, transactionId, customerId, status, overallFraudConfidence,
                    detectionTypes, raisedAt, raisedBy, assignedTo, reviewedAt, actionTaken, createdAt, updatedAt);
        }
    }
}