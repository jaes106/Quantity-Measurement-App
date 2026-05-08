package com.bridgelabz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;

public class QuantityInputDTO {

    @Valid
    private QuantityDTO quantity;

    @Valid
    private QuantityDTO firstQuantity;

    @Valid
    private QuantityDTO secondQuantity;

    @Pattern(regexp = "^[A-Z_]+$", message = "Target unit must be an enum-style uppercase value")
    private String targetUnit;

    public QuantityDTO getQuantity() {
        return quantity;
    }

    public void setQuantity(QuantityDTO quantity) {
        this.quantity = quantity;
    }

    public QuantityDTO getFirstQuantity() {
        return firstQuantity;
    }

    public void setFirstQuantity(QuantityDTO firstQuantity) {
        this.firstQuantity = firstQuantity;
    }

    public QuantityDTO getSecondQuantity() {
        return secondQuantity;
    }

    public void setSecondQuantity(QuantityDTO secondQuantity) {
        this.secondQuantity = secondQuantity;
    }

    public String getTargetUnit() {
        return targetUnit;
    }

    public void setTargetUnit(String targetUnit) {
        this.targetUnit = targetUnit;
    }

    @AssertTrue(message = "Request must include either quantity or both firstQuantity and secondQuantity")
    public boolean isOperationInputPresent() {
        return quantity != null || (firstQuantity != null && secondQuantity != null);
    }
}
