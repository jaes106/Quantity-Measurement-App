package com.bridgelabz;

import com.bridgelabz.controller.QuantityMeasurementController;
import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        var repo = QuantityMeasurementCacheRepository.getInstance();
        var service = new QuantityMeasurementServiceImpl(repo);
        var controller = new QuantityMeasurementController(service);

        QuantityDTO q1 = new QuantityDTO(10, "FEET", "length");
        QuantityDTO q2 = new QuantityDTO(12, "INCHES", "length");

        controller.performAddition(q1, q2, "FEET");
        controller.performComparison(q1, q2);
    }
}