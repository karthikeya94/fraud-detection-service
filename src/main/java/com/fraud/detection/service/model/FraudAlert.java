package com.fraud.detection.service.model;

import com.fraud.detection.service.model.enums.AlertStatus;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.Instant;
import java.util.List;

@Document(collection = "fraud_alerts")
public class FraudAlert {

    @Id
    private String id;

    @Field("transactionId")
    private String transactionId;

    @Field("customerId")
    private String customerId;

    @Field("overallFraudConfidence")
    private Integer overallFraudConfidence;

    @Field("status")
    private AlertStatus status;

    @Field("detectionTypes")
    private List<DetectionResult> detectionTypes;

    @Field("fraudFlags")
    private List<String> fraudFlags;

    @Field("raisedAt")
    private Instant raisedAt;

    @Field("raisedBy")
    private String raisedBy;

    @Field("assignedTo")
    private String assignedTo;

    @Field("reviewedAt")
    private Instant reviewedAt;

    @Field("resolution")
    private Resolution resolution;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedAt")
    private Instant updatedAt;

    public FraudAlert() {
    }

    public FraudAlert(String id, String transactionId, String customerId, Integer overallFraudConfidence,
            AlertStatus status, List<DetectionResult> detectionTypes, List<String> fraudFlags, Instant raisedAt,
            String raisedBy, String assignedTo, Instant reviewedAt, Resolution resolution, Instant createdAt,
            Instant updatedAt) {
        this.id = id;
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.overallFraudConfidence = overallFraudConfidence;
        this.status = status;
        this.detectionTypes = detectionTypes;
        this.fraudFlags = fraudFlags;
        this.raisedAt = raisedAt;
        this.raisedBy = raisedBy;
        this.assignedTo = assignedTo;
        this.reviewedAt = reviewedAt;
        this.resolution = resolution;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getOverallFraudConfidence() {
        return overallFraudConfidence;
    }

    public void setOverallFraudConfidence(Integer overallFraudConfidence) {
        this.overallFraudConfidence = overallFraudConfidence;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public List<DetectionResult> getDetectionTypes() {
        return detectionTypes;
    }

    public void setDetectionTypes(List<DetectionResult> detectionTypes) {
        this.detectionTypes = detectionTypes;
    }

    public List<String> getFraudFlags() {
        return fraudFlags;
    }

    public void setFraudFlags(List<String> fraudFlags) {
        this.fraudFlags = fraudFlags;
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

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
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

    public static FraudAlertBuilder builder() {
        return new FraudAlertBuilder();
    }

    public static class FraudAlertBuilder {
        private String id;
        private String transactionId;
        private String customerId;
        private Integer overallFraudConfidence;
        private AlertStatus status;
        private List<DetectionResult> detectionTypes;
        private List<String> fraudFlags;
        private Instant raisedAt;
        private String raisedBy;
        private String assignedTo;
        private Instant reviewedAt;
        private Resolution resolution;
        private Instant createdAt;
        private Instant updatedAt;

        FraudAlertBuilder() {
        }

        public FraudAlertBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FraudAlertBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public FraudAlertBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public FraudAlertBuilder overallFraudConfidence(Integer overallFraudConfidence) {
            this.overallFraudConfidence = overallFraudConfidence;
            return this;
        }

        public FraudAlertBuilder status(AlertStatus status) {
            this.status = status;
            return this;
        }

        public FraudAlertBuilder detectionTypes(List<DetectionResult> detectionTypes) {
            this.detectionTypes = detectionTypes;
            return this;
        }

        public FraudAlertBuilder fraudFlags(List<String> fraudFlags) {
            this.fraudFlags = fraudFlags;
            return this;
        }

        public FraudAlertBuilder raisedAt(Instant raisedAt) {
            this.raisedAt = raisedAt;
            return this;
        }

        public FraudAlertBuilder raisedBy(String raisedBy) {
            this.raisedBy = raisedBy;
            return this;
        }

        public FraudAlertBuilder assignedTo(String assignedTo) {
            this.assignedTo = assignedTo;
            return this;
        }

        public FraudAlertBuilder reviewedAt(Instant reviewedAt) {
            this.reviewedAt = reviewedAt;
            return this;
        }

        public FraudAlertBuilder resolution(Resolution resolution) {
            this.resolution = resolution;
            return this;
        }

        public FraudAlertBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FraudAlertBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public FraudAlert build() {
            return new FraudAlert(id, transactionId, customerId, overallFraudConfidence, status, detectionTypes,
                    fraudFlags, raisedAt, raisedBy, assignedTo, reviewedAt, resolution, createdAt, updatedAt);
        }
    }
}