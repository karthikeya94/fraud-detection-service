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

@Document(collection = "fraud_patterns")
public class FraudPattern {

    @Id
    private String id;

    @Field("patternId")
    private String patternId;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("type")
    private String type;

    @Field("detectionAlgorithm")
    private String detectionAlgorithm;

    @Field("frequency")
    private String frequency;

    @Field("avgConfidence")
    private Integer avgConfidence;

    @Field("affectedCustomers")
    private Integer affectedCustomers;

    @Field("trend")
    private String trend;

    @Field("firstDetected")
    private Instant firstDetected;

    @Field("lastDetected")
    private Instant lastDetected;

    @Field("relatedAlerts")
    private List<String> relatedAlerts;

    @Field("severity")
    private String severity;

    @Field("riskScore")
    private Integer riskScore;

    @Field("mitigationActions")
    private List<String> mitigationActions;

    @Field("falsePositiveRate")
    private Double falsePositiveRate;

    @Field("detectionTypes")
    private List<String> detectionTypes;

    public FraudPattern() {
    }

    public FraudPattern(String id, String patternId, String name, String description, String type,
            String detectionAlgorithm, String frequency, Integer avgConfidence, Integer affectedCustomers, String trend,
            Instant firstDetected, Instant lastDetected, List<String> relatedAlerts, String severity, Integer riskScore,
            List<String> mitigationActions, Double falsePositiveRate, List<String> detectionTypes) {
        this.id = id;
        this.patternId = patternId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.detectionAlgorithm = detectionAlgorithm;
        this.frequency = frequency;
        this.avgConfidence = avgConfidence;
        this.affectedCustomers = affectedCustomers;
        this.trend = trend;
        this.firstDetected = firstDetected;
        this.lastDetected = lastDetected;
        this.relatedAlerts = relatedAlerts;
        this.severity = severity;
        this.riskScore = riskScore;
        this.mitigationActions = mitigationActions;
        this.falsePositiveRate = falsePositiveRate;
        this.detectionTypes = detectionTypes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetectionAlgorithm() {
        return detectionAlgorithm;
    }

    public void setDetectionAlgorithm(String detectionAlgorithm) {
        this.detectionAlgorithm = detectionAlgorithm;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getAvgConfidence() {
        return avgConfidence;
    }

    public void setAvgConfidence(Integer avgConfidence) {
        this.avgConfidence = avgConfidence;
    }

    public Integer getAffectedCustomers() {
        return affectedCustomers;
    }

    public void setAffectedCustomers(Integer affectedCustomers) {
        this.affectedCustomers = affectedCustomers;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public Instant getFirstDetected() {
        return firstDetected;
    }

    public void setFirstDetected(Instant firstDetected) {
        this.firstDetected = firstDetected;
    }

    public Instant getLastDetected() {
        return lastDetected;
    }

    public void setLastDetected(Instant lastDetected) {
        this.lastDetected = lastDetected;
    }

    public List<String> getRelatedAlerts() {
        return relatedAlerts;
    }

    public void setRelatedAlerts(List<String> relatedAlerts) {
        this.relatedAlerts = relatedAlerts;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }

    public List<String> getMitigationActions() {
        return mitigationActions;
    }

    public void setMitigationActions(List<String> mitigationActions) {
        this.mitigationActions = mitigationActions;
    }

    public Double getFalsePositiveRate() {
        return falsePositiveRate;
    }

    public void setFalsePositiveRate(Double falsePositiveRate) {
        this.falsePositiveRate = falsePositiveRate;
    }

    public List<String> getDetectionTypes() {
        return detectionTypes;
    }

    public void setDetectionTypes(List<String> detectionTypes) {
        this.detectionTypes = detectionTypes;
    }

    public static FraudPatternBuilder builder() {
        return new FraudPatternBuilder();
    }

    public static class FraudPatternBuilder {
        private String id;
        private String patternId;
        private String name;
        private String description;
        private String type;
        private String detectionAlgorithm;
        private String frequency;
        private Integer avgConfidence;
        private Integer affectedCustomers;
        private String trend;
        private Instant firstDetected;
        private Instant lastDetected;
        private List<String> relatedAlerts;
        private String severity;
        private Integer riskScore;
        private List<String> mitigationActions;
        private Double falsePositiveRate;
        private List<String> detectionTypes;

        FraudPatternBuilder() {
        }

        public FraudPatternBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FraudPatternBuilder patternId(String patternId) {
            this.patternId = patternId;
            return this;
        }

        public FraudPatternBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FraudPatternBuilder description(String description) {
            this.description = description;
            return this;
        }

        public FraudPatternBuilder type(String type) {
            this.type = type;
            return this;
        }

        public FraudPatternBuilder detectionAlgorithm(String detectionAlgorithm) {
            this.detectionAlgorithm = detectionAlgorithm;
            return this;
        }

        public FraudPatternBuilder frequency(String frequency) {
            this.frequency = frequency;
            return this;
        }

        public FraudPatternBuilder avgConfidence(Integer avgConfidence) {
            this.avgConfidence = avgConfidence;
            return this;
        }

        public FraudPatternBuilder affectedCustomers(Integer affectedCustomers) {
            this.affectedCustomers = affectedCustomers;
            return this;
        }

        public FraudPatternBuilder trend(String trend) {
            this.trend = trend;
            return this;
        }

        public FraudPatternBuilder firstDetected(Instant firstDetected) {
            this.firstDetected = firstDetected;
            return this;
        }

        public FraudPatternBuilder lastDetected(Instant lastDetected) {
            this.lastDetected = lastDetected;
            return this;
        }

        public FraudPatternBuilder relatedAlerts(List<String> relatedAlerts) {
            this.relatedAlerts = relatedAlerts;
            return this;
        }

        public FraudPatternBuilder severity(String severity) {
            this.severity = severity;
            return this;
        }

        public FraudPatternBuilder riskScore(Integer riskScore) {
            this.riskScore = riskScore;
            return this;
        }

        public FraudPatternBuilder mitigationActions(List<String> mitigationActions) {
            this.mitigationActions = mitigationActions;
            return this;
        }

        public FraudPatternBuilder falsePositiveRate(Double falsePositiveRate) {
            this.falsePositiveRate = falsePositiveRate;
            return this;
        }

        public FraudPatternBuilder detectionTypes(List<String> detectionTypes) {
            this.detectionTypes = detectionTypes;
            return this;
        }

        public FraudPattern build() {
            return new FraudPattern(id, patternId, name, description, type, detectionAlgorithm, frequency,
                    avgConfidence, affectedCustomers, trend, firstDetected, lastDetected, relatedAlerts, severity,
                    riskScore, mitigationActions, falsePositiveRate, detectionTypes);
        }
    }
}