package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // Same Unit Equality

    @Test
    void testFeetToFeet() {
        assertTrue(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET)
                .equals(new QuantityMeasurementApp.Length(1.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }

    @Test
    void testYardToYard() {
        assertTrue(new QuantityMeasurementApp.Length(2.0,
                QuantityMeasurementApp.LengthUnit.YARDS)
                .equals(new QuantityMeasurementApp.Length(2.0,
                        QuantityMeasurementApp.LengthUnit.YARDS)));
    }

    @Test
    void testCmToCm() {
        assertTrue(new QuantityMeasurementApp.Length(2.0,
                QuantityMeasurementApp.LengthUnit.CENTIMETERS)
                .equals(new QuantityMeasurementApp.Length(2.0,
                        QuantityMeasurementApp.LengthUnit.CENTIMETERS)));
    }

    // Cross Unit Equality

    @Test
    void testYardToFeet() {
        assertTrue(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.YARDS)
                .equals(new QuantityMeasurementApp.Length(3.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }

    @Test
    void testYardToInches() {
        assertTrue(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.YARDS)
                .equals(new QuantityMeasurementApp.Length(36.0,
                        QuantityMeasurementApp.LengthUnit.INCHES)));
    }

    @Test
    void testCmToInches() {
        assertTrue(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.CENTIMETERS)
                .equals(new QuantityMeasurementApp.Length(0.393701,
                        QuantityMeasurementApp.LengthUnit.INCHES)));
    }

    // Transitive Property

    @Test
    void testTransitiveProperty() {
        QuantityMeasurementApp.Length yard =
                new QuantityMeasurementApp.Length(1.0,
                        QuantityMeasurementApp.LengthUnit.YARDS);

        QuantityMeasurementApp.Length feet =
                new QuantityMeasurementApp.Length(3.0,
                        QuantityMeasurementApp.LengthUnit.FEET);

        QuantityMeasurementApp.Length inches =
                new QuantityMeasurementApp.Length(36.0,
                        QuantityMeasurementApp.LengthUnit.INCHES);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yard.equals(inches));
    }

    // Null Safety

    @Test
    void testNullComparison() {
        assertFalse(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET)
                .equals(null));
    }

    @Test
    void testSameReference() {
        QuantityMeasurementApp.Length length =
                new QuantityMeasurementApp.Length(1.0,
                        QuantityMeasurementApp.LengthUnit.YARDS);
        assertTrue(length.equals(length));
    }

    // Different Values

    @Test
    void testDifferentValues() {
        assertFalse(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.YARDS)
                .equals(new QuantityMeasurementApp.Length(2.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }
}