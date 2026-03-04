package com.bridgelabz;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;
    private static final double EPSILON = 1e-4;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    /* =========================
       EQUALITY
     ========================= */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = ((IMeasurable) other.unit).convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }

    /* =========================
       CONVERSION
     ========================= */

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(round(converted), targetUnit);
    }

    /* =========================
       ADDITION (UC11)
     ========================= */

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateOperation(other, targetUnit);

        double baseResult =
                unit.convertToBaseUnit(this.value) +
                        other.unit.convertToBaseUnit(other.value);

        double finalValue = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(finalValue), targetUnit);
    }

    /* =========================
       SUBTRACTION (UC12)
     ========================= */

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperation(other, targetUnit);

        double baseResult =
                unit.convertToBaseUnit(this.value) -
                        other.unit.convertToBaseUnit(other.value);

        double finalValue = targetUnit.convertFromBaseUnit(baseResult);

        return new Quantity<>(round(finalValue), targetUnit);
    }

    /* =========================
       DIVISION (UC12)
     ========================= */

    public double divide(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category division not allowed");

        double divisorBase = other.unit.convertToBaseUnit(other.value);

        if (Math.abs(divisorBase) < EPSILON)
            throw new ArithmeticException("Division by zero");

        double base1 = unit.convertToBaseUnit(this.value);

        return base1 / divisorBase;
    }

    /* =========================
       VALIDATION
     ========================= */

    private void validateOperation(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Quantity cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category operation not allowed");
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}