package com.fraud.detection.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import com.riskplatform.common.entity.Transaction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customer_profiles")
public class CustomerProfile {
    @MongoId
    private String id;
    private String customerId;
    private BigDecimal averageTransactionAmount;
    private List<String> preferredMerchants;
    private List<String> preferredCategories;
    private List<String> highRiskCountries;
    private Boolean fraudHistory;
    private Integer previousAlerts;
    private String accountAge;
    private Instant lastUpdated;
    private List<Transaction> transactionHistory;
    private List<CustomerDevice> deviceHistory;
}