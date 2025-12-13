package com.fraud.detection.service.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.Instant;
import java.util.List;

@Document(collection = "fraud_rules")
public class FraudRule {

    @Id
    private String id;

    @Field("ruleName")
    private String ruleName;

    @Field("ruleType")
    private String ruleType;

    @Field("condition")
    private String condition;

    @Field("action")
    private String action;

    @Field("confidence")
    private Integer confidence;

    @Field("enabled")
    private Boolean enabled;

    @Field("effectiveDate")
    private Instant effectiveDate;

    @Field("expirationDate")
    private Instant expirationDate;

    @Field("priority")
    private Integer priority;

    @Field("tags")
    private List<String> tags;

    @Field("description")
    private String description;

    @Field("createdBy")
    private String createdBy;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedAt")
    private Instant updatedAt;

    @Field("version")
    private Integer version;

    public FraudRule() {
    }

    public FraudRule(String id, String ruleName, String ruleType, String condition, String action, Integer confidence,
            Boolean enabled, Instant effectiveDate, Instant expirationDate, Integer priority, List<String> tags,
            String description, String createdBy, Instant createdAt, Instant updatedAt, Integer version) {
        this.id = id;
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.condition = condition;
        this.action = action;
        this.confidence = confidence;
        this.enabled = enabled;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.priority = priority;
        this.tags = tags;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public static FraudRuleBuilder builder() {
        return new FraudRuleBuilder();
    }

    public static class FraudRuleBuilder {
        private String id;
        private String ruleName;
        private String ruleType;
        private String condition;
        private String action;
        private Integer confidence;
        private Boolean enabled;
        private Instant effectiveDate;
        private Instant expirationDate;
        private Integer priority;
        private List<String> tags;
        private String description;
        private String createdBy;
        private Instant createdAt;
        private Instant updatedAt;
        private Integer version;

        FraudRuleBuilder() {
        }

        public FraudRuleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FraudRuleBuilder ruleName(String ruleName) {
            this.ruleName = ruleName;
            return this;
        }

        public FraudRuleBuilder ruleType(String ruleType) {
            this.ruleType = ruleType;
            return this;
        }

        public FraudRuleBuilder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public FraudRuleBuilder action(String action) {
            this.action = action;
            return this;
        }

        public FraudRuleBuilder confidence(Integer confidence) {
            this.confidence = confidence;
            return this;
        }

        public FraudRuleBuilder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public FraudRuleBuilder effectiveDate(Instant effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public FraudRuleBuilder expirationDate(Instant expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public FraudRuleBuilder priority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public FraudRuleBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public FraudRuleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FraudRuleBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public FraudRuleBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FraudRuleBuilder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public FraudRuleBuilder version(Integer version) {
            this.version = version;
            return this;
        }

        public FraudRule build() {
            return new FraudRule(id, ruleName, ruleType, condition, action, confidence, enabled, effectiveDate,
                    expirationDate, priority, tags, description, createdBy, createdAt, updatedAt, version);
        }
    }
}