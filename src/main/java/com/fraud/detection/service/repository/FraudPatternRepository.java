package com.fraud.detection.service.repository;

import com.riskplatform.common.entity.FraudPattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.Instant;

@Repository
public interface FraudPatternRepository extends MongoRepository<FraudPattern, String> {

    List<FraudPattern> findByLastDetectedAfter(Instant since);

    List<FraudPattern> findByTrend(String trend);

    List<FraudPattern> findBySeverity(String severity);

    List<FraudPattern> findByDetectionTypesContaining(String detectionType);

    List<FraudPattern> findByName(String name);
}