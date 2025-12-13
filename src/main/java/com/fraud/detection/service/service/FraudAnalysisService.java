package com.fraud.detection.service.service;

import com.fraud.detection.service.kafka.FraudDetectionProducer;
import com.fraud.detection.service.model.*;
import com.fraud.detection.service.model.enums.AlertStatus;
import com.fraud.detection.service.model.enums.ActionType;
import com.fraud.detection.service.repository.CustomerProfileRepository;
import com.fraud.detection.service.repository.FraudAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FraudAnalysisService {

    @Autowired
    private VelocityFraudDetector velocityFraudDetector;

    @Autowired
    private GeographicalFraudDetector geographicalFraudDetector;

    @Autowired
    private BehavioralFraudDetector behavioralFraudDetector;

    @Autowired
    private CustomerProfileRepository customerProfileRepository;

    @Autowired
    private FraudAlertRepository fraudAlertRepository;

    @Autowired
    private FraudDetectionProducer fraudDetectionProducer;

    public FraudAnalysisResult analyzeTransaction(Transaction transaction) throws FraudDetectionException {
        try {
            if (transaction.getCustomerId() == null || transaction.getCustomerId().isEmpty()) {
                throw new FraudDetectionException("Customer ID is required");
            }

            CustomerProfile customerProfile = customerProfileRepository
                    .findByCustomerId(transaction.getCustomerId())
                    .orElse(null);

            DetectionResult velocityResult = velocityFraudDetector.detect(transaction, customerProfile);
            DetectionResult geoResult = geographicalFraudDetector.detect(transaction, customerProfile);
            DetectionResult behavioralResult = behavioralFraudDetector.detect(transaction, customerProfile);

            List<DetectionResult> detectionResults = new ArrayList<>();
            detectionResults.add(velocityResult);
            detectionResults.add(geoResult);
            detectionResults.add(behavioralResult);

            int overallConfidence = calculateOverallConfidence(detectionResults);

            ActionType recommendedAction = determineRecommendedAction(detectionResults, overallConfidence);

            List<String> fraudFlags = createFraudFlags(detectionResults);

            CustomerRiskContext riskContext = createCustomerRiskContext(customerProfile);

            RequiredAction requiredAction = createRequiredAction(recommendedAction);

            FraudAnalysisResult result = FraudAnalysisResult.builder()
                    .transactionId(transaction.getId())
                    .fraudAlertId("FA" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                    .overallFraudConfidence(overallConfidence)
                    .status(AlertStatus.RAISED.name())
                    .recommendedAction(recommendedAction.name())
                    .detectionTypes(detectionResults)
                    .fraudFlags(fraudFlags)
                    .customerRiskContext(riskContext)
                    .requiredAction(requiredAction)
                    .build();

            FraudAlert savedAlert = saveFraudAlert(transaction, result, detectionResults);

            if (recommendedAction == ActionType.AUTO_BLOCK) {
                fraudDetectionProducer.sendFraudAlertConfirmed(savedAlert);
            } else if (recommendedAction == ActionType.MANUAL_REVIEW) {
                fraudDetectionProducer.sendFraudAlertRaised(savedAlert);
            } else if (recommendedAction == ActionType.MONITOR) {
                fraudDetectionProducer.sendFraudAlertRaised(savedAlert);
            }

            return result;
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to analyze transaction for fraud: " + e.getMessage(), e);
        }
    }

    private int calculateOverallConfidence(List<DetectionResult> detectionResults) {
        if (detectionResults.isEmpty()) {
            return 0;
        }

        // Weighted average:
        // Velocity: 35%, Geographic: 30%, Behavioral: 35%
        double velocityWeight = 0.35;
        double geographicWeight = 0.30;
        double behavioralWeight = 0.35;

        int velocityConfidence = detectionResults.get(0).getConfidence() != null
                ? detectionResults.get(0).getConfidence()
                : 0;
        int geographicConfidence = detectionResults.get(1).getConfidence() != null
                ? detectionResults.get(1).getConfidence()
                : 0;
        int behavioralConfidence = detectionResults.get(2).getConfidence() != null
                ? detectionResults.get(2).getConfidence()
                : 0;

        double weightedConfidence = (velocityConfidence * velocityWeight) +
                (geographicConfidence * geographicWeight) +
                (behavioralConfidence * behavioralWeight);

        return (int) Math.round(weightedConfidence);
    }

    private ActionType determineRecommendedAction(List<DetectionResult> detectionResults, int overallConfidence) {
        // Check for auto-block conditions first
        for (DetectionResult result : detectionResults) {
            if (result.getAction() == ActionType.AUTO_BLOCK) {
                return ActionType.AUTO_BLOCK;
            }
        }

        // Check for manual review
        for (DetectionResult result : detectionResults) {
            if (result.getAction() == ActionType.MANUAL_REVIEW) {
                return ActionType.MANUAL_REVIEW;
            }
        }

        // Check for monitor
        for (DetectionResult result : detectionResults) {
            if (result.getAction() == ActionType.MONITOR) {
                return ActionType.MONITOR;
            }
        }

        // Default to allow if confidence is low
        if (overallConfidence < 40) {
            return ActionType.ALLOW;
        }

        // For moderate confidence, default to monitor
        return ActionType.MONITOR;
    }

    private List<String> createFraudFlags(List<DetectionResult> detectionResults) {
        List<String> flags = new ArrayList<>();

        for (DetectionResult result : detectionResults) {
            if ("DETECTED".equals(result.getStatus())) {
                switch (result.getType()) {
                    case VELOCITY_FRAUD:
                        flags.add("VELOCITY_ANOMALY");
                        if (result.getConfidence() != null && result.getConfidence() >= 80) {
                            flags.add("HIGH_VELOCITY");
                        }
                        break;
                    case GEOGRAPHIC_FRAUD:
                        flags.add("GEOGRAPHIC_ANOMALY");
                        if (result.getConfidence() != null && result.getConfidence() >= 90) {
                            flags.add("IMPOSSIBLE_TRAVEL");
                        }
                        break;
                    case BEHAVIORAL_FRAUD:
                        flags.add("BEHAVIORAL_ANOMALY");
                        if (result.getConfidence() != null && result.getConfidence() >= 75) {
                            flags.add("ACCOUNT_TAKEOVER_SUSPECTED");
                        }
                        break;
                }
            }
        }

        return flags;
    }

    private CustomerRiskContext createCustomerRiskContext(CustomerProfile customerProfile) {
        return CustomerRiskContext.builder()
                .fraudHistory(customerProfile != null ? customerProfile.getFraudHistory() : false)
                .previousAlerts(customerProfile != null ? customerProfile.getPreviousAlerts() : 0)
                .accountAge(customerProfile != null ? customerProfile.getAccountAge() : "Unknown")
                .build();
    }

    private RequiredAction createRequiredAction(ActionType recommendedAction) {
        switch (recommendedAction) {
            case AUTO_BLOCK:
                return RequiredAction.builder()
                        .type("ACCOUNT_LOCK")
                        .channel("ALL")
                        .expirySeconds(0)
                        .build();
            case MANUAL_REVIEW:
                return RequiredAction.builder()
                        .type("MANUAL_REVIEW")
                        .channel("FRAUD_TEAM")
                        .expirySeconds(1800) // 30 minutes
                        .build();
            case MONITOR:
                return RequiredAction.builder()
                        .type("ENHANCED_MONITORING")
                        .channel("SYSTEM")
                        .expirySeconds(86400) // 24 hours
                        .build();
            default:
                return null;
        }
    }

    private FraudAlert saveFraudAlert(Transaction transaction, FraudAnalysisResult analysisResult,
            List<DetectionResult> detectionResults) throws FraudDetectionException {
        try {
            Resolution resolution = Resolution.builder()
                    .action(null)
                    .reason(null)
                    .resolvedBy(null)
                    .resolvedAt(null)
                    .build();

            FraudAlert fraudAlert = FraudAlert.builder()
                    .id(analysisResult.getFraudAlertId()) // Use the generated alert ID
                    .transactionId(transaction.getId())
                    .customerId(transaction.getCustomerId())
                    .overallFraudConfidence(analysisResult.getOverallFraudConfidence())
                    .status(AlertStatus.RAISED)
                    .detectionTypes(detectionResults)
                    .fraudFlags(analysisResult.getFraudFlags())
                    .raisedAt(Instant.now())
                    .raisedBy("FraudDetectionEngine")
                    .assignedTo(null)
                    .reviewedAt(null)
                    .resolution(resolution)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

            return fraudAlertRepository.save(fraudAlert);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to save fraud alert: " + e.getMessage(), e);
        }
    }
}