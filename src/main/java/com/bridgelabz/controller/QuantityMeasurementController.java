package com.bridgelabz.controller;

import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public void performConversion(QuantityDTO dto, String targetUnit) {
        System.out.println(service.convert(dto, targetUnit));
    }

    public void performAddition(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        System.out.println(service.add(q1, q2, targetUnit));
    }

    public void performSubtraction(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        System.out.println(service.subtract(q1, q2, targetUnit));
    }

    public void performDivision(QuantityDTO q1, QuantityDTO q2) {
        System.out.println(service.divide(q1, q2));
    }

    public void performComparison(QuantityDTO q1, QuantityDTO q2) {
        System.out.println(service.compare(q1, q2));
    }
}