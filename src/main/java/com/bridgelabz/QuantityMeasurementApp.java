package com.bridgelabz;

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public Length(double value, LengthUnit unit) {
            if (!Double.isFinite(value))
                throw new IllegalArgumentException("Invalid value");
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
            return unit.convertToBaseUnit(value);
        }

        public Length convertTo(LengthUnit targetUnit) {
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = toBaseUnit();
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Length(converted, targetUnit);
        }

        public Length add(Length other) {
            return add(other, this.unit);
        }

        public Length add(Length other, LengthUnit targetUnit) {
            if (other == null)
                throw new IllegalArgumentException("Other length cannot be null");
            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double sumBase =
                    this.toBaseUnit() + other.toBaseUnit();

            double converted =
                    targetUnit.convertFromBaseUnit(sumBase);

            return new Length(converted, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Length)) return false;

            Length other = (Length) obj;

            return Math.abs(
                    this.toBaseUnit() - other.toBaseUnit()
            ) < EPSILON;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    /* =========================
       UC12 Generic Demonstration Methods
     ========================= */

    public static <U extends IMeasurable>
    Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
        return q1.subtract(q2);
    }

    public static <U extends IMeasurable>
    Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        return q1.subtract(q2, targetUnit);
    }

    public static <U extends IMeasurable>
    double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
        return q1.divide(q2);
    }

    /* =========================
       MAIN METHOD
     ========================= */

    public static void main(String[] args) {

        // Old Length class demo
        Length a = new Length(1.0, LengthUnit.FEET);
        Length b = new Length(12.0, LengthUnit.INCHES);

        System.out.println(a.convertTo(LengthUnit.INCHES));
        System.out.println(a.add(b, LengthUnit.FEET));
        System.out.println(b.equals(new Length(1.0, LengthUnit.YARDS)));

        System.out.println("----- UC12 Generic Operations -----");

        // Generic Quantity demo
        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println("Subtraction (implicit): "
                + demonstrateSubtraction(q1, q2));

        System.out.println("Subtraction (explicit INCHES): "
                + demonstrateSubtraction(q1, q2, LengthUnit.INCHES));

        System.out.println("Division: "
                + demonstrateDivision(q1, q2));
    }
}