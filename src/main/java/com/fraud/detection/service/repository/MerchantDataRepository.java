package com.fraud.detection.service.repository;

import com.fraud.detection.service.model.MerchantData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantDataRepository extends MongoRepository<MerchantData, String> {
    
    Optional<MerchantData> findByMerchantId(String merchantId);
    
    List<MerchantData> findByCategory(String category);
    
    List<MerchantData> findByRiskLevel(String riskLevel);
    
    List<MerchantData> findByHighRiskTrue();
}