package com.bridgelabz.dto;

import com.bridgelabz.entity.OperationType;

import java.time.LocalDateTime;

public class QuantityMeasurementDTO {

    private Long id;
    private OperationType operation;
    private Object result;
    private String error;
    private LocalDateTime createdAt;

    public QuantityMeasurementDTO() {
    }

    public QuantityMeasurementDTO(
            Long id,
            OperationType operation,
            Object result,
            String error,
            LocalDateTime createdAt) {
        this.id = id;
        this.operation = operation;
        this.result = result;
        this.error = error;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public OperationType getOperation() {
        return operation;
    }

    public Object getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
