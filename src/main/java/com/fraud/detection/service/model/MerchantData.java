package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.Instant;
import java.util.List;

@Document(collection = "merchant_data")
public class MerchantData {

    @Id
    private String id;

    @Field("merchantId")
    private String merchantId;

    @Field("merchantName")
    private String merchantName;

    @Field("category")
    private String category;

    @Field("riskLevel")
    private String riskLevel;

    @Field("countriesOperated")
    private List<String> countriesOperated;

    @Field("highRisk")
    private Boolean highRisk;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedAt")
    private Instant updatedAt;

    public MerchantData() {
    }

    public MerchantData(String id, String merchantId, String merchantName, String category, String riskLevel,
            List<String> countriesOperated, Boolean highRisk, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.category = category;
        this.riskLevel = riskLevel;
        this.countriesOperated = countriesOperated;
        this.highRisk = highRisk;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<String> getCountriesOperated() {
        return countriesOperated;
    }

    public void setCountriesOperated(List<String> countriesOperated) {
        this.countriesOperated = countriesOperated;
    }

    public Boolean getHighRisk() {
        return highRisk;
    }

    public void setHighRisk(Boolean highRisk) {
        this.highRisk = highRisk;
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

    public static MerchantDataBuilder builder() {
        return new MerchantDataBuilder();
    }

    public static class MerchantDataBuilder {
        private String id;
        private String merchantId;
        private String merchantName;
        private String category;
        private String riskLevel;
        private List<String> countriesOperated;
        private Boolean highRisk;
        private Instant createdAt;
        private Instant updatedAt;

        MerchantDataBuilder() {
        }

        public MerchantDataBuilder id(String id) {
            this.id = id;
            return this;
        }

        public MerchantDataBuilder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public MerchantDataBuilder merchantName(String merchantName) {
            this.merchantName = merchantName;
            return this;
        }

        public MerchantDataBuilder category(String category) {
            this.category = category;
            return this;
        }

        public MerchantDataBuilder riskLevel(String riskLevel) {
            this.riskLevel = riskLevel;
            return this;
        }

        public MerchantDataBuilder countriesOperated(List<String> countriesOperated) {
            this.countriesOperated = countriesOperated;
            return this;
        }

        public MerchantDataBuilder highRisk(Boolean highRisk) {
            this.highRisk = highRisk;
            return this;
        }

        public MerchantDataBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public MerchantDataBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public MerchantData build() {
            return new MerchantData(id, merchantId, merchantName, category, riskLevel, countriesOperated, highRisk,
                    createdAt, updatedAt);
        }
    }
}