package com.fraud.detection.service.config;

import com.fraud.detection.service.model.*;
import com.fraud.detection.service.model.enums.AlertStatus;
import com.fraud.detection.service.model.enums.FraudType;
import com.fraud.detection.service.model.enums.ActionType;
import com.fraud.detection.service.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

//@Component
public class DemoDataInitializer {//implements CommandLineRunner {

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private FraudAlertRepository fraudAlertRepository;

    @Autowired
    private FraudRuleRepository fraudRuleRepository;

    @Autowired
    private FraudPatternRepository fraudPatternRepository;

    @Autowired
    private MerchantDataRepository merchantDataRepository;

//    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (customerProfileRepository.count() == 0) {
            initializeDemoData();
        }
    }

    private void initializeDemoData() {
        System.out.println("Initializing demo data...");

        // Create merchant data first as it's referenced by other entities
        createMerchantData();

        // Create customer profiles
        createCustomerProfiles();

        // Create fraud alerts
        createFraudAlerts();

        // Create fraud rules
        createFraudRules();

        // Create fraud patterns
        createFraudPatterns();

        System.out.println("Demo data initialization completed.");
    }

    private void createMerchantData() {
        List<MerchantData> merchants = Arrays.asList(
            MerchantData.builder()
                .merchantId("MCH-AMAZON")
                .merchantName("Amazon")
                .category("ELECTRONICS")
                .riskLevel("LOW")
                .countriesOperated(Arrays.asList("US", "CA", "GB", "IN"))
                .highRisk(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build(),
            MerchantData.builder()
                .merchantId("MCH-EBAY")
                .merchantName("eBay")
                .category("ONLINE_MARKETPLACE")
                .riskLevel("MEDIUM")
                .countriesOperated(Arrays.asList("US", "CA", "GB", "AU"))
                .highRisk(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build(),
            MerchantData.builder()
                .merchantId("MCH-CRYPTOEXCHANGE")
                .merchantName("Crypto Exchange")
                .category("CRYPTOCURRENCY")
                .riskLevel("HIGH")
                .countriesOperated(Arrays.asList("US", "SG", "CH"))
                .highRisk(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build(),
            MerchantData.builder()
                .merchantId("MCH-GAMBLING")
                .merchantName("Online Casino")
                .category("GAMBLING")
                .riskLevel("HIGH")
                .countriesOperated(Arrays.asList("CR", "PA", "AN"))
                .highRisk(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build()
        );

        merchantDataRepository.saveAll(merchants);
        System.out.println("Created " + merchants.size() + " merchant records.");
    }

    private void createCustomerProfiles() {
        List<CustomerProfile> profiles = Arrays.asList(
            CustomerProfile.builder()
                .customerId("CUST-001")
//                .firstName("John")
//                .lastName("Doe")
//                .email("john.doe@example.com")
//                .phone("+1-555-0101")
//                .address("123 Main St, New York, NY 10001")
//                .dateOfBirth("1985-06-15")
//                .accountCreationDate(Instant.now().minus(365, ChronoUnit.DAYS))
//                .kycStatus("VERIFIED")
//                .riskScore(30)
//                .totalTransactions(45)
//                .totalTransactionAmount(BigDecimal.valueOf(12500.00))
//                .preferredCategories(Arrays.asList("ELECTRONICS", "BOOKS"))
                .preferredMerchants(Arrays.asList("Amazon", "Best Buy"))
                .highRiskCountries(Arrays.asList("NG", "RU"))
                .fraudHistory(false)
                .previousAlerts(0)
                .accountAge("1 year")
                .lastUpdated(Instant.now())
                .build(),
            CustomerProfile.builder()
                .customerId("CUST-002")
//                .firstName("Jane")
//                .lastName("Smith")
//                .email("jane.smith@example.com")
//                .phone("+1-555-0102")
//                .address("456 Oak Ave, Los Angeles, CA 90210")
//                .dateOfBirth("1990-03-22")
//                .accountCreationDate(Instant.now().minus(180, ChronoUnit.DAYS))
//                .kycStatus("VERIFIED")
//                .riskScore(75)
//                .totalTransactions(22)
//                .totalTransactionAmount(BigDecimal.valueOf(8750.50))
                .preferredCategories(Arrays.asList("FASHION", "TRAVEL"))
                .preferredMerchants(Arrays.asList("eBay", "Expedia"))
                .highRiskCountries(Arrays.asList("KP", "IR"))
                .fraudHistory(true)
                .previousAlerts(2)
                .accountAge("6 months")
                .lastUpdated(Instant.now())
                .build(),
            CustomerProfile.builder()
                .customerId("CUST-003")
//                .firstName("Robert")
//                .lastName("Johnson")
//                .email("robert.johnson@example.com")
//                .phone("+1-555-0103")
//                .address("789 Pine Rd, Chicago, IL 60601")
//                .dateOfBirth("1982-11-08")
//                .accountCreationDate(Instant.now().minus(730, ChronoUnit.DAYS))
//                .kycStatus("VERIFIED")
//                .riskScore(20)
//                .totalTransactions(120)
//                .totalTransactionAmount(BigDecimal.valueOf(32000.75))
                .preferredCategories(Arrays.asList("HOME_IMPROVEMENT", "AUTOMOTIVE"))
                .preferredMerchants(Arrays.asList("Home Depot", "AutoZone"))
                .highRiskCountries(Arrays.asList("SY", "SO"))
                .fraudHistory(false)
                .previousAlerts(0)
                .accountAge("2 years")
                .lastUpdated(Instant.now())
                .build()
        );

        customerProfileRepository.saveAll(profiles);
        System.out.println("Created " + profiles.size() + " customer profiles.");
    }

    private void createFraudAlerts() {
        List<FraudAlert> alerts = Arrays.asList(
            FraudAlert.builder()
                .transactionId("TXN-1001")
                .customerId("CUST-002")
                .overallFraudConfidence(85)
                .status(AlertStatus.REVIEW_PENDING)
                .detectionTypes(Arrays.asList(
                    DetectionResult.builder()
                        .type(FraudType.VELOCITY_FRAUD)
                        .confidence(75)
                        .status("CONFIRMED")
                        .action(ActionType.MANUAL_REVIEW)
                        .reason("Unusual transaction frequency")
                        .details(Map.of("txnCount30min", 12, "txnCountThreshold", 10))
                        .build(),
                    DetectionResult.builder()
                        .type(FraudType.BEHAVIORAL_FRAUD)
                        .confidence(90)
                        .status("CONFIRMED")
                        .action(ActionType.MANUAL_REVIEW)
                        .reason("Transaction with high-risk merchant")
                        .details(Map.of("merchantCategory", "CRYPTOCURRENCY", "customerHistory", false))
                        .build()
                ))
                .fraudFlags(Arrays.asList("VELOCITY_SPIKE", "HIGH_RISK_MERCHANT"))
                .raisedAt(Instant.now().minus(2, ChronoUnit.HOURS))
                .raisedBy("FraudDetectionEngine")
                .assignedTo("analyst_001")
                .createdAt(Instant.now().minus(2, ChronoUnit.HOURS))
                .updatedAt(Instant.now().minus(1, ChronoUnit.HOURS))
                .build(),
            FraudAlert.builder()
                .transactionId("TXN-1002")
                .customerId("CUST-001")
                .overallFraudConfidence(45)
                .status(AlertStatus.RAISED)
                .detectionTypes(Arrays.asList(
                    DetectionResult.builder()
                        .type(FraudType.GEOGRAPHIC_FRAUD)
                        .confidence(40)
                        .status("DETECTED")
                        .action(ActionType.MONITOR)
                        .reason("Possible location inconsistency")
                        .details(Map.of("distance", "2500 km", "timeDifference", "30 mins"))
                        .build()
                ))
                .fraudFlags(Arrays.asList("LOCATION_INCONSISTENCY"))
                .raisedAt(Instant.now().minus(1, ChronoUnit.HOURS))
                .raisedBy("FraudDetectionEngine")
                .createdAt(Instant.now().minus(1, ChronoUnit.HOURS))
                .updatedAt(Instant.now().minus(30, ChronoUnit.MINUTES))
                .build()
        );

        fraudAlertRepository.saveAll(alerts);
        System.out.println("Created " + alerts.size() + " fraud alerts.");
    }

    private void createFraudRules() {
        List<FraudRule> rules = Arrays.asList(
            FraudRule.builder()
                .ruleName("VELOCITY_THRESHOLD_HIGH")
                .ruleType("VELOCITY")
                .condition("txn_count_30min > 10")
                .action("AUTO_BLOCK")
                .confidence(90)
                .enabled(true)
                .effectiveDate(Instant.now().minus(30, ChronoUnit.DAYS))
                .priority(1)
                .tags(Arrays.asList("velocity", "threshold", "block"))
                .description("Block transactions when customer makes more than 10 transactions in 30 minutes")
                .createdBy("fraud_admin")
                .createdAt(Instant.now().minus(30, ChronoUnit.DAYS))
                .updatedAt(Instant.now().minus(5, ChronoUnit.DAYS))
                .version(1)
                .build(),
            FraudRule.builder()
                .ruleName("HIGH_RISK_COUNTRY_MONITOR")
                .ruleType("GEOGRAPHIC")
                .condition("transaction_country IN high_risk_countries")
                .action("MANUAL_REVIEW")
                .confidence(70)
                .enabled(true)
                .effectiveDate(Instant.now().minus(60, ChronoUnit.DAYS))
                .priority(2)
                .tags(Arrays.asList("geographic", "monitor", "high-risk"))
                .description("Flag transactions from high-risk countries for manual review")
                .createdBy("fraud_admin")
                .createdAt(Instant.now().minus(60, ChronoUnit.DAYS))
                .updatedAt(Instant.now().minus(10, ChronoUnit.DAYS))
                .version(1)
                .build(),
            FraudRule.builder()
                .ruleName("CRYPTO_MERCHANT_RESTRICT")
                .ruleType("BEHAVIORAL")
                .condition("merchant_category = 'CRYPTOCURRENCY'")
                .action("MANUAL_REVIEW")
                .confidence(85)
                .enabled(true)
                .effectiveDate(Instant.now().minus(45, ChronoUnit.DAYS))
                .priority(1)
                .tags(Arrays.asList("behavioral", "cryptocurrency", "review"))
                .description("Review transactions with cryptocurrency merchants")
                .createdBy("fraud_admin")
                .createdAt(Instant.now().minus(45, ChronoUnit.DAYS))
                .updatedAt(Instant.now().minus(15, ChronoUnit.DAYS))
                .version(1)
                .build()
        );

        fraudRuleRepository.saveAll(rules);
        System.out.println("Created " + rules.size() + " fraud rules.");
    }

    private void createFraudPatterns() {
        List<FraudPattern> patterns = Arrays.asList(
            FraudPattern.builder()
                .patternId("PATTERN-001")
                .name("High_Velocity_New_Merchants")
                .description("Multiple rapid transactions with new merchants")
                .type("VELOCITY_BEHAVIORAL")
                .detectionAlgorithm("CLUSTERING")
                .frequency("12 occurrences in last 24 hours")
                .avgConfidence(68)
                .affectedCustomers(5)
                .trend("INCREASING")
                .firstDetected(Instant.now().minus(7, ChronoUnit.DAYS))
                .lastDetected(Instant.now().minus(2, ChronoUnit.HOURS))
                .relatedAlerts(Arrays.asList("FA890", "FA891", "FA892"))
                .severity("MEDIUM")
                .riskScore(75)
                .mitigationActions(Arrays.asList(
                    "Increase monitoring for new merchant transactions",
                    "Add velocity checks for customer segments"
                ))
                .falsePositiveRate(0.15)
                .detectionTypes(Arrays.asList("VELOCITY_FRAUD", "BEHAVIORAL_FRAUD"))
                .build(),
            FraudPattern.builder()
                .patternId("PATTERN-002")
                .name("Impossible_Travel")
                .description("Transactions from geographically impossible locations")
                .type("GEOGRAPHIC")
                .detectionAlgorithm("ANOMALY_DETECTION")
                .frequency("3 occurrences in last 24 hours")
                .avgConfidence(94)
                .affectedCustomers(2)
                .trend("STABLE")
                .firstDetected(Instant.now().minus(30, ChronoUnit.DAYS))
                .lastDetected(Instant.now().minus(5, ChronoUnit.HOURS))
                .relatedAlerts(Arrays.asList("FA901", "FA902"))
                .severity("HIGH")
                .riskScore(92)
                .mitigationActions(Arrays.asList(
                    "Implement stricter geographic verification",
                    "Add IP geolocation checks"
                ))
                .falsePositiveRate(0.05)
                .detectionTypes(Arrays.asList("GEOGRAPHIC_FRAUD"))
                .build()
        );

        fraudPatternRepository.saveAll(patterns);
        System.out.println("Created " + patterns.size() + " fraud patterns.");
    }
}