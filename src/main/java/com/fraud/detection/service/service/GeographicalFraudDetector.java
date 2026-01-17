package com.fraud.detection.service.service;

import com.fraud.detection.service.model.CustomerDevice;
import com.fraud.detection.service.model.CustomerProfile;
import com.riskplatform.common.entity.Transaction;
import com.riskplatform.common.model.DeviceInfo;
import com.riskplatform.common.model.Location;
import com.riskplatform.common.entity.DetectionResult;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

@Service
public class GeographicalFraudDetector {

    // High-risk countries list (simplified for demonstration)
    private static final Set<String> HIGH_RISK_COUNTRIES = new HashSet<>(Arrays.asList(
            "KP", "IR", "SY", "CU", "SD", "ZW"));

    public DetectionResult detect(Transaction transaction, CustomerProfile customerProfile)
            throws FraudDetectionException {
        try {
            DetectionResult result = DetectionResult.builder()
                    .type(com.riskplatform.common.enums.FraudType.GEOGRAPHIC_FRAUD)
                    .status(com.riskplatform.common.enums.AlertStatus.NOT_DETECTED)
                    .confidence(0)
                    .details(new HashMap<>())
                    .build();

            if (transaction.getLocation() == null || customerProfile == null) {
                return result;
            }

            Location currentLocation = transaction.getLocation();
            Map<String, Object> details = result.getDetails();
            int impossibleTravelConfidence = checkImpossibleTravel(transaction, customerProfile, details);
            if (impossibleTravelConfidence >= 90) {
                result.setStatus(com.riskplatform.common.enums.AlertStatus.DETECTED);
                result.setConfidence(impossibleTravelConfidence);
                result.setReason("Impossible travel detected");
                return result;
            }

            int highRiskCountryConfidence = checkHighRiskCountry(currentLocation, customerProfile, details);
            if (highRiskCountryConfidence >= 70) {
                result.setStatus(com.riskplatform.common.enums.AlertStatus.DETECTED);
                result.setConfidence(highRiskCountryConfidence);
                result.setReason("Transaction from high-risk country");
                return result;
            }

            int deviceIpMismatchConfidence = checkDeviceIpMismatch(transaction, customerProfile, details);
            if (deviceIpMismatchConfidence > 0) {
                result.setStatus(com.riskplatform.common.enums.AlertStatus.DETECTED);
                result.setConfidence(deviceIpMismatchConfidence);

                if (deviceIpMismatchConfidence >= 65) {
                    result.setReason("Device/IP mismatch with high confidence");
                } else if (deviceIpMismatchConfidence >= 40) {
                    result.setReason("Device/IP mismatch detected");
                } else {
                    result.setReason("Minor device/IP inconsistency");
                }

                return result;
            }

            return result;
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to perform geographical fraud detection: " + e.getMessage(), e);
        }
    }

    private int checkImpossibleTravel(Transaction transaction, CustomerProfile customerProfile,
            Map<String, Object> details) {
        List<Transaction> transactionHistory = customerProfile.getTransactionHistory();
        if (transactionHistory == null || transactionHistory.isEmpty()) {
            return 0;
        }

        Location currentLocation = transaction.getLocation();
        Instant currentTime = transaction.getTimestamp();

        for (int i = transactionHistory.size() - 1; i >= 0; i--) {
            Transaction prevTransaction = transactionHistory.get(i);
            if (prevTransaction.getLocation() != null && prevTransaction.getLocation().getLatitude() != null
                    && prevTransaction.getLocation().getLongitude() != null) {
                Location prevLocation = prevTransaction.getLocation();
                Instant prevTime = prevTransaction.getTimestamp();

                long timeDiffMinutes = ChronoUnit.MINUTES.between(prevTime, currentTime);
                if (timeDiffMinutes <= 0)
                    continue;

                double distanceKm = calculateDistance(
                        prevLocation.getLatitude(), prevLocation.getLongitude(),
                        currentLocation.getLatitude(), currentLocation.getLongitude());

                double minPossibleTimeHours = distanceKm / 1000.0;
                double minPossibleTimeMinutes = minPossibleTimeHours * 60;

                if (timeDiffMinutes < minPossibleTimeMinutes && distanceKm > 500) {
                    details.put("impossibleTravel", true);
                    details.put("distanceKm", distanceKm);
                    details.put("timeDiffMinutes", timeDiffMinutes);
                    details.put("minPossibleTimeMinutes", minPossibleTimeMinutes);
                    return 95;
                }

                break;
            }
        }

        return 0;
    }

    private int checkHighRiskCountry(Location currentLocation, CustomerProfile customerProfile,
            Map<String, Object> details) {
        String currentCountry = currentLocation.getCountry();
        if (currentCountry == null) {
            return 0;
        }

        if (HIGH_RISK_COUNTRIES.contains(currentCountry.toUpperCase())) {
            boolean hasHistoryInCountry = false;
            if (customerProfile.getTransactionHistory() != null) {
                for (Transaction txn : customerProfile.getTransactionHistory()) {
                    if (txn.getLocation() != null && currentCountry.equalsIgnoreCase(txn.getLocation().getCountry())) {
                        hasHistoryInCountry = true;
                        break;
                    }
                }
            }

            details.put("highRiskCountry", currentCountry);
            details.put("customerHasHistoryInCountry", hasHistoryInCountry);

            if (!hasHistoryInCountry) {
                return 75;
            }
        }

        return 0;
    }

    private int checkDeviceIpMismatch(Transaction transaction, CustomerProfile customerProfile,
            Map<String, Object> details) {
        Location locationInfo = transaction.getLocation();

        if (locationInfo == null || locationInfo.getIp() == null) {
            return 0;
        }

        String currentIp = locationInfo.getIp();

        if (currentIp == null) {
            return 0;
        }

        boolean deviceUsedFromLocationBefore = false;
        boolean isNewCustomer = (customerProfile.getTransactionHistory() == null
                || customerProfile.getTransactionHistory().isEmpty());

        details.put("deviceUsedFromLocationBefore", deviceUsedFromLocationBefore);
        details.put("isNewCustomer", isNewCustomer);

        if (!deviceUsedFromLocationBefore) {
            if (isNewCustomer) {
                return 65;
            } else {
                return 40;
            }
        }

        return 0;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}