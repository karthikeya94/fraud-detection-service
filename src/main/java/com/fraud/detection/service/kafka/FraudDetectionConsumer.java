package com.fraud.detection.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraud.detection.service.model.Transaction;
import com.fraud.detection.service.service.FraudAnalysisService;
import com.fraud.detection.service.service.FraudDetectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FraudDetectionConsumer {
    
    @Autowired
    private FraudAnalysisService fraudAnalysisService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @KafkaListener(topics = "transaction-validated", groupId = "fraud-detection-service")
    public void handleTransactionValidated(String message) {
        try {
            Transaction transaction = objectMapper.readValue(message, Transaction.class);
            fraudAnalysisService.analyzeTransaction(transaction);
        } catch (FraudDetectionException e) {
            log.error("Fraud detection error processing transaction-validated message: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error processing transaction-validated message: {}", e.getMessage());
        }
    }
    
    @KafkaListener(topics = "risk-score-calculated", groupId = "fraud-detection-service")
    public void handleRiskScoreCalculated(String message) {
        try {
            Transaction transaction = objectMapper.readValue(message, Transaction.class);
            fraudAnalysisService.analyzeTransaction(transaction);
        } catch (FraudDetectionException e) {
            log.error("Fraud detection error processing risk-score-calculated message: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error processing risk-score-calculated message: {}", e.getMessage());
        }
    }
}