package com.fraud.detection.service.service;

import com.fraud.detection.service.model.FraudRule;
import com.fraud.detection.service.client.MongoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FraudRuleService {

    @Autowired
    private MongoServiceClient mongoServiceClient;

    public List<FraudRule> getActiveRules() throws FraudDetectionException {
        try {
            Instant now = Instant.now();
            List<com.riskplatform.common.entity.FraudRule> commonRules = mongoServiceClient.findActiveFraudRules(now, now);
            return commonRules.stream().map(this::convertToLocalModel).collect(Collectors.toList());
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve active fraud rules: " + e.getMessage(), e);
        }
    }

    public List<FraudRule> getRulesByType(String ruleType) throws FraudDetectionException {
        try {
            List<com.riskplatform.common.entity.FraudRule> commonRules = mongoServiceClient.findFraudRulesByRuleTypeAndEnabledTrue(ruleType);
            return commonRules.stream().map(this::convertToLocalModel).collect(Collectors.toList());
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud rules by type: " + e.getMessage(), e);
        }
    }

    public List<FraudRule> getRulesByTag(String tag) throws FraudDetectionException {
        try {
            List<com.riskplatform.common.entity.FraudRule> commonRules = mongoServiceClient.findFraudRulesByTagsContaining(tag);
            return commonRules.stream().map(this::convertToLocalModel).collect(Collectors.toList());
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to retrieve fraud rules by tag: " + e.getMessage(), e);
        }
    }

    public FraudRule addRule(FraudRule fraudRule) throws FraudDetectionException {
        try {
            fraudRule.setCreatedAt(Instant.now());
            fraudRule.setUpdatedAt(Instant.now());
            fraudRule.setVersion(1);

            com.riskplatform.common.entity.FraudRule commonRule = convertToCommonModel(fraudRule);
            com.riskplatform.common.entity.FraudRule savedCommonRule = mongoServiceClient.saveFraudRule(commonRule);
            return convertToLocalModel(savedCommonRule);
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to add fraud rule: " + e.getMessage(), e);
        }
    }

    public FraudRule updateRule(String ruleId, FraudRule updatedRule) throws FraudDetectionException {
        try {
            Optional<com.riskplatform.common.entity.FraudRule> existingRuleOpt = mongoServiceClient.findFraudRuleById(ruleId);

            if (existingRuleOpt.isPresent()) {
                com.riskplatform.common.entity.FraudRule existingRule = existingRuleOpt.get();

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

                com.riskplatform.common.entity.FraudRule savedRule = mongoServiceClient.saveFraudRule(existingRule);
                return convertToLocalModel(savedRule);
            } else {
                throw new IllegalArgumentException("Fraud rule not found with ID: " + ruleId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to update fraud rule: " + e.getMessage(), e);
        }
    }

    public void deleteRule(String ruleId) throws FraudDetectionException {
        try {
            Optional<com.riskplatform.common.entity.FraudRule> existingRuleOpt = mongoServiceClient.findFraudRuleById(ruleId);
            if (existingRuleOpt.isPresent()) {
                com.riskplatform.common.entity.FraudRule existingRule = existingRuleOpt.get();
                existingRule.setEnabled(false);
                existingRule.setUpdatedAt(Instant.now());
                mongoServiceClient.saveFraudRule(existingRule);
            } else {
                throw new IllegalArgumentException("Fraud rule not found with ID: " + ruleId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to delete fraud rule: " + e.getMessage(), e);
        }
    }

    public FraudRule setRuleEnabled(String ruleId, boolean enabled) throws FraudDetectionException {
        try {
            Optional<com.riskplatform.common.entity.FraudRule> existingRuleOpt = mongoServiceClient.findFraudRuleById(ruleId);
            if (existingRuleOpt.isPresent()) {
                com.riskplatform.common.entity.FraudRule existingRule = existingRuleOpt.get();
                existingRule.setEnabled(enabled);
                existingRule.setUpdatedAt(Instant.now());
                com.riskplatform.common.entity.FraudRule savedRule = mongoServiceClient.saveFraudRule(existingRule);
                return convertToLocalModel(savedRule);
            } else {
                throw new IllegalArgumentException("Fraud rule not found with ID: " + ruleId);
            }
        } catch (Exception e) {
            throw new FraudDetectionException("Failed to set fraud rule enabled status: " + e.getMessage(), e);
        }
    }

    private FraudRule convertToLocalModel(com.riskplatform.common.entity.FraudRule commonRule) {
        FraudRule localRule = new FraudRule();
        localRule.setId(commonRule.getId());
        localRule.setRuleName(commonRule.getRuleName());
        localRule.setRuleType(commonRule.getRuleType());
        localRule.setCondition(commonRule.getCondition());
        localRule.setAction(commonRule.getAction());
        localRule.setConfidence(commonRule.getConfidence());
        localRule.setEnabled(commonRule.getEnabled());
        localRule.setEffectiveDate(commonRule.getEffectiveDate());
        localRule.setExpirationDate(commonRule.getExpirationDate());
        localRule.setPriority(commonRule.getPriority());
        localRule.setTags(commonRule.getTags());
        localRule.setDescription(commonRule.getDescription());
        localRule.setCreatedAt(commonRule.getCreatedAt());
        localRule.setUpdatedAt(commonRule.getUpdatedAt());
        localRule.setVersion(commonRule.getVersion());
        return localRule;
    }

    private com.riskplatform.common.entity.FraudRule convertToCommonModel(FraudRule localRule) {
        return com.riskplatform.common.entity.FraudRule.builder()
                .id(localRule.getId())
                .ruleName(localRule.getRuleName())
                .ruleType(localRule.getRuleType())
                .condition(localRule.getCondition())
                .action(localRule.getAction())
                .confidence(localRule.getConfidence())
                .enabled(localRule.getEnabled())
                .effectiveDate(localRule.getEffectiveDate())
                .expirationDate(localRule.getExpirationDate())
                .priority(localRule.getPriority())
                .tags(localRule.getTags())
                .description(localRule.getDescription())
                .createdAt(localRule.getCreatedAt())
                .updatedAt(localRule.getUpdatedAt())
                .version(localRule.getVersion())
                .build();
    }
}