package com.fraud.detection.service.service;

import com.fraud.detection.service.kafka.FraudDetectionProducer;
import com.fraud.detection.service.model.CustomerProfile;
import com.fraud.detection.service.model.FraudAnalysisResult;
import com.fraud.detection.service.client.MongoServiceClient;
import com.riskplatform.common.entity.Transaction;
import com.riskplatform.common.entity.DetectionResult;
import com.riskplatform.common.entity.FraudAlert;
import com.riskplatform.common.entity.FraudFlag;
import com.riskplatform.common.entity.Resolution;
import com.riskplatform.common.entity.RequiredAction;
import com.riskplatform.common.entity.CustomerRiskContext;
import com.riskplatform.common.enums.AlertStatus;
import com.riskplatform.common.enums.ActionType;
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
    private MongoServiceClient mongoServiceClient;

    @Autowired
    private FraudDetectionProducer fraudDetectionProducer;

    public FraudAnalysisResult analyzeTransaction(Transaction transaction) throws FraudDetectionException {
        try {
            if (transaction.getCustomerId() == null || transaction.getCustomerId().isEmpty()) {
                throw new FraudDetectionException("Customer ID is required");
            }

            com.riskplatform.common.entity.CustomerProfile commonProfile = mongoServiceClient
                    .findCustomerProfileByCustomerId(transaction.getCustomerId())
                    .orElse(null);
            
            // Convert common entity to local model
            CustomerProfile customerProfile = null;
            if (commonProfile != null) {
                customerProfile = new CustomerProfile();
                customerProfile.setCustomerId(commonProfile.getCustomerId());
                customerProfile.setFraudHistory(commonProfile.getFraudHistory() != null ? commonProfile.getFraudHistory() : false);
                customerProfile.setPreviousAlerts(commonProfile.getPreviousAlerts() != null ? commonProfile.getPreviousAlerts() : 0);
                customerProfile.setAccountAge(commonProfile.getAccountAge());
            }

            DetectionResult velocityResult = velocityFraudDetector.detect(transaction, customerProfile);
            DetectionResult geoResult = geographicalFraudDetector.detect(transaction, customerProfile);
            DetectionResult behavioralResult = behavioralFraudDetector.detect(transaction, customerProfile);

            List<DetectionResult> detectionResults = new ArrayList<>();
            detectionResults.add(velocityResult);
            detectionResults.add(geoResult);
            detectionResults.add(behavioralResult);

            int overallConfidence = calculateOverallConfidence(detectionResults);

            ActionType recommendedAction = determineRecommendedAction(detectionResults, overallConfidence);

            List<FraudFlag> fraudFlags = createFraudFlags(detectionResults);

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
        // Determine action based on confidence levels since common-models
        // DetectionResult doesn't have action field
        // Check for auto-block conditions (confidence >= 80)
        for (DetectionResult result : detectionResults) {
            if (result.getConfidence() != null && result.getConfidence() >= 80) {
                return ActionType.AUTO_BLOCK;
            }
        }

        // Check for manual review conditions (confidence >= 60)
        for (DetectionResult result : detectionResults) {
            if (result.getConfidence() != null && result.getConfidence() >= 60) {
                return ActionType.MANUAL_REVIEW;
            }
        }

        // Check for monitor conditions (confidence >= 40)
        for (DetectionResult result : detectionResults) {
            if (result.getConfidence() != null && result.getConfidence() >= 40) {
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

    private List<FraudFlag> createFraudFlags(List<DetectionResult> detectionResults) {
        List<FraudFlag> flags = new ArrayList<>();

        for (DetectionResult result : detectionResults) {
            if ("DETECTED".equals(result.getStatus())) {
                switch (result.getType()) {
                    case VELOCITY_FRAUD:
                        flags.add(FraudFlag.builder()
                                .flag("VELOCITY_ANOMALY")
                                .severity(com.riskplatform.common.enums.Severity.MEDIUM)
                                .description("Unusual transaction velocity detected")
                                .build());
                        if (result.getConfidence() != null && result.getConfidence() >= 80) {
                            flags.add(FraudFlag.builder()
                                    .flag("HIGH_VELOCITY")
                                    .severity(com.riskplatform.common.enums.Severity.HIGH)
                                    .description("Very high transaction velocity detected")
                                    .build());
                        }
                        break;
                    case GEOGRAPHIC_FRAUD:
                        flags.add(FraudFlag.builder()
                                .flag("GEOGRAPHIC_ANOMALY")
                                .severity(com.riskplatform.common.enums.Severity.MEDIUM)
                                .description("Transaction location is unusual")
                                .build());
                        if (result.getConfidence() != null && result.getConfidence() >= 90) {
                            flags.add(FraudFlag.builder()
                                    .flag("IMPOSSIBLE_TRAVEL")
                                    .severity(com.riskplatform.common.enums.Severity.CRITICAL)
                                    .description("Impossible travel speed detected")
                                    .build());
                        }
                        break;
                    case BEHAVIORAL_FRAUD:
                        flags.add(FraudFlag.builder()
                                .flag("BEHAVIORAL_ANOMALY")
                                .severity(com.riskplatform.common.enums.Severity.MEDIUM)
                                .description("Transaction behavior deviates from pattern")
                                .build());
                        if (result.getConfidence() != null && result.getConfidence() >= 75) {
                            flags.add(FraudFlag.builder()
                                    .flag("ACCOUNT_TAKEOVER_SUSPECTED")
                                    .severity(com.riskplatform.common.enums.Severity.HIGH)
                                    .description("Potential account takeover suspected")
                                    .build());
                        }
                        break;
                    case ACCOUNT_TAKEOVER:
                        flags.add(FraudFlag.builder()
                                .flag("ACCOUNT_TAKEOVER_DETECTED")
                                .severity(com.riskplatform.common.enums.Severity.CRITICAL)
                                .description("Confirmed account takeover activity")
                                .build());
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
                        .type(ActionType.BLOCK_CARD_AND_REFUND)
                        .channel(com.riskplatform.common.enums.NotificationChannel.SMS)
                        .expirySeconds(0)
                        .build();
            case MANUAL_REVIEW:
                return RequiredAction.builder()
                        .type(ActionType.MANUAL_REVIEW)
                        .channel(com.riskplatform.common.enums.NotificationChannel.EMAIL)
                        .expirySeconds(1800) // 30 minutes
                        .build();
            case MONITOR:
                return RequiredAction.builder()
                        .type(ActionType.MONITOR)
                        .channel(com.riskplatform.common.enums.NotificationChannel.SMS)
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
                    .notes(null)
                    .build();

            FraudAlert fraudAlert = FraudAlert.builder()
                    .fraudAlertId(analysisResult.getFraudAlertId()) // Use the generated alert ID
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

            return mongoServiceClient.saveFraudAlert(fraudAlert);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to save fraud alert: " + e.getMessage(), e);
        }
    }
}