package com.bridgelabz.service;

import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO convert(QuantityDTO input, String targetUnit);

    QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit);

    QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2);

    QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2);

    List<QuantityMeasurementDTO> history();
}
