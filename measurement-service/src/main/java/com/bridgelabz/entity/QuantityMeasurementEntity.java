package com.bridgelabz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private OperationType operation;

    @Column(length = 255)
    private String result;

    @Column(length = 255)
    private String error;

    @Column(nullable = false)
    private boolean errorFlag;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public QuantityMeasurementEntity(OperationType operation, String result) {
        this.operation = operation;
        this.result = result;
    }

    public QuantityMeasurementEntity(OperationType operation, String result, String error) {
        this.operation = operation;
        this.result = result;
        this.error = error;
        this.errorFlag = error != null;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
