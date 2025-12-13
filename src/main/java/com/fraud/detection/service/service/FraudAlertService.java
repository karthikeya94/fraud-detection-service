package com.fraud.detection.service.service;

import com.fraud.detection.service.kafka.FraudDetectionProducer;
import com.fraud.detection.service.model.FraudAlert;
import com.fraud.detection.service.model.Resolution;
import com.fraud.detection.service.model.enums.AlertStatus;
import com.fraud.detection.service.repository.FraudAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;

@Service
public class FraudAlertService {

    @Autowired
    private FraudAlertRepository fraudAlertRepository;

    @Autowired
    private FraudDetectionProducer fraudDetectionProducer;

    public FraudAlert confirmFraudAlert(String alertId, String resolverId, Resolution resolutionDetails)
            throws FraudDetectionException {
        try {
            Optional<FraudAlert> alertOpt = fraudAlertRepository.findById(alertId);

            if (alertOpt.isPresent()) {
                FraudAlert alert = alertOpt.get();

                if (alert.getStatus() == AlertStatus.RAISED || alert.getStatus() == AlertStatus.REVIEW_PENDING) {
                    alert.setStatus(AlertStatus.CONFIRMED);
                    alert.setResolution(resolutionDetails);
                    alert.setUpdatedAt(Instant.now());

                    FraudAlert updatedAlert = fraudAlertRepository.save(alert);

                    fraudDetectionProducer.sendFraudAlertConfirmed(updatedAlert);

                    return updatedAlert;
                } else {
                    throw new IllegalStateException("Cannot confirm fraud alert in status: " + alert.getStatus());
                }
            } else {
                throw new IllegalArgumentException("Fraud alert not found with ID: " + alertId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to confirm fraud alert: " + e.getMessage(), e);
        }
    }

    public FraudAlert dismissFraudAlert(String alertId, String resolverId, Resolution resolutionDetails)
            throws FraudDetectionException {
        try {
            Optional<FraudAlert> alertOpt = fraudAlertRepository.findById(alertId);

            if (alertOpt.isPresent()) {
                FraudAlert alert = alertOpt.get();

                if (alert.getStatus() == AlertStatus.RAISED || alert.getStatus() == AlertStatus.REVIEW_PENDING) {
                    alert.setStatus(AlertStatus.DISMISSED);
                    alert.setResolution(resolutionDetails);
                    alert.setUpdatedAt(Instant.now());

                    FraudAlert updatedAlert = fraudAlertRepository.save(alert);

                    fraudDetectionProducer.sendFraudAlertDismissed(updatedAlert);

                    return updatedAlert;
                } else {
                    throw new IllegalStateException("Cannot dismiss fraud alert in status: " + alert.getStatus());
                }
            } else {
                throw new IllegalArgumentException("Fraud alert not found with ID: " + alertId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to dismiss fraud alert: " + e.getMessage(), e);
        }
    }

    public FraudAlert resolveFraudAlert(String alertId, String resolverId, Resolution resolutionDetails)
            throws FraudDetectionException {
        try {
            Optional<FraudAlert> alertOpt = fraudAlertRepository.findById(alertId);

            if (alertOpt.isPresent()) {
                FraudAlert alert = alertOpt.get();

                if (alert.getStatus() == AlertStatus.CONFIRMED) {
                    alert.setStatus(AlertStatus.RESOLVED);
                    alert.setResolution(resolutionDetails);
                    alert.setUpdatedAt(Instant.now());

                    return fraudAlertRepository.save(alert);
                } else {
                    throw new IllegalStateException("Cannot resolve fraud alert in status: " + alert.getStatus());
                }
            } else {
                throw new IllegalArgumentException("Fraud alert not found with ID: " + alertId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to resolve fraud alert: " + e.getMessage(), e);
        }
    }

    public FraudAlert assignForReview(String alertId, String assignedTo) throws FraudDetectionException {
        try {
            Optional<FraudAlert> alertOpt = fraudAlertRepository.findById(alertId);

            if (alertOpt.isPresent()) {
                FraudAlert alert = alertOpt.get();

                if (alert.getStatus() == AlertStatus.RAISED) {
                    alert.setStatus(AlertStatus.REVIEW_PENDING);
                    alert.setAssignedTo(assignedTo);
                    alert.setReviewedAt(Instant.now());
                    alert.setUpdatedAt(Instant.now());

                    return fraudAlertRepository.save(alert);
                } else {
                    throw new IllegalStateException(
                            "Cannot assign fraud alert for review in status: " + alert.getStatus());
                }
            } else {
                throw new IllegalArgumentException("Fraud alert not found with ID: " + alertId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to assign fraud alert for review: " + e.getMessage(), e);
        }
    }

    public void autoTransitionPendingAlerts(long maxReviewTimeSeconds) throws FraudDetectionException {
        try {
            Instant cutoffTime = Instant.now().minusSeconds(maxReviewTimeSeconds);

            fraudAlertRepository.findByStatusAndReviewedAtBefore(AlertStatus.REVIEW_PENDING, cutoffTime)
                    .forEach(alert -> {
                        try {
                            Resolution resolution = Resolution.builder()
                                    .action("AUTO_ESCALATE")
                                    .reason("Not reviewed within time limit")
                                    .resolvedBy("SYSTEM")
                                    .resolvedAt(Instant.now())
                                    .build();

                            confirmFraudAlert(alert.getId(), "SYSTEM", resolution);
                        } catch (FraudDetectionException e) {
                        }
                    });
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to auto-transition pending alerts: " + e.getMessage(), e);
        }
    }

    public void autoResolveConfirmedAlerts(long maxInactivitySeconds) throws FraudDetectionException {
        try {
            Instant cutoffTime = Instant.now().minusSeconds(maxInactivitySeconds);

            fraudAlertRepository.findByStatusAndUpdatedAtBefore(AlertStatus.CONFIRMED, cutoffTime)
                    .forEach(alert -> {
                        try {
                            Resolution resolution = Resolution.builder()
                                    .action("AUTO_RESOLVE")
                                    .reason("No activity for extended period")
                                    .resolvedBy("SYSTEM")
                                    .resolvedAt(Instant.now())
                                    .build();

                            resolveFraudAlert(alert.getId(), "SYSTEM", resolution);
                        } catch (FraudDetectionException e) {
                        }
                    });
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to auto-resolve confirmed alerts: " + e.getMessage(), e);
        }
    }
}