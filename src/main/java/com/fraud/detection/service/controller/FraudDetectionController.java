package com.fraud.detection.service.controller;

import com.fraud.detection.service.dto.*;
import com.riskplatform.common.entity.FraudAlert;
import com.fraud.detection.service.model.FraudRule;
import com.riskplatform.common.entity.Resolution;
import com.riskplatform.common.enums.AlertStatus;
import com.riskplatform.common.enums.ActionType;
import com.fraud.detection.service.service.FraudAnalysisService;
import com.fraud.detection.service.client.MongoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fraud")
public class FraudDetectionController {

    @Autowired
    private FraudAnalysisService fraudAnalysisService;

    @Autowired
    private MongoServiceClient mongoServiceClient;

    @PostMapping("/analyze")
    public ResponseEntity<FraudAnalysisResponse> analyzeFraud(@Valid @RequestBody FraudAnalysisRequest request) {
        try {
            com.riskplatform.common.entity.Transaction transaction = com.riskplatform.common.entity.Transaction
                    .builder()
                    .transactionId(request.getTransactionId())
                    .customerId(request.getCustomerId())
                    .amount(request.getAmount())
                    .merchant(request.getMerchant())
                    .merchantCategory(request.getMerchantCategory())
                    .timestamp(request.getTimestamp())
                    .location(com.riskplatform.common.model.Location.builder()
                            .country(request.getLocation().getCountry())
                            .city(request.getLocation().getCity())
                            .ip(request.getLocation().getIp())
                            .build())
                    .device(request.getDevice().getDeviceId())
                    .build();

            com.fraud.detection.service.model.FraudAnalysisResult analysisResult = fraudAnalysisService
                    .analyzeTransaction(transaction);

            FraudAnalysisResponse response = FraudAnalysisResponse.builder()
                    .fraudAnalysis(FraudAnalysisResponse.FraudAnalysisResult.builder()
                            .transactionId(analysisResult.getTransactionId())
                            .fraudAlertId(analysisResult.getFraudAlertId())
                            .overallFraudConfidence(analysisResult.getOverallFraudConfidence())
                            .status(analysisResult.getStatus())
                            .recommendedAction(analysisResult.getRecommendedAction())
                            .detectionTypes(analysisResult.getDetectionTypes())
                            .fraudFlags(analysisResult.getFraudFlags())
                            .customerRiskContext(analysisResult.getCustomerRiskContext())
                            .requiredAction(analysisResult.getRequiredAction())
                            .build())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/alerts/{alertId}")
    public ResponseEntity<FraudAlertResponse> getFraudAlert(@PathVariable String alertId) {
        try {
            Optional<FraudAlert> fraudAlertOpt = mongoServiceClient.findFraudAlertById(alertId);

            if (fraudAlertOpt.isPresent()) {
                FraudAlert fraudAlert = fraudAlertOpt.get();

                FraudAlertResponse response = FraudAlertResponse.builder()
                        .fraudAlertId(fraudAlert.getFraudAlertId())
                        .transactionId(fraudAlert.getTransactionId())
                        .customerId(fraudAlert.getCustomerId())
                        .status(fraudAlert.getStatus().name())
                        .overallFraudConfidence(fraudAlert.getOverallFraudConfidence())
                        .detectionTypes(fraudAlert.getDetectionTypes())
                        .raisedAt(fraudAlert.getRaisedAt())
                        .raisedBy(fraudAlert.getRaisedBy())
                        .assignedTo(fraudAlert.getAssignedTo())
                        .reviewedAt(fraudAlert.getReviewedAt())
                        .actionTaken(
                                fraudAlert.getResolution() != null && fraudAlert.getResolution().getAction() != null
                                        ? fraudAlert.getResolution().getAction().name()
                                        : null)
                        .createdAt(fraudAlert.getCreatedAt())
                        .updatedAt(fraudAlert.getUpdatedAt())
                        .build();

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/alerts/{alertId}/resolve")
    public ResponseEntity<ResolveFraudAlertResponse> resolveFraudAlert(
            @PathVariable String alertId,
            @Valid @RequestBody ResolveFraudAlertRequest request) {
        try {
            Optional<FraudAlert> fraudAlertOpt = mongoServiceClient.findFraudAlertById(alertId);

            if (fraudAlertOpt.isPresent()) {
                FraudAlert fraudAlert = fraudAlertOpt.get();

                Resolution resolution = Resolution.builder()
                        .action(ActionType.valueOf(request.getAction()))
                        .reason(request.getReason())
                        .resolvedBy("fraud_officer_001")
                        .resolvedAt(Instant.now())
                        .build();

                fraudAlert.setResolution(resolution);
                fraudAlert.setStatus(AlertStatus.CONFIRMED);
                fraudAlert.setUpdatedAt(Instant.now());

                mongoServiceClient.saveFraudAlert(fraudAlert);

                ResolveFraudAlertResponse response = ResolveFraudAlertResponse.builder()
                        .fraudAlertId(fraudAlert.getFraudAlertId())
                        .status(fraudAlert.getStatus().name())
                        .action(request.getAction())
                        .resolvedAt(resolution.getResolvedAt())
                        .resolvedBy(resolution.getResolvedBy())
                        .refundStatus("INITIATED")
                        .cardBlockStatus("INITIATED")
                        .message("Fraud confirmed. Refund initiated. Card blocked.")
                        .build();

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/patterns")
    public ResponseEntity<FraudPatternsResponse> getFraudPatterns() {
        try {
            Instant since = Instant.now().minusSeconds(24 * 60 * 60);
            // Note: findByRaisedAtBetween not available in MongoServiceClient
            // Using findByStatus and filtering in memory
            List<FraudAlert> allAlerts = mongoServiceClient.findFraudAlertsByStatus(AlertStatus.RAISED);
            List<com.riskplatform.common.entity.FraudPattern> patterns = allAlerts.stream()
                    .filter(alert -> alert.getRaisedAt() != null && 
                            alert.getRaisedAt().isAfter(since) && 
                            alert.getRaisedAt().isBefore(Instant.now()))
                    .filter(alert -> alert.getOverallFraudConfidence() != null
                            && alert.getOverallFraudConfidence() >= 40)
                    .map(this::convertToFraudPattern)
                    .collect(Collectors.toList());

            FraudPatternsResponse response = FraudPatternsResponse.builder()
                    .patterns(patterns)
                    .timeWindow("24_hours")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    private com.riskplatform.common.entity.FraudPattern convertToFraudPattern(
            com.riskplatform.common.entity.FraudAlert alert) {
        return com.riskplatform.common.entity.FraudPattern.builder()
                .patternId("PATTERN-"
                        + alert.getFraudAlertId().substring(0, Math.min(6, alert.getFraudAlertId().length())))
                .name("Suspicious Activity Pattern")
                .description("Pattern detected from multiple fraud alerts")
                .type("MULTI_ALERT_PATTERN")
                .frequency("Multiple occurrences")
                .avgConfidence(alert.getOverallFraudConfidence())
                .affectedCustomers(1)
                .trend("STABLE")
                .lastDetected(alert.getRaisedAt())
                .build();
    }

    @PostMapping("/rules/add")
    public ResponseEntity<AddFraudRuleResponse> addFraudRule(@Valid @RequestBody AddFraudRuleRequest request) {
        try {
            FraudRule fraudRule = FraudRule.builder()
                    .ruleName(request.getRuleName())
                    .ruleType(request.getRuleType())
                    .condition(request.getCondition())
                    .action(request.getAction())
                    .confidence(request.getConfidence())
                    .enabled(request.getEnabled())
                    .effectiveDate(request.getEffectiveDate())
                    .expirationDate(null) // No expiration by default
                    .priority(1) // Default priority
                    .tags(List.of("DYNAMIC_RULE")) // Tag as dynamic rule
                    .description("Dynamic rule added via API")
                    .createdBy("API_USER") // In a real implementation, this would come from authentication
                    .createdAt(java.time.Instant.now())
                    .updatedAt(java.time.Instant.now())
                    .version(1)
                    .build();

            // Save the rule to the database
            com.riskplatform.common.entity.FraudRule commonRule = convertToCommonFraudRule(fraudRule);
            com.riskplatform.common.entity.FraudRule savedCommonRule = mongoServiceClient.saveFraudRule(commonRule);
            fraudRule.setId(savedCommonRule.getId());

            AddFraudRuleResponse response = AddFraudRuleResponse.builder()
                    .ruleId(fraudRule.getId())
                    .status("ACTIVE")
                    .version(fraudRule.getVersion())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    private com.riskplatform.common.entity.FraudRule convertToCommonFraudRule(FraudRule localRule) {
        return com.riskplatform.common.entity.FraudRule.builder()
                .id(localRule.getId())
                .ruleName(localRule.getRuleName())
                .ruleType(localRule.getRuleType())
                .condition(localRule.getCondition())
                .action(localRule.getAction())
                .confidence(localRule.getConfidence())
                .enabled(localRule.getEnabled())
                .effectiveDate(localRule.getEffectiveDate())
                .expirationDate(localRule.getExpirationDate())
                .priority(localRule.getPriority())
                .tags(localRule.getTags())
                .description(localRule.getDescription())
                .createdAt(localRule.getCreatedAt())
                .updatedAt(localRule.getUpdatedAt())
                .version(localRule.getVersion())
                .build();
    }
}