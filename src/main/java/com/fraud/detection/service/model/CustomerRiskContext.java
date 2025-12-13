package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class CustomerRiskContext {
    private Boolean fraudHistory;
    private Integer previousAlerts;
    private String accountAge;

    public CustomerRiskContext() {
    }

    public CustomerRiskContext(Boolean fraudHistory, Integer previousAlerts, String accountAge) {
        this.fraudHistory = fraudHistory;
        this.previousAlerts = previousAlerts;
        this.accountAge = accountAge;
    }

    public Boolean getFraudHistory() {
        return fraudHistory;
    }

    public void setFraudHistory(Boolean fraudHistory) {
        this.fraudHistory = fraudHistory;
    }

    public Integer getPreviousAlerts() {
        return previousAlerts;
    }

    public void setPreviousAlerts(Integer previousAlerts) {
        this.previousAlerts = previousAlerts;
    }

    public String getAccountAge() {
        return accountAge;
    }

    public void setAccountAge(String accountAge) {
        this.accountAge = accountAge;
    }

    public static CustomerRiskContextBuilder builder() {
        return new CustomerRiskContextBuilder();
    }

    public static class CustomerRiskContextBuilder {
        private Boolean fraudHistory;
        private Integer previousAlerts;
        private String accountAge;

        CustomerRiskContextBuilder() {
        }

        public CustomerRiskContextBuilder fraudHistory(Boolean fraudHistory) {
            this.fraudHistory = fraudHistory;
            return this;
        }

        public CustomerRiskContextBuilder previousAlerts(Integer previousAlerts) {
            this.previousAlerts = previousAlerts;
            return this;
        }

        public CustomerRiskContextBuilder accountAge(String accountAge) {
            this.accountAge = accountAge;
            return this;
        }

        public CustomerRiskContext build() {
            return new CustomerRiskContext(fraudHistory, previousAlerts, accountAge);
        }
    }
}