package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // Same unit equality

    @Test
    void testFeetToFeet_SameValue() {
        assertTrue(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET)
                .equals(new QuantityMeasurementApp.Length(1.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }

    @Test
    void testInchToInch_SameValue() {
        assertTrue(new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES)
                .equals(new QuantityMeasurementApp.Length(12.0,
                        QuantityMeasurementApp.LengthUnit.INCHES)));
    }

    // Cross-unit equality

    @Test
    void testFeetToInch_EquivalentValue() {
        assertTrue(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET)
                .equals(new QuantityMeasurementApp.Length(12.0,
                        QuantityMeasurementApp.LengthUnit.INCHES)));
    }

    @Test
    void testInchToFeet_EquivalentValue() {
        assertTrue(new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES)
                .equals(new QuantityMeasurementApp.Length(1.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }

    // Different values

    @Test
    void testFeetDifferentValue() {
        assertFalse(new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET)
                .equals(new QuantityMeasurementApp.Length(2.0,
                        QuantityMeasurementApp.LengthUnit.FEET)));
    }

    // Null safety

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
                        QuantityMeasurementApp.LengthUnit.FEET);
        assertTrue(length.equals(length));
    }
}