package com.bridgelabz.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class QuantityDTO {

    @NotNull(message = "Value is required")
    private Double value;

    @NotBlank(message = "Unit is required")
    @Pattern(regexp = "^[A-Z_]+$", message = "Unit must be an enum-style uppercase value")
    private String unit;

    @NotBlank(message = "Measurement type is required")
    @Pattern(regexp = "length|weight|volume|temperature",
            message = "Measurement type must be length, weight, volume, or temperature")
    private String measurementType;

    public QuantityDTO() {}

    public QuantityDTO(Double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    @AssertTrue(message = "Value must be a finite number")
    public boolean isFiniteValue() {
        return value != null && Double.isFinite(value);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
