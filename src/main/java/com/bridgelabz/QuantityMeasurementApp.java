package com.bridgelabz;

public class QuantityMeasurementApp {

    // UC2 - Feet Class
    public static class Feet {
        private final double value;

        public Feet(double value) {
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // UC2 - Inch Class
    public static class Inch {
        private final double value;

        public Inch(double value) {
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (this.getClass() != obj.getClass()) return false;

            Inch other = (Inch) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }
}