package com.bridgelabz.repository;

import com.bridgelabz.entity.OperationType;
import com.bridgelabz.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(OperationType operation);

    long countByOperationAndErrorFlagFalse(OperationType operation);
}
