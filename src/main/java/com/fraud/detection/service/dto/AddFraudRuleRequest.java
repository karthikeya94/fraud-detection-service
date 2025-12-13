package com.fraud.detection.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.Instant;

public class AddFraudRuleRequest {
    @NotBlank(message = "Rule name is required")
    private String ruleName;

    @NotBlank(message = "Rule type is required")
    private String ruleType;

    @NotBlank(message = "Condition is required")
    private String condition;

    @NotBlank(message = "Action is required")
    private String action;

    @NotNull(message = "Confidence is required")
    @Min(value = 0, message = "Confidence must be between 0 and 100")
    @Max(value = 100, message = "Confidence must be between 0 and 100")
    private Integer confidence;

    @NotNull(message = "Enabled flag is required")
    private Boolean enabled;

    @NotNull(message = "Effective date is required")
    private Instant effectiveDate;

    public AddFraudRuleRequest() {
    }

    public AddFraudRuleRequest(String ruleName, String ruleType, String condition, String action, Integer confidence,
            Boolean enabled, Instant effectiveDate) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.condition = condition;
        this.action = action;
        this.confidence = confidence;
        this.enabled = enabled;
        this.effectiveDate = effectiveDate;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public static AddFraudRuleRequestBuilder builder() {
        return new AddFraudRuleRequestBuilder();
    }

    public static class AddFraudRuleRequestBuilder {
        private String ruleName;
        private String ruleType;
        private String condition;
        private String action;
        private Integer confidence;
        private Boolean enabled;
        private Instant effectiveDate;

        AddFraudRuleRequestBuilder() {
        }

        public AddFraudRuleRequestBuilder ruleName(String ruleName) {
            this.ruleName = ruleName;
            return this;
        }

        public AddFraudRuleRequestBuilder ruleType(String ruleType) {
            this.ruleType = ruleType;
            return this;
        }

        public AddFraudRuleRequestBuilder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public AddFraudRuleRequestBuilder action(String action) {
            this.action = action;
            return this;
        }

        public AddFraudRuleRequestBuilder confidence(Integer confidence) {
            this.confidence = confidence;
            return this;
        }

        public AddFraudRuleRequestBuilder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public AddFraudRuleRequestBuilder effectiveDate(Instant effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public AddFraudRuleRequest build() {
            return new AddFraudRuleRequest(ruleName, ruleType, condition, action, confidence, enabled, effectiveDate);
        }
    }
}