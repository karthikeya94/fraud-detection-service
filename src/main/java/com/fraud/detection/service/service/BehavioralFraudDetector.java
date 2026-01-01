package com.fraud.detection.service.service;

import com.fraud.detection.service.model.CustomerProfile;
import com.riskplatform.common.entity.Transaction;
import com.riskplatform.common.model.DeviceInfo;
import com.riskplatform.common.entity.DetectionResult;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

@Service
public class BehavioralFraudDetector {

    // High-risk merchant categories
    private static final Set<String> HIGH_RISK_CATEGORIES = new HashSet<>(Arrays.asList(
            "CRYPTOCURRENCY", "GAMBLING", "DARK_WEB", "ADULT_CONTENT"));

    public DetectionResult detect(Transaction transaction, CustomerProfile customerProfile)
            throws FraudDetectionException {
        try {
            DetectionResult result = DetectionResult.builder()
                    .type(com.riskplatform.common.enums.FraudType.BEHAVIORAL_FRAUD)
                    .status(com.riskplatform.common.enums.AlertStatus.NOT_DETECTED)
                    .confidence(0)
                    .details(new HashMap<>())
                    .build();

            if (customerProfile == null) {
                return result;
            }

            Map<String, Object> details = result.getDetails();
            int baseConfidence = 0;

            int merchantScore = checkUnusualMerchant(transaction, customerProfile, details);
            baseConfidence += merchantScore;

            int amountScore = checkUnusualAmountPattern(transaction, customerProfile, details);
            baseConfidence += amountScore;

            int accountTakeoverScore = checkAccountTakeoverSignals(transaction, customerProfile, details);
            baseConfidence += accountTakeoverScore;

            int velocityScore = checkVelocityAnomaly(transaction, customerProfile, details);
            baseConfidence += velocityScore;

            int finalConfidence = Math.min(100, baseConfidence);
            result.setConfidence(finalConfidence);

            if (finalConfidence > 0) {
                result.setStatus(com.riskplatform.common.enums.AlertStatus.DETECTED);

                if (finalConfidence >= 75) {
                    result.setReason("High confidence behavioral fraud detected");
                } else if (finalConfidence >= 50) {
                    result.setReason("Medium confidence behavioral fraud detected");
                } else if (finalConfidence >= 30) {
                    result.setReason("Low confidence behavioral fraud detected");
                } else {
                    result.setReason("Minimal behavioral fraud detected");
                }
            }

            return result;
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to perform behavioral fraud detection: " + e.getMessage(), e);
        }
    }

    private int checkUnusualMerchant(Transaction transaction, CustomerProfile customerProfile,
            Map<String, Object> details) {
        String merchant = transaction.getMerchant();
        String merchantCategory = transaction.getMerchantCategory();

        if (merchant == null || merchantCategory == null) {
            return 0;
        }

        boolean merchantNewToCustomer = true;
        if (customerProfile.getPreferredMerchants() != null) {
            merchantNewToCustomer = !customerProfile.getPreferredMerchants().contains(merchant);
        }

        boolean isHighRiskCategory = HIGH_RISK_CATEGORIES.contains(merchantCategory.toUpperCase());

        boolean categoryNewToCustomer = true;
        if (customerProfile.getPreferredCategories() != null) {
            categoryNewToCustomer = !customerProfile.getPreferredCategories().contains(merchantCategory);
        }

        details.put("merchantNewToCustomer", merchantNewToCustomer);
        details.put("isHighRiskCategory", isHighRiskCategory);
        details.put("categoryNewToCustomer", categoryNewToCustomer);

        if (merchantNewToCustomer && isHighRiskCategory && categoryNewToCustomer) {
            return 30;
        } else if (merchantNewToCustomer && categoryNewToCustomer) {
            return 15;
        } else if (isHighRiskCategory) {
            return 10;
        }

        return 0;
    }

    private int checkUnusualAmountPattern(Transaction transaction, CustomerProfile customerProfile,
            Map<String, Object> details) {
        BigDecimal currentAmount = transaction.getAmount();
        BigDecimal averageAmount = customerProfile.getAverageTransactionAmount();

        if (currentAmount == null || averageAmount == null || averageAmount.compareTo(BigDecimal.ZERO) == 0) {
            return 0;
        }

        BigDecimal tenTimesAverage = averageAmount.multiply(new BigDecimal("10"));
        boolean isAmount10xAverage = currentAmount.compareTo(tenTimesAverage) >= 0;

        details.put("isAmount10xAverage", isAmount10xAverage);
        details.put("currentAmount", currentAmount);
        details.put("averageAmount", averageAmount);

        if (isAmount10xAverage) {
            return 25;
        }

        return 0;
    }

    private int checkAccountTakeoverSignals(Transaction transaction, CustomerProfile customerProfile,
            Map<String, Object> details) {
        DeviceInfo deviceInfo = transaction.getDevice();
        int takeoverSignals = 0;

        if (deviceInfo != null && Boolean.TRUE.equals(deviceInfo.getIsNewDevice())) {
            takeoverSignals++;
        }

        String merchant = transaction.getMerchant();
        if (merchant != null && customerProfile.getPreferredMerchants() != null) {
            boolean merchantNewToCustomer = !customerProfile.getPreferredMerchants().contains(merchant);
            if (merchantNewToCustomer) {
                takeoverSignals++;
            }
        }

        BigDecimal currentAmount = transaction.getAmount();
        BigDecimal averageAmount = customerProfile.getAverageTransactionAmount();
        if (currentAmount != null && averageAmount != null && averageAmount.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal twiceAverage = averageAmount.multiply(new BigDecimal("2"));
            if (currentAmount.compareTo(twiceAverage) > 0) {
                takeoverSignals++;
            }
        }

        details.put("accountTakeoverSignals", takeoverSignals);

        if (takeoverSignals > 2) {
            return 40;
        } else if (takeoverSignals == 2) {
            return 20;
        } else if (takeoverSignals == 1) {
            return 10;
        }

        return 0;
    }

    private int checkVelocityAnomaly(Transaction transaction, CustomerProfile customerProfile,
            Map<String, Object> details) {
        List<Transaction> transactionHistory = customerProfile.getTransactionHistory();
        if (transactionHistory == null || transactionHistory.isEmpty()) {
            return 0;
        }

        Instant currentTime = transaction.getTimestamp();
        Instant twoHoursAgo = currentTime.minus(2, ChronoUnit.HOURS);

        long recentTransactions = transactionHistory.stream()
                .filter(txn -> txn.getTimestamp().isAfter(twoHoursAgo) && txn.getTimestamp().isBefore(currentTime))
                .count();

        details.put("recentTransactionsIn2Hours", recentTransactions);

        if (recentTransactions >= 3) {
            if (transactionHistory.size() >= 3) {
                int lastIndex = transactionHistory.size() - 1;
                BigDecimal amount1 = transactionHistory.get(lastIndex).getAmount();
                BigDecimal amount2 = transactionHistory.get(lastIndex - 1).getAmount();
                BigDecimal amount3 = transactionHistory.get(lastIndex - 2).getAmount();

                if (amount1 != null && amount2 != null && amount3 != null) {
                    boolean escalating = amount1.compareTo(amount2) > 0 && amount2.compareTo(amount3) > 0;
                    boolean significantIncrease = amount1.compareTo(amount3.multiply(new BigDecimal("3"))) > 0;

                    if (escalating && significantIncrease) {
                        details.put("rapidEscalation", true);
                        return 15;
                    }
                }
            }
            return 10;
        }

        return 0;
    }
}