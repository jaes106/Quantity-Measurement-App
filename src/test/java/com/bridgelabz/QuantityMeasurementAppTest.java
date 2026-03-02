package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // ---------------- FEET TESTS ----------------

    @Test
    void testFeetEquality_SameValue() {
        assertTrue(new QuantityMeasurementApp.Feet(1.0)
                .equals(new QuantityMeasurementApp.Feet(1.0)));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(new QuantityMeasurementApp.Feet(1.0)
                .equals(new QuantityMeasurementApp.Feet(2.0)));
    }

    @Test
    void testFeetEquality_NullComparison() {
        assertFalse(new QuantityMeasurementApp.Feet(1.0)
                .equals(null));
    }

    @Test
    void testFeetEquality_SameReference() {
        QuantityMeasurementApp.Feet feet =
                new QuantityMeasurementApp.Feet(1.0);
        assertTrue(feet.equals(feet));
    }

    @Test
    void testFeetEquality_DifferentClass() {
        assertFalse(new QuantityMeasurementApp.Feet(1.0)
                .equals(new QuantityMeasurementApp.Inch(12.0)));
    }

    // ---------------- INCH TESTS ----------------

    @Test
    void testInchEquality_SameValue() {
        assertTrue(new QuantityMeasurementApp.Inch(12.0)
                .equals(new QuantityMeasurementApp.Inch(12.0)));
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(new QuantityMeasurementApp.Inch(12.0)
                .equals(new QuantityMeasurementApp.Inch(24.0)));
    }

    @Test
    void testInchEquality_NullComparison() {
        assertFalse(new QuantityMeasurementApp.Inch(12.0)
                .equals(null));
    }

    @Test
    void testInchEquality_SameReference() {
        QuantityMeasurementApp.Inch inch =
                new QuantityMeasurementApp.Inch(12.0);
        assertTrue(inch.equals(inch));
    }

    @Test
    void testInchEquality_DifferentClass() {
        assertFalse(new QuantityMeasurementApp.Inch(12.0)
                .equals(new QuantityMeasurementApp.Feet(1.0)));
    }
}