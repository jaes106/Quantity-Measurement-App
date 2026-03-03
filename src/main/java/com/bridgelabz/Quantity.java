package com.bridgelabz;

import java.util.Objects;

public final class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-4;

    private final double value;
    private final U unit;

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

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue);

        return new Quantity<>(convertedValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Cannot add null quantity");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseSum =
                this.unit.convertToBaseUnit(this.value) +
                        other.unit.convertToBaseUnit(other.value);

        double resultValue = targetUnit.convertFromBaseUnit(baseSum);

        return new Quantity<>(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!this.unit.getClass().equals(other.unit.getClass()))
            return false;

        double baseThis = this.unit.convertToBaseUnit(this.value);
        double baseOther = other.unit.convertToBaseUnit(other.value);

        return Math.abs(baseThis - baseOther) < EPSILON;
    }

    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(baseValue / EPSILON));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}