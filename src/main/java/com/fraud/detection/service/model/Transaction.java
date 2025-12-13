package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;

public class Transaction {
    private String id;
    private String customerId;
    private BigDecimal amount;
    private String merchant;
    private String merchantCategory;
    private Instant timestamp;
    private LocationInfo location;
    private DeviceInfo device;

    public Transaction() {
    }

    public Transaction(String id, String customerId, BigDecimal amount, String merchant, String merchantCategory,
            Instant timestamp, LocationInfo location, DeviceInfo device) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.merchant = merchant;
        this.merchantCategory = merchantCategory;
        this.timestamp = timestamp;
        this.location = location;
        this.device = device;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public DeviceInfo getDevice() {
        return device;
    }

    public void setDevice(DeviceInfo device) {
        this.device = device;
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public static class TransactionBuilder {
        private String id;
        private String customerId;
        private BigDecimal amount;
        private String merchant;
        private String merchantCategory;
        private Instant timestamp;
        private LocationInfo location;
        private DeviceInfo device;

        TransactionBuilder() {
        }

        public TransactionBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TransactionBuilder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public TransactionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder merchant(String merchant) {
            this.merchant = merchant;
            return this;
        }

        public TransactionBuilder merchantCategory(String merchantCategory) {
            this.merchantCategory = merchantCategory;
            return this;
        }

        public TransactionBuilder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionBuilder location(LocationInfo location) {
            this.location = location;
            return this;
        }

        public TransactionBuilder device(DeviceInfo device) {
            this.device = device;
            return this;
        }

        public Transaction build() {
            return new Transaction(id, customerId, amount, merchant, merchantCategory, timestamp, location, device);
        }
    }
}