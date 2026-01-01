package com.fraud.detection.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.riskplatform.common.entity.DetectionResult;
import com.riskplatform.common.entity.FraudFlag;
import com.riskplatform.common.entity.CustomerRiskContext;
import com.riskplatform.common.entity.RequiredAction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FraudAnalysisResult {

    private String transactionId;
    private String fraudAlertId;
    private Integer overallFraudConfidence;
    private String status;
    private String recommendedAction;
    private List<DetectionResult> detectionTypes;
    private List<FraudFlag> fraudFlags;
    private CustomerRiskContext customerRiskContext;
    private RequiredAction requiredAction;
}