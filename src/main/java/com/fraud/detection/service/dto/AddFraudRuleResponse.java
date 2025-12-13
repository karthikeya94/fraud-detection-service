package com.fraud.detection.service.dto;

public class AddFraudRuleResponse {
    private String ruleId;
    private String status;
    private Integer version;

    public AddFraudRuleResponse() {
    }

    public AddFraudRuleResponse(String ruleId, String status, Integer version) {
        this.ruleId = ruleId;
        this.status = status;
        this.version = version;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static AddFraudRuleResponseBuilder builder() {
        return new AddFraudRuleResponseBuilder();
    }

    public static class AddFraudRuleResponseBuilder {
        private String ruleId;
        private String status;
        private Integer version;

        AddFraudRuleResponseBuilder() {
        }

        public AddFraudRuleResponseBuilder ruleId(String ruleId) {
            this.ruleId = ruleId;
            return this;
        }

        public AddFraudRuleResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        public AddFraudRuleResponseBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public AddFraudRuleResponse build() {
            return new AddFraudRuleResponse(ruleId, status, version);
        }
    }
}