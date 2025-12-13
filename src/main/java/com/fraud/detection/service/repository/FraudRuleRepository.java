package com.fraud.detection.service.repository;

import com.fraud.detection.service.model.FraudRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.Instant;

@Repository
public interface FraudRuleRepository extends MongoRepository<FraudRule, String> {

    List<FraudRule> findByEnabledTrueAndEffectiveDateBeforeAndExpirationDateAfter(
            Instant effectiveDate, Instant expirationDate);

    List<FraudRule> findByRuleTypeAndEnabledTrue(String ruleType);

    List<FraudRule> findByTagsContaining(String tag);
}