package com.fraud.detection.service.repository;

import com.fraud.detection.service.model.FraudAlert;
import com.fraud.detection.service.model.enums.AlertStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface FraudAlertRepository extends MongoRepository<FraudAlert, String> {
    
    Optional<FraudAlert> findByTransactionId(String transactionId);
    
    List<FraudAlert> findByCustomerId(String customerId);
    
    List<FraudAlert> findByStatus(AlertStatus status);
    
    List<FraudAlert> findByCustomerIdAndStatus(String customerId, AlertStatus status);
    
    List<FraudAlert> findByRaisedAtBetween(java.time.Instant startDate, java.time.Instant endDate);
    
    List<FraudAlert> findByStatusAndReviewedAtBefore(AlertStatus status, java.time.Instant cutoffTime);
    
    List<FraudAlert> findByStatusAndUpdatedAtBefore(AlertStatus status, java.time.Instant cutoffTime);
}