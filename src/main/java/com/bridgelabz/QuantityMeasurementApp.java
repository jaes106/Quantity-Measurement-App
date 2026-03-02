package com.bridgelabz;

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public Length(double value, LengthUnit unit) {
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid numeric value");
            if (unit == null)
                throw new IllegalArgumentException("Unit cannot be null");

            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        private double toBaseUnit() {
            return value * unit.getFactor();
        }

        private static double fromBaseUnit(double baseValue, LengthUnit target) {
            return baseValue / target.getFactor();
        }

        // UC6 – implicit (unit of first operand)
        public Length add(Length other) {
            if (other == null)
                throw new IllegalArgumentException("Length cannot be null");

            return add(other, this.unit);
        }

        // UC7 – explicit target unit
        public Length add(Length other, LengthUnit targetUnit) {
            if (other == null)
                throw new IllegalArgumentException("Length cannot be null");
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseSum = this.toBaseUnit() + other.toBaseUnit();
            double converted = fromBaseUnit(baseSum, targetUnit);

            return new Length(converted, targetUnit);
        }

        public Length convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = toBaseUnit();
            double converted = fromBaseUnit(base, targetUnit);
            return new Length(converted, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Length)) return false;

            Length other = (Length) obj;
            return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < EPSILON;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }
}