package com.bridgelabz.service;

import com.bridgelabz.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO convert(QuantityDTO input, String targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    double divide(QuantityDTO q1, QuantityDTO q2);

    boolean compare(QuantityDTO q1, QuantityDTO q2);
}