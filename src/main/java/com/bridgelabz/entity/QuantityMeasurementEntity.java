package com.bridgelabz.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private final long id;
    private final String operation;
    private final String result;
    private final String error;
    private final LocalDateTime createdAt;

    public QuantityMeasurementEntity(String operation, String result) {
        this(0, operation, result, null, LocalDateTime.now());
    }

    public QuantityMeasurementEntity(String operation, String result, String error) {
        this(0, operation, result, error, LocalDateTime.now());
    }

    public QuantityMeasurementEntity(
            long id,
            String operation,
            String result,
            String error,
            LocalDateTime createdAt) {
        this.id = id;
        this.operation = operation;
        this.result = result;
        this.error = error;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }

    public String getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
