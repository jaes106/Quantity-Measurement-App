package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testAddition_ExplicitTarget_Feet() {
        var a = new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = a.add(b,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTarget_Inches() {
        var a = new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = a.add(b,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(QuantityMeasurementApp.LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testAddition_ExplicitTarget_Yards() {
        var a = new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var result = a.add(b,
                QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(0.666666, result.getValue(), 1e-3);
    }

    @Test
    void testAddition_Commutative_WithTarget() {
        var a = new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var r1 = a.add(b,
                QuantityMeasurementApp.LengthUnit.YARDS);

        var r2 = b.add(a,
                QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
    }

    @Test
    void testAddition_NullTarget_Throws() {
        var a = new QuantityMeasurementApp.Length(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.Length(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class,
                () -> a.add(b, null));
    }
}