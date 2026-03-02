package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testFeetToInches() {
        assertEquals(12.0,
                QuantityMeasurementApp.convert(
                        1.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    void testInchesToFeet() {
        assertEquals(2.0,
                QuantityMeasurementApp.convert(
                        24.0,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        QuantityMeasurementApp.LengthUnit.FEET),
                EPSILON);
    }

    @Test
    void testYardsToInches() {
        assertEquals(36.0,
                QuantityMeasurementApp.convert(
                        1.0,
                        QuantityMeasurementApp.LengthUnit.YARDS,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    void testCentimetersToInches() {
        assertEquals(1.0,
                QuantityMeasurementApp.convert(
                        2.54,
                        QuantityMeasurementApp.LengthUnit.CENTIMETERS,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    void testRoundTripConversion() {
        double original = 5.0;

        double converted =
                QuantityMeasurementApp.convert(
                        original,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES);

        double back =
                QuantityMeasurementApp.convert(
                        converted,
                        QuantityMeasurementApp.LengthUnit.INCHES,
                        QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(original, back, EPSILON);
    }

    @Test
    void testZeroConversion() {
        assertEquals(0.0,
                QuantityMeasurementApp.convert(
                        0.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    void testNegativeConversion() {
        assertEquals(-12.0,
                QuantityMeasurementApp.convert(
                        -1.0,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                EPSILON);
    }

    @Test
    void testInvalidUnitThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.convert(
                        1.0,
                        null,
                        QuantityMeasurementApp.LengthUnit.FEET));
    }

    @Test
    void testInvalidValueThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityMeasurementApp.convert(
                        Double.NaN,
                        QuantityMeasurementApp.LengthUnit.FEET,
                        QuantityMeasurementApp.LengthUnit.INCHES));
    }
}