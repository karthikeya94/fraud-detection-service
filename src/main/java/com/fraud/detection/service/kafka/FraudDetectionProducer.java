package com.fraud.detection.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riskplatform.common.entity.FraudAlert;
import com.fraud.detection.service.service.FraudDetectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FraudDetectionProducer {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    public void sendFraudAlertRaised(FraudAlert fraudAlert) throws FraudDetectionException {
        try {
            String message = objectMapper.writeValueAsString(fraudAlert);
            kafkaTemplate.send("fraud-alert-raised", fraudAlert.getCustomerId(), message);
        } catch (JsonProcessingException e) {
            log.error("Error serializing fraud raised alert: {}", e.getMessage());
            throw new FraudDetectionException("Failed to serialize fraud alert for fraud-alert-raised topic: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error sending fraud raised alert to Kafka: {}", e.getMessage());
            throw new FraudDetectionException("Failed to send fraud alert to fraud-alert-raised topic: " + e.getMessage(), e);
        }
    }
    
    public void sendFraudAlertConfirmed(FraudAlert fraudAlert) throws FraudDetectionException {
        try {
            String message = objectMapper.writeValueAsString(fraudAlert);
            kafkaTemplate.send("fraud-alert-confirmed", fraudAlert.getCustomerId(), message);
        } catch (JsonProcessingException e) {
            log.error("Error serializing fraud  confirmed alert: {}", e.getMessage());
            throw new FraudDetectionException("Failed to serialize fraud alert for fraud-alert-confirmed topic: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error sending fraud confirmed alert to Kafka: {}", e.getMessage());
            throw new FraudDetectionException("Failed to send fraud alert to fraud-alert-confirmed topic: " + e.getMessage(), e);
        }
    }
    
    public void sendFraudAlertDismissed(FraudAlert fraudAlert) throws FraudDetectionException {
        try {
            String message = objectMapper.writeValueAsString(fraudAlert);
            kafkaTemplate.send("fraud-alert-dismissed", fraudAlert.getCustomerId(), message);
        } catch (JsonProcessingException e) {
            log.error("Error serializing fraud alert: {}", e.getMessage());
            throw new FraudDetectionException("Failed to serialize fraud alert for fraud-alert-dismissed topic: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error sending fraud alert to Kafka: {}", e.getMessage());
            throw new FraudDetectionException("Failed to send fraud alert to fraud-alert-dismissed topic: " + e.getMessage(), e);
        }
    }

}