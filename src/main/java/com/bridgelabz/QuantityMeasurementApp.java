package com.bridgelabz;

import java.util.Scanner;

public class QuantityMeasurementApp {

    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first value in feet:");
        String input1 = scanner.nextLine();

        System.out.println("Enter second value in feet:");
        String input2 = scanner.nextLine();

        try {

            double value1 = Double.parseDouble(input1);
            double value2 = Double.parseDouble(input2);

            Feet feet1 = new Feet(value1);
            Feet feet2 = new Feet(value2);

            boolean result = feet1.equals(feet2);

            System.out.println("Equal: " + result);

        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        }
    }
}