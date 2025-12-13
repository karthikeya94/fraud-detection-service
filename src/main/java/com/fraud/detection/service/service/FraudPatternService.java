package com.fraud.detection.service.service;

import com.fraud.detection.service.model.FraudAlert;
import com.fraud.detection.service.model.FraudPattern;
import com.fraud.detection.service.repository.FraudAlertRepository;
import com.fraud.detection.service.repository.FraudPatternRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FraudPatternService {

    @Autowired
    private FraudAlertRepository fraudAlertRepository;

    @Autowired
    private FraudPatternRepository fraudPatternRepository;

    public void detectAndUpdatePatterns(long timeWindowSeconds) throws FraudDetectionException {
        try {
            Instant since = Instant.now().minusSeconds(timeWindowSeconds);
            List<FraudAlert> recentAlerts = fraudAlertRepository.findByRaisedAtBetween(since, Instant.now());

            Map<String, List<FraudAlert>> alertsByCustomer = recentAlerts.stream()
                    .collect(Collectors.groupingBy(FraudAlert::getCustomerId));

            for (Map.Entry<String, List<FraudAlert>> entry : alertsByCustomer.entrySet()) {
                List<FraudAlert> customerAlerts = entry.getValue();

                if (customerAlerts.size() >= 5) {
                    updateOrCreatePattern("High_Velocity_Customer", "Multiple alerts for single customer",
                            "VELOCITY", "High", customerAlerts.size(), 1);
                }

                Map<String, Long> detectionTypeCounts = customerAlerts.stream()
                        .flatMap(alert -> alert.getDetectionTypes().stream())
                        .filter(type -> "DETECTED".equals(type.getStatus()))
                        .collect(Collectors.groupingBy(
                                type -> type.getType().name(),
                                Collectors.counting()));

                for (Map.Entry<String, Long> typeEntry : detectionTypeCounts.entrySet()) {
                    String detectionType = typeEntry.getKey();
                    Long count = typeEntry.getValue();

                    if (count >= 3) {
                        updateOrCreatePattern(
                                detectionType + "_Pattern",
                                "Multiple occurrences of " + detectionType,
                                detectionType,
                                "Medium",
                                count.intValue(),
                                1);
                    }
                }
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to detect and update fraud patterns: " + e.getMessage(), e);
        }
    }

    public List<FraudPattern> getPatternsByTrend(String trend) throws FraudDetectionException {
        try {
            return fraudPatternRepository.findByTrend(trend);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud patterns by trend: " + e.getMessage(), e);
        }
    }

    public List<FraudPattern> getPatternsBySeverity(String severity) throws FraudDetectionException {
        try {
            return fraudPatternRepository.findBySeverity(severity);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud patterns by severity: " + e.getMessage(), e);
        }
    }

    public List<FraudPattern> getPatternsByDetectionType(String detectionType) throws FraudDetectionException {
        try {
            return fraudPatternRepository.findByDetectionTypesContaining(detectionType);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud patterns by detection type: " + e.getMessage(),
                    e);
        }
    }

    private void updateOrCreatePattern(String name, String description, String type,
            String severity, int frequency, int affectedCustomers) throws FraudDetectionException {
        try {
            List<FraudPattern> existingPatterns = fraudPatternRepository.findByName(name);

            FraudPattern pattern;
            if (!existingPatterns.isEmpty()) {
                pattern = existingPatterns.get(0);
                pattern.setFrequency(pattern.getFrequency() + ", " + frequency + " occurrences in last period");
                pattern.setAffectedCustomers(pattern.getAffectedCustomers() + affectedCustomers);
                pattern.setLastDetected(Instant.now());

                if (frequency > parseLastFrequency(pattern.getFrequency())) {
                    pattern.setTrend("INCREASING");
                } else if (frequency < parseLastFrequency(pattern.getFrequency())) {
                    pattern.setTrend("DECREASING");
                } else {
                    pattern.setTrend("STABLE");
                }
            } else {
                pattern = FraudPattern.builder()
                        .patternId("PATTERN-" + System.currentTimeMillis() % 100000)
                        .name(name)
                        .description(description)
                        .type(type)
                        .frequency(frequency + " occurrences in last period")
                        .avgConfidence(0)
                        .affectedCustomers(affectedCustomers)
                        .trend("NEW")
                        .firstDetected(Instant.now())
                        .lastDetected(Instant.now())
                        .severity(severity)
                        .riskScore(0)
                        .falsePositiveRate(0.0)
                        .build();
            }

            fraudPatternRepository.save(pattern);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to update or create fraud pattern: " + e.getMessage(), e);
        }
    }

    private int parseLastFrequency(String frequency) {
        try {
            // Extract the last numeric value from the frequency string
            String[] parts = frequency.split(", ");
            String lastPart = parts[parts.length - 1];
            return Integer.parseInt(lastPart.split(" ")[0]);
        } catch (Exception e) {
            return 0;
        }
    }
}