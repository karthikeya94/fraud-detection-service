package com.fraud.detection.service.service;

import com.fraud.detection.service.model.CustomerProfile;
import com.riskplatform.common.entity.Transaction;
import com.riskplatform.common.entity.DetectionResult;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VelocityFraudDetector {

    public DetectionResult detect(Transaction transaction, CustomerProfile customerProfile)
            throws FraudDetectionException {
        try {
            DetectionResult result = DetectionResult.builder()
                    .type(com.riskplatform.common.enums.FraudType.VELOCITY_FRAUD)
                    .status(com.riskplatform.common.enums.AlertStatus.NOT_DETECTED)
                    .confidence(0)
                    .details(new HashMap<>())
                    .build();

            if (customerProfile == null) {
                return result;
            }

            List<Transaction> transactionHistory = customerProfile.getTransactionHistory();
            if (transactionHistory == null || transactionHistory.isEmpty()) {
                return result;
            }

            int txnCount30Min = countTransactionsInTimeWindow(transactionHistory, transaction.getTimestamp(), 30);
            int txnCount24Hours = countTransactionsInTimeWindow(transactionHistory, transaction.getTimestamp(), 1440);
            BigDecimal amountSpikeRatio = calculateAmountSpikeRatio(transaction, customerProfile);

            Map<String, Object> details = result.getDetails();
            details.put("txnCount30Min", txnCount30Min);
            details.put("txnCount24Hours", txnCount24Hours);
            details.put("amountSpikeRatio", amountSpikeRatio);

            boolean condition1 = txnCount30Min > 10;
            boolean condition2 = txnCount24Hours > 50;
            boolean condition3 = amountSpikeRatio.compareTo(new BigDecimal("5")) > 0;

            if (condition1 || condition2 || condition3) {
                result.setStatus(com.riskplatform.common.enums.AlertStatus.DETECTED);

                int confidence = calculateConfidence(txnCount30Min, txnCount24Hours, amountSpikeRatio, customerProfile);
                result.setConfidence(confidence);

                if (confidence >= 80) {
                    result.setReason("High velocity fraud detected");
                } else if (confidence >= 60) {
                    result.setReason("Medium velocity fraud detected");
                } else if (confidence >= 40) {
                    result.setReason("Low velocity fraud detected");
                } else {
                    result.setReason("Minimal velocity fraud detected");
                }

                if (condition1) {
                    details.put("reason", "More than 10 transactions in 30 minutes");
                } else if (condition2) {
                    details.put("reason", "More than 50 transactions in 24 hours");
                } else if (condition3) {
                    details.put("reason", "Transaction amount increased 5x from average");
                }
            }

            return result;
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to perform velocity fraud detection: " + e.getMessage(), e);
        }
    }

    private int countTransactionsInTimeWindow(List<Transaction> transactions, Instant currentTime, int minutes) {
        Instant startTime = currentTime.minus(minutes, ChronoUnit.MINUTES);
        return (int) transactions.stream()
                .filter(txn -> txn.getTimestamp().isAfter(startTime) && txn.getTimestamp().isBefore(currentTime))
                .count();
    }

    private BigDecimal calculateAmountSpikeRatio(Transaction currentTransaction, CustomerProfile customerProfile) {
        BigDecimal averageAmount = customerProfile.getAverageTransactionAmount();
        if (averageAmount == null || averageAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal currentAmount = currentTransaction.getAmount();
        return currentAmount.divide(averageAmount, 2, java.math.RoundingMode.HALF_UP);
    }

    private int calculateConfidence(int txnCount30Min, int txnCount24Hours, BigDecimal amountSpikeRatio,
            CustomerProfile customerProfile) {
        // Get normal transaction count (average per 30 minutes)
        int normalTxnCount = getNormalTransactionCount(customerProfile);

        // Calculate factors
        double txnCountFactor = normalTxnCount > 0 ? (double) txnCount30Min / normalTxnCount : 0;
        double amountSpikeFactor = amountSpikeRatio.doubleValue() / 5.0; // 5x is the threshold
        double timeWindowDensity = calculateTimeWindowDensity(customerProfile, txnCount30Min);

        // Apply weights (40%, 30%, 30%)
        double confidence = (txnCountFactor * 0.4 + amountSpikeFactor * 0.3 + timeWindowDensity * 0.3) * 100;

        return Math.min(100, Math.max(0, (int) Math.round(confidence)));
    }

    private int getNormalTransactionCount(CustomerProfile customerProfile) {
        // Simplified calculation - in a real system, this would be more sophisticated
        List<Transaction> history = customerProfile.getTransactionHistory();
        if (history == null || history.isEmpty()) {
            return 1; // Default to 1 to avoid division by zero
        }

        // Calculate average transactions per day and convert to 30-min average
        long daysOfHistory = ChronoUnit.DAYS.between(
                history.get(history.size() - 1).getTimestamp(),
                history.get(0).getTimestamp()) + 1; // +1 to include both start and end days

        if (daysOfHistory <= 0) {
            return 1;
        }

        return Math.max(1, (int) Math.round((double) history.size() / (daysOfHistory * 48))); // 48 half-hour periods
                                                                                              // per day
    }

    private double calculateTimeWindowDensity(CustomerProfile customerProfile, int txnCount30Min) {
        // This is a simplified density calculation
        // In a real system, this would analyze the distribution of transactions within
        // the time window
        return Math.min(1.0, txnCount30Min / 20.0); // Assume 20 transactions in 30 min is high density
    }
}