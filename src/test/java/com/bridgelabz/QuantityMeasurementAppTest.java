package com.bridgelabz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    // ---------------- LENGTH TESTS ----------------

    @Test
    void testFeetEquality() {
        var a = new Quantity<>(1.0, LengthUnit.FEET);
        var b = new Quantity<>(1.0, LengthUnit.FEET);
        assertTrue(a.equals(b));
    }

    @Test
    void testFeetInchesEquality() {
        var a = new Quantity<>(1.0, LengthUnit.FEET);
        var b = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(a.equals(b));
    }

    @Test
    void testYardEqualsThreeFeet() {
        var yard = new Quantity<>(1.0, LengthUnit.YARDS);
        var feet = new Quantity<>(3.0, LengthUnit.FEET);
        assertTrue(yard.equals(feet));
    }

    @Test
    void testCentimeterEqualsInches() {
        var cm = new Quantity<>(2.54, LengthUnit.CENTIMETERS);
        var inch = new Quantity<>(1.0, LengthUnit.INCHES);
        assertTrue(cm.equals(inch));
    }

    @Test
    void testConvertFeetToInches() {
        var feet = new Quantity<>(1.0, LengthUnit.FEET);
        var inches = feet.convertTo(LengthUnit.INCHES);
        assertEquals(12.0, inches.getValue(), EPSILON);
    }

    @Test
    void testLengthAdditionDefaultUnit() {
        var a = new Quantity<>(1.0, LengthUnit.FEET);
        var b = new Quantity<>(12.0, LengthUnit.INCHES);
        var result = a.add(b);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testLengthAdditionExplicitUnit() {
        var a = new Quantity<>(1.0, LengthUnit.FEET);
        var b = new Quantity<>(12.0, LengthUnit.INCHES);
        var result = a.add(b, LengthUnit.INCHES);
        assertEquals(24.0, result.getValue(), EPSILON);
    }

    @Test
    void testLengthNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testLengthInvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    // ---------------- WEIGHT TESTS ----------------

    @Test
    void testWeightKgToGramEquality() {
        var kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var gram = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(kg.equals(gram));
    }

    @Test
    void testWeightKgToPoundEquality() {
        var kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var pound = new Quantity<>(2.20462, WeightUnit.POUND);
        assertTrue(kg.equals(pound));
    }

    @Test
    void testWeightConversion() {
        var kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var grams = kg.convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, grams.getValue(), EPSILON);
    }

    @Test
    void testWeightAdditionDefaultUnit() {
        var kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var gram = new Quantity<>(1000.0, WeightUnit.GRAM);
        var result = kg.add(gram);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(WeightUnit.KILOGRAM, result.getUnit());
    }

    @Test
    void testWeightAdditionExplicitUnit() {
        var kg = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var gram = new Quantity<>(1000.0, WeightUnit.GRAM);
        var result = kg.add(gram, WeightUnit.GRAM);
        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    // ---------------- CROSS CATEGORY ----------------

    @Test
    void testWeightLengthIncompatibility() {
        var weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var length = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(weight.equals(length));
    }
}