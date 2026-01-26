package com.fraud.detection.service.client;

import com.riskplatform.common.entity.*;
import com.riskplatform.common.enums.AlertStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "mongo-service", contextId = "fraudDetectionClient")
public interface MongoServiceClient {

    @PostMapping("/merchant-data")
    MerchantData saveMerchantData(@RequestBody MerchantData merchantData);

    @GetMapping("/merchant-data/search/findByMerchantId")
    Optional<MerchantData> findMerchantDataByMerchantId(@RequestParam("merchantId") String merchantId);

    @GetMapping("/merchant-data/search/findByCategory")
    List<MerchantData> findMerchantDataByCategory(@RequestParam("category") String category);

    @PostMapping("/fraud-rules")
    FraudRule saveFraudRule(@RequestBody FraudRule fraudRule);

    @GetMapping("/fraud-rules/{id}")
    Optional<FraudRule> findFraudRuleById(@PathVariable("id") String id);

    @GetMapping("/fraud-rules/search/findByEnabledTrueAndEffectiveDateBeforeAndExpirationDateAfter")
    List<FraudRule> findActiveFraudRules(@RequestParam("effectiveDate") Instant effectiveDate, @RequestParam("expirationDate") Instant expirationDate);

    @GetMapping("/fraud-rules/search/findByRuleTypeAndEnabledTrue")
    List<FraudRule> findFraudRulesByRuleTypeAndEnabledTrue(@RequestParam("ruleType") String ruleType);

    @GetMapping("/fraud-rules/search/findByTagsContaining")
    List<FraudRule> findFraudRulesByTagsContaining(@RequestParam("tag") String tag);

    @PostMapping("/fraud-patterns")
    FraudPattern saveFraudPattern(@RequestBody FraudPattern fraudPattern);

    @GetMapping("/fraud-patterns/{id}")
    Optional<FraudPattern> findFraudPatternById(@PathVariable("id") String id);

    @GetMapping("/fraud-patterns/search/findByName")
    List<FraudPattern> findFraudPatternsByName(@RequestParam("name") String name);

    @GetMapping("/fraud-patterns/search/findByTrend")
    List<FraudPattern> findFraudPatternsByTrend(@RequestParam("trend") String trend);

    @GetMapping("/fraud-patterns/search/findBySeverity")
    List<FraudPattern> findFraudPatternsBySeverity(@RequestParam("severity") String severity);

    @GetMapping("/fraud-patterns/search/findByDetectionTypesContaining")
    List<FraudPattern> findFraudPatternsByDetectionTypesContaining(@RequestParam("detectionType") String detectionType);

    @PostMapping("/fraud-alerts")
    FraudAlert saveFraudAlert(@RequestBody FraudAlert fraudAlert);

    @GetMapping("/fraud-alerts/{id}")
    Optional<FraudAlert> findFraudAlertById(@PathVariable("id") String id);

    @GetMapping("/fraud-alerts/search/findByTransactionId")
    Optional<FraudAlert> findFraudAlertByTransactionId(@RequestParam("transactionId") String transactionId);

    @GetMapping("/fraud-alerts/search/findByCustomerId")
    List<FraudAlert> findFraudAlertsByCustomerId(@RequestParam("customerId") String customerId);

    @GetMapping("/fraud-alerts/search/findByStatus")
    List<FraudAlert> findFraudAlertsByStatus(@RequestParam("status") AlertStatus status);

    @GetMapping("/fraud-alerts/search/findByRaisedAtBetween")
    List<FraudAlert> findFraudAlertsByRaisedAtBetween(@RequestParam("startDate") Instant startDate, @RequestParam("endDate") Instant endDate);

    @GetMapping("/fraud-alerts/search/findByStatusAndReviewedAtBefore")
    List<FraudAlert> findFraudAlertsByStatusAndReviewedAtBefore(@RequestParam("status") AlertStatus status, @RequestParam("cutoffTime") Instant cutoffTime);

    @GetMapping("/fraud-alerts/search/findByStatusAndUpdatedAtBefore")
    List<FraudAlert> findFraudAlertsByStatusAndUpdatedAtBefore(@RequestParam("status") AlertStatus status, @RequestParam("cutoffTime") Instant cutoffTime);

    @PostMapping("/customer-profiles")
    CustomerProfile saveCustomerProfile(@RequestBody CustomerProfile customerProfile);

    @GetMapping("/customer-profiles/search/findByCustomerId")
    Optional<CustomerProfile> findCustomerProfileByCustomerId(@RequestParam("customerId") String customerId);
}
