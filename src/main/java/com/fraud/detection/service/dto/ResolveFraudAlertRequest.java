package com.fraud.detection.service.dto;

import jakarta.validation.constraints.NotBlank;

public class ResolveFraudAlertRequest {
    @NotBlank(message = "Action is required")
    private String action;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String nextStep;
    private String notes;

    public ResolveFraudAlertRequest() {
    }

    public ResolveFraudAlertRequest(String action, String reason, String nextStep, String notes) {
        this.action = action;
        this.reason = reason;
        this.nextStep = nextStep;
        this.notes = notes;
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

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static ResolveFraudAlertRequestBuilder builder() {
        return new ResolveFraudAlertRequestBuilder();
    }

    public static class ResolveFraudAlertRequestBuilder {
        private String action;
        private String reason;
        private String nextStep;
        private String notes;

        ResolveFraudAlertRequestBuilder() {
        }

        public ResolveFraudAlertRequestBuilder action(String action) {
            this.action = action;
            return this;
        }

        public ResolveFraudAlertRequestBuilder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public ResolveFraudAlertRequestBuilder nextStep(String nextStep) {
            this.nextStep = nextStep;
            return this;
        }

        public ResolveFraudAlertRequestBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public ResolveFraudAlertRequest build() {
            return new ResolveFraudAlertRequest(action, reason, nextStep, notes);
        }
    }
}