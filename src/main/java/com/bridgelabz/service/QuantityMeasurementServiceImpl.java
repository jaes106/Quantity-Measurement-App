package com.bridgelabz.service;

import com.bridgelabz.IMeasurable;
import com.bridgelabz.LengthUnit;
import com.bridgelabz.Quantity;
import com.bridgelabz.TemperatureUnit;
import com.bridgelabz.VolumeUnit;
import com.bridgelabz.WeightUnit;
import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.dto.QuantityMeasurementDTO;
import com.bridgelabz.entity.OperationType;
import com.bridgelabz.entity.QuantityMeasurementEntity;
import com.bridgelabz.exception.QuantityMeasurementException;
import com.bridgelabz.repository.QuantityMeasurementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(String type, String unit) {
        try {
            return switch (type.toLowerCase()) {
                case "length" -> LengthUnit.valueOf(unit);
                case "weight" -> WeightUnit.valueOf(unit);
                case "volume" -> VolumeUnit.valueOf(unit);
                case "temperature" -> TemperatureUnit.valueOf(unit);
                default -> throw new QuantityMeasurementException("Invalid measurement type");
            };
        } catch (IllegalArgumentException e) {
            throw new QuantityMeasurementException("Invalid unit");
        }
    }

    private Quantity<IMeasurable> toQuantity(QuantityDTO dto) {
        if (dto == null) {
            throw new QuantityMeasurementException("Quantity is required");
        }
        IMeasurable unit = resolveUnit(dto.getMeasurementType(), dto.getUnit());
        return new Quantity<>(dto.getValue(), unit);
    }

    @Override
    public QuantityMeasurementDTO convert(QuantityDTO input, String targetUnit) {
        try {
            Quantity<IMeasurable> quantity = toQuantity(input);
            IMeasurable target = resolveUnit(input.getMeasurementType(), targetUnit);
            Quantity<IMeasurable> result = quantity.convertTo(target);
            QuantityDTO resultDto = new QuantityDTO(result.getValue(), target.getUnitName(), input.getMeasurementType());
            return saveSuccess(OperationType.CONVERT, resultDto);
        } catch (Exception e) {
            saveFailure(OperationType.CONVERT, e);
            throw asQuantityException(e);
        }
    }

    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            Quantity<IMeasurable> first = toQuantity(q1);
            Quantity<IMeasurable> second = toQuantity(q2);
            IMeasurable target = resolveUnit(q1.getMeasurementType(), targetUnit);
            Quantity<IMeasurable> result = first.add(second, target);
            QuantityDTO resultDto = new QuantityDTO(result.getValue(), target.getUnitName(), q1.getMeasurementType());
            return saveSuccess(OperationType.ADD, resultDto);
        } catch (Exception e) {
            saveFailure(OperationType.ADD, e);
            throw asQuantityException(e);
        }
    }

    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {
        try {
            Quantity<IMeasurable> first = toQuantity(q1);
            Quantity<IMeasurable> second = toQuantity(q2);
            IMeasurable target = resolveUnit(q1.getMeasurementType(), targetUnit);
            Quantity<IMeasurable> result = first.subtract(second, target);
            QuantityDTO resultDto = new QuantityDTO(result.getValue(), target.getUnitName(), q1.getMeasurementType());
            return saveSuccess(OperationType.SUBTRACT, resultDto);
        } catch (Exception e) {
            saveFailure(OperationType.SUBTRACT, e);
            throw asQuantityException(e);
        }
    }

    @Override
    public QuantityMeasurementDTO divide(QuantityDTO q1, QuantityDTO q2) {
        try {
            Quantity<IMeasurable> first = toQuantity(q1);
            Quantity<IMeasurable> second = toQuantity(q2);
            return saveSuccess(OperationType.DIVIDE, first.divide(second));
        } catch (Exception e) {
            saveFailure(OperationType.DIVIDE, e);
            throw asQuantityException(e);
        }
    }

    @Override
    public QuantityMeasurementDTO compare(QuantityDTO q1, QuantityDTO q2) {
        try {
            Quantity<IMeasurable> first = toQuantity(q1);
            Quantity<IMeasurable> second = toQuantity(q2);
            return saveSuccess(OperationType.COMPARE, first.equals(second));
        } catch (Exception e) {
            saveFailure(OperationType.COMPARE, e);
            throw asQuantityException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuantityMeasurementDTO> history() {
        return repository.findAll().stream()
                .map(this::toMeasurementDTO)
                .toList();
    }

    private QuantityMeasurementDTO saveSuccess(OperationType operation, Object result) {
        QuantityMeasurementEntity entity = repository.save(
                new QuantityMeasurementEntity(operation, String.valueOf(result)));
        return new QuantityMeasurementDTO(entity.getId(), operation, result, null, entity.getCreatedAt());
    }

    private void saveFailure(OperationType operation, Exception exception) {
        repository.save(new QuantityMeasurementEntity(operation, null, exception.getMessage()));
    }

    private QuantityMeasurementDTO toMeasurementDTO(QuantityMeasurementEntity entity) {
        return new QuantityMeasurementDTO(
                entity.getId(),
                entity.getOperation(),
                entity.getResult(),
                entity.getError(),
                entity.getCreatedAt()
        );
    }

    private QuantityMeasurementException asQuantityException(Exception exception) {
        if (exception instanceof QuantityMeasurementException quantityMeasurementException) {
            return quantityMeasurementException;
        }
        return new QuantityMeasurementException(exception.getMessage());
    }
}
