package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

public class FraudAnalysisResult {
    private String transactionId;
    private String fraudAlertId;
    private Integer overallFraudConfidence;
    private String status;
    private String recommendedAction;
    private List<DetectionResult> detectionTypes;
    private List<String> fraudFlags;
    private CustomerRiskContext customerRiskContext;
    private RequiredAction requiredAction;

    public FraudAnalysisResult() {
    }

    public FraudAnalysisResult(String transactionId, String fraudAlertId, Integer overallFraudConfidence, String status,
            String recommendedAction, List<DetectionResult> detectionTypes, List<String> fraudFlags,
            CustomerRiskContext customerRiskContext, RequiredAction requiredAction) {
        this.transactionId = transactionId;
        this.fraudAlertId = fraudAlertId;
        this.overallFraudConfidence = overallFraudConfidence;
        this.status = status;
        this.recommendedAction = recommendedAction;
        this.detectionTypes = detectionTypes;
        this.fraudFlags = fraudFlags;
        this.customerRiskContext = customerRiskContext;
        this.requiredAction = requiredAction;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFraudAlertId() {
        return fraudAlertId;
    }

    public void setFraudAlertId(String fraudAlertId) {
        this.fraudAlertId = fraudAlertId;
    }

    public Integer getOverallFraudConfidence() {
        return overallFraudConfidence;
    }

    public void setOverallFraudConfidence(Integer overallFraudConfidence) {
        this.overallFraudConfidence = overallFraudConfidence;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
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

    public CustomerRiskContext getCustomerRiskContext() {
        return customerRiskContext;
    }

    public void setCustomerRiskContext(CustomerRiskContext customerRiskContext) {
        this.customerRiskContext = customerRiskContext;
    }

    public RequiredAction getRequiredAction() {
        return requiredAction;
    }

    public void setRequiredAction(RequiredAction requiredAction) {
        this.requiredAction = requiredAction;
    }

    public static FraudAnalysisResultBuilder builder() {
        return new FraudAnalysisResultBuilder();
    }

    public static class FraudAnalysisResultBuilder {
        private String transactionId;
        private String fraudAlertId;
        private Integer overallFraudConfidence;
        private String status;
        private String recommendedAction;
        private List<DetectionResult> detectionTypes;
        private List<String> fraudFlags;
        private CustomerRiskContext customerRiskContext;
        private RequiredAction requiredAction;

        FraudAnalysisResultBuilder() {
        }

        public FraudAnalysisResultBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public FraudAnalysisResultBuilder fraudAlertId(String fraudAlertId) {
            this.fraudAlertId = fraudAlertId;
            return this;
        }

        public FraudAnalysisResultBuilder overallFraudConfidence(Integer overallFraudConfidence) {
            this.overallFraudConfidence = overallFraudConfidence;
            return this;
        }

        public FraudAnalysisResultBuilder status(String status) {
            this.status = status;
            return this;
        }

        public FraudAnalysisResultBuilder recommendedAction(String recommendedAction) {
            this.recommendedAction = recommendedAction;
            return this;
        }

        public FraudAnalysisResultBuilder detectionTypes(List<DetectionResult> detectionTypes) {
            this.detectionTypes = detectionTypes;
            return this;
        }

        public FraudAnalysisResultBuilder fraudFlags(List<String> fraudFlags) {
            this.fraudFlags = fraudFlags;
            return this;
        }

        public FraudAnalysisResultBuilder customerRiskContext(CustomerRiskContext customerRiskContext) {
            this.customerRiskContext = customerRiskContext;
            return this;
        }

        public FraudAnalysisResultBuilder requiredAction(RequiredAction requiredAction) {
            this.requiredAction = requiredAction;
            return this;
        }

        public FraudAnalysisResult build() {
            return new FraudAnalysisResult(transactionId, fraudAlertId, overallFraudConfidence, status,
                    recommendedAction, detectionTypes, fraudFlags, customerRiskContext, requiredAction);
        }
    }
}