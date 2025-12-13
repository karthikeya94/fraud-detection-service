package com.fraud.detection.service.model;

import com.fraud.detection.service.model.enums.FraudType;
import com.fraud.detection.service.model.enums.ActionType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Map;

public class DetectionResult {
    private FraudType type;
    private Integer confidence;
    private String status;
    private ActionType action;
    private String reason;
    private Map<String, Object> details;

    public DetectionResult() {
    }

    public DetectionResult(FraudType type, Integer confidence, String status, ActionType action, String reason,
            Map<String, Object> details) {
        this.type = type;
        this.confidence = confidence;
        this.status = status;
        this.action = action;
        this.reason = reason;
        this.details = details;
    }

    public FraudType getType() {
        return type;
    }

    public void setType(FraudType type) {
        this.type = type;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public static DetectionResultBuilder builder() {
        return new DetectionResultBuilder();
    }

    public static class DetectionResultBuilder {
        private FraudType type;
        private Integer confidence;
        private String status;
        private ActionType action;
        private String reason;
        private Map<String, Object> details;

        DetectionResultBuilder() {
        }

        public DetectionResultBuilder type(FraudType type) {
            this.type = type;
            return this;
        }

        public DetectionResultBuilder confidence(Integer confidence) {
            this.confidence = confidence;
            return this;
        }

        public DetectionResultBuilder status(String status) {
            this.status = status;
            return this;
        }

        public DetectionResultBuilder action(ActionType action) {
            this.action = action;
            return this;
        }

        public DetectionResultBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public DetectionResultBuilder details(Map<String, Object> details) {
            this.details = details;
            return this;
        }

        public DetectionResult build() {
            return new DetectionResult(type, confidence, status, action, reason, details);
        }
    }
}