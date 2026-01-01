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

@Document(collection = "merchant_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantData {

    @Id
    private String id;

    @Field("merchantId")
    private String merchantId;

    @Field("merchantName")
    private String merchantName;

    @Field("category")
    private String category;

    @Field("riskLevel")
    private String riskLevel;

    @Field("countriesOperated")
    private List<String> countriesOperated;

    @Field("highRisk")
    private Boolean highRisk;

    @Field("createdAt")
    private Instant createdAt;

    @Field("updatedAt")
    private Instant updatedAt;
}