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

        public Length convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double baseValue = toBaseUnit();
            double converted = baseValue / targetUnit.getFactor();
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

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");
        if (source == null || target == null)
            throw new IllegalArgumentException("Unit cannot be null");

        double baseValue = value * source.getFactor();
        return baseValue / target.getFactor();
    }

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static Length demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        double converted = convert(value, from, to);
        return new Length(converted, to);
    }

    public static Length demonstrateLengthConversion(Length length,
                                                     LengthUnit toUnit) {
        return length.convertTo(toUnit);
    }

    public static void main(String[] args) {
        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));
        System.out.println(convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));
    }
}