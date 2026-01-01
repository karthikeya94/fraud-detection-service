package com.fraud.detection.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document(collection = "fraud_rules")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
}