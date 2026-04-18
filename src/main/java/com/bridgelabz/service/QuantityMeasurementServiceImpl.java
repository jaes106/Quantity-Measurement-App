package com.bridgelabz.service;

import com.bridgelabz.*;
import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.entity.QuantityMeasurementEntity;
import com.bridgelabz.exception.QuantityMeasurementException;
import com.bridgelabz.repository.IQuantityMeasurementRepository;

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(String type, String unit) {

        return switch (type.toLowerCase()) {
            case "length" -> LengthUnit.valueOf(unit);
            case "weight" -> WeightUnit.valueOf(unit);
            case "volume" -> VolumeUnit.valueOf(unit);
            case "temperature" -> TemperatureUnit.valueOf(unit);
            default -> throw new QuantityMeasurementException("Invalid type");
        };
    }

    private Quantity<IMeasurable> toQuantity(QuantityDTO dto) {
        IMeasurable unit = resolveUnit(dto.getMeasurementType(), dto.getUnit());
        return new Quantity<>(dto.getValue(), unit);
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, String targetUnit) {

        try {
            Quantity<IMeasurable> q = toQuantity(input);
            IMeasurable target = resolveUnit(input.getMeasurementType(), targetUnit);

            Quantity<IMeasurable> result = q.convertTo(target);

            repository.save(new QuantityMeasurementEntity("convert", result.toString()));

            return new QuantityDTO(result.getValue(), target.getUnitName(), input.getMeasurementType());

        } catch (Exception e) {
            repository.save(new QuantityMeasurementEntity("convert", null, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        try {
            Quantity<IMeasurable> a = toQuantity(q1);
            Quantity<IMeasurable> b = toQuantity(q2);
            IMeasurable target = resolveUnit(q1.getMeasurementType(), targetUnit);

            Quantity<IMeasurable> result = a.add(b, target);

            repository.save(new QuantityMeasurementEntity("add", result.toString()));

            return new QuantityDTO(result.getValue(), target.getUnitName(), q1.getMeasurementType());

        } catch (Exception e) {
            repository.save(new QuantityMeasurementEntity("add", null, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        try {
            Quantity<IMeasurable> a = toQuantity(q1);
            Quantity<IMeasurable> b = toQuantity(q2);
            IMeasurable target = resolveUnit(q1.getMeasurementType(), targetUnit);

            Quantity<IMeasurable> result = a.subtract(b, target);

            repository.save(new QuantityMeasurementEntity("subtract", result.toString()));

            return new QuantityDTO(result.getValue(), target.getUnitName(), q1.getMeasurementType());

        } catch (Exception e) {
            repository.save(new QuantityMeasurementEntity("subtract", null, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    @Override
    public double divide(QuantityDTO q1, QuantityDTO q2) {

        try {
            Quantity<IMeasurable> a = toQuantity(q1);
            Quantity<IMeasurable> b = toQuantity(q2);

            double result = a.divide(b);

            repository.save(new QuantityMeasurementEntity("divide", String.valueOf(result)));

            return result;

        } catch (Exception e) {
            repository.save(new QuantityMeasurementEntity("divide", null, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    @Override
    public boolean compare(QuantityDTO q1, QuantityDTO q2) {

        try {
            Quantity<IMeasurable> a = toQuantity(q1);
            Quantity<IMeasurable> b = toQuantity(q2);

            boolean result = a.equals(b);

            repository.save(new QuantityMeasurementEntity("compare", String.valueOf(result)));

            return result;

        } catch (Exception e) {
            repository.save(new QuantityMeasurementEntity("compare", null, e.getMessage()));
            throw new QuantityMeasurementException(e.getMessage());
        }
    }
}