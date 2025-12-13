package com.fraud.detection.service.repository;

import com.fraud.detection.service.model.CustomerProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerProfileRepository extends MongoRepository<CustomerProfile, String> {
    
    Optional<CustomerProfile> findByCustomerId(String customerId);
}