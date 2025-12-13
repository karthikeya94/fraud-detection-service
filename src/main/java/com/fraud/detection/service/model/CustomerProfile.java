package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "customer_profiles")
public class CustomerProfile {

    @Id
    private String id;

    @Field("customerId")
    private String customerId;

    @Field("transactionHistory")
    private List<Transaction> transactionHistory;

    @Field("averageTransactionAmount")
    private BigDecimal averageTransactionAmount;

    @Field("preferredMerchants")
    private List<String> preferredMerchants;

    @Field("preferredCategories")
    private List<String> preferredCategories;

    @Field("highRiskCountries")
    private List<String> highRiskCountries;

    @Field("deviceHistory")
    private List<CustomerDevice> deviceHistory;

    @Field("fraudHistory")
    private Boolean fraudHistory;

    @Field("previousAlerts")
    private Integer previousAlerts;

    @Field("accountAge")
    private String accountAge;

    @Field("lastUpdated")
    private Instant lastUpdated;

    public CustomerProfile() {
    }

    public CustomerProfile(String id, String customerId, List<Transaction> transactionHistory,
            BigDecimal averageTransactionAmount, List<String> preferredMerchants, List<String> preferredCategories,
            List<String> highRiskCountries, List<CustomerDevice> deviceHistory, Boolean fraudHistory,
            Integer previousAlerts, String accountAge, Instant lastUpdated) {
        this.id = id;
        this.customerId = customerId;
        this.transactionHistory = transactionHistory;
        this.averageTransactionAmount = averageTransactionAmount;
        this.preferredMerchants = preferredMerchants;
        this.preferredCategories = preferredCategories;
        this.highRiskCountries = highRiskCountries;
        this.deviceHistory = deviceHistory;
        this.fraudHistory = fraudHistory;
        this.previousAlerts = previousAlerts;
        this.accountAge = accountAge;
        this.lastUpdated = lastUpdated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public BigDecimal getAverageTransactionAmount() {
        return averageTransactionAmount;
    }

    public void setAverageTransactionAmount(BigDecimal averageTransactionAmount) {
        this.averageTransactionAmount = averageTransactionAmount;
    }

    public List<String> getPreferredMerchants() {
        return preferredMerchants;
    }

    public void setPreferredMerchants(List<String> preferredMerchants) {
        this.preferredMerchants = preferredMerchants;
    }

    public List<String> getPreferredCategories() {
        return preferredCategories;
    }

    public void setPreferredCategories(List<String> preferredCategories) {
        this.preferredCategories = preferredCategories;
    }

    public List<String> getHighRiskCountries() {
        return highRiskCountries;
    }

    public void setHighRiskCountries(List<String> highRiskCountries) {
        this.highRiskCountries = highRiskCountries;
    }

    public List<CustomerDevice> getDeviceHistory() {
        return deviceHistory;
    }

    public void setDeviceHistory(List<CustomerDevice> deviceHistory) {
        this.deviceHistory = deviceHistory;
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

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public static CustomerProfileBuilder builder() {
        return new CustomerProfileBuilder();
    }

    public static class CustomerProfileBuilder {
        private String id;
        private String customerId;
        private List<Transaction> transactionHistory;
        private BigDecimal averageTransactionAmount;
        private List<String> preferredMerchants;
        private List<String> preferredCategories;
        private List<String> highRiskCountries;
        private List<CustomerDevice> deviceHistory;
        private Boolean fraudHistory;
        private Integer previousAlerts;
        private String accountAge;
        private Instant lastUpdated;

        CustomerProfileBuilder() {
        }

        public CustomerProfileBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CustomerProfileBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public CustomerProfileBuilder transactionHistory(List<Transaction> transactionHistory) {
            this.transactionHistory = transactionHistory;
            return this;
        }

        public CustomerProfileBuilder averageTransactionAmount(BigDecimal averageTransactionAmount) {
            this.averageTransactionAmount = averageTransactionAmount;
            return this;
        }

        public CustomerProfileBuilder preferredMerchants(List<String> preferredMerchants) {
            this.preferredMerchants = preferredMerchants;
            return this;
        }

        public CustomerProfileBuilder preferredCategories(List<String> preferredCategories) {
            this.preferredCategories = preferredCategories;
            return this;
        }

        public CustomerProfileBuilder highRiskCountries(List<String> highRiskCountries) {
            this.highRiskCountries = highRiskCountries;
            return this;
        }

        public CustomerProfileBuilder deviceHistory(List<CustomerDevice> deviceHistory) {
            this.deviceHistory = deviceHistory;
            return this;
        }

        public CustomerProfileBuilder fraudHistory(Boolean fraudHistory) {
            this.fraudHistory = fraudHistory;
            return this;
        }

        public CustomerProfileBuilder previousAlerts(Integer previousAlerts) {
            this.previousAlerts = previousAlerts;
            return this;
        }

        public CustomerProfileBuilder accountAge(String accountAge) {
            this.accountAge = accountAge;
            return this;
        }

        public CustomerProfileBuilder lastUpdated(Instant lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public CustomerProfile build() {
            return new CustomerProfile(id, customerId, transactionHistory, averageTransactionAmount, preferredMerchants,
                    preferredCategories, highRiskCountries, deviceHistory, fraudHistory, previousAlerts, accountAge,
                    lastUpdated);
        }
    }
}