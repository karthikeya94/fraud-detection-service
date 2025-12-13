package com.fraud.detection.service.service;

import com.fraud.detection.service.model.FraudRule;
import com.fraud.detection.service.repository.FraudRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class FraudRuleService {

    @Autowired
    private FraudRuleRepository fraudRuleRepository;

    public List<FraudRule> getActiveRules() throws FraudDetectionException {
        try {
            Instant now = Instant.now();
            return fraudRuleRepository.findByEnabledTrueAndEffectiveDateBeforeAndExpirationDateAfter(now, now);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve active fraud rules: " + e.getMessage(), e);
        }
    }

    public List<FraudRule> getRulesByType(String ruleType) throws FraudDetectionException {
        try {
            return fraudRuleRepository.findByRuleTypeAndEnabledTrue(ruleType);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud rules by type: " + e.getMessage(), e);
        }
    }

    public List<FraudRule> getRulesByTag(String tag) throws FraudDetectionException {
        try {
            return fraudRuleRepository.findByTagsContaining(tag);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud rules by tag: " + e.getMessage(), e);
        }
    }

    public FraudRule addRule(FraudRule fraudRule) throws FraudDetectionException {
        try {
            fraudRule.setCreatedAt(Instant.now());
            fraudRule.setUpdatedAt(Instant.now());
            fraudRule.setVersion(1);

            return fraudRuleRepository.save(fraudRule);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to add fraud rule: " + e.getMessage(), e);
        }
    }

    public FraudRule updateRule(String ruleId, FraudRule updatedRule) throws FraudDetectionException {
        try {
            Optional<FraudRule> existingRuleOpt = fraudRuleRepository.findById(ruleId);

            if (existingRuleOpt.isPresent()) {
                FraudRule existingRule = existingRuleOpt.get();

                existingRule.setRuleName(updatedRule.getRuleName());
                existingRule.setRuleType(updatedRule.getRuleType());
                existingRule.setCondition(updatedRule.getCondition());
                existingRule.setAction(updatedRule.getAction());
                existingRule.setConfidence(updatedRule.getConfidence());
                existingRule.setEnabled(updatedRule.getEnabled());
                existingRule.setEffectiveDate(updatedRule.getEffectiveDate());
                existingRule.setExpirationDate(updatedRule.getExpirationDate());
                existingRule.setPriority(updatedRule.getPriority());
                existingRule.setTags(updatedRule.getTags());
                existingRule.setDescription(updatedRule.getDescription());
                existingRule.setUpdatedAt(Instant.now());
                existingRule.setVersion(existingRule.getVersion() + 1);

                return fraudRuleRepository.save(existingRule);
            } else {
                throw new IllegalArgumentException("Fraud rule not found with ID: " + ruleId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to update fraud rule: " + e.getMessage(), e);
        }
    }

    public void deleteRule(String ruleId) throws FraudDetectionException {
        try {
            fraudRuleRepository.deleteById(ruleId);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to delete fraud rule: " + e.getMessage(), e);
        }
    }

    public FraudRule setRuleEnabled(String ruleId, boolean enabled) throws FraudDetectionException {
        try {
            Optional<FraudRule> ruleOpt = fraudRuleRepository.findById(ruleId);

            if (ruleOpt.isPresent()) {
                FraudRule rule = ruleOpt.get();
                rule.setEnabled(enabled);
                rule.setUpdatedAt(Instant.now());
                rule.setVersion(rule.getVersion() + 1);

                return fraudRuleRepository.save(rule);
            } else {
                throw new IllegalArgumentException("Fraud rule not found with ID: " + ruleId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to set fraud rule enabled status: " + e.getMessage(), e);
        }
    }
}