package com.bridgelabz.integrationtests;

import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.repository.QuantityMeasurementDatabaseRepository;
import com.bridgelabz.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class QuantityMeasurementJdbcPersistenceTest {

    private QuantityMeasurementDatabaseRepository repository;
    private QuantityMeasurementServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementDatabaseRepository.getInstance();
        repository.deleteAll();
        service = new QuantityMeasurementServiceImpl(repository);
    }

    @Test
    void givenAdditionOperation_whenServiceAdds_thenResultIsPersistedToDatabase() {
        QuantityDTO first = new QuantityDTO(1, "FEET", "length");
        QuantityDTO second = new QuantityDTO(12, "INCHES", "length");

        QuantityDTO result = service.add(first, second, "FEET");

        var measurements = repository.findAll();

        assertEquals(2.0, result.getValue());
        assertEquals(1, measurements.size());
        assertEquals("add", measurements.get(0).getOperation());
        assertEquals("Quantity(2.0, FEET)", measurements.get(0).getResult());
        assertNull(measurements.get(0).getError());
        assertNotNull(measurements.get(0).getCreatedAt());
    }

    @Test
    void givenInvalidOperation_whenServiceFails_thenErrorIsPersistedToDatabase() {
        QuantityDTO temperatureOne = new QuantityDTO(10, "CELSIUS", "temperature");
        QuantityDTO temperatureTwo = new QuantityDTO(20, "CELSIUS", "temperature");

        try {
            service.add(temperatureOne, temperatureTwo, "CELSIUS");
        } catch (RuntimeException ignored) {
            // The persistence assertion below is the behavior under test.
        }

        var measurements = repository.findAll();

        assertEquals(1, measurements.size());
        assertEquals("add", measurements.get(0).getOperation());
        assertNull(measurements.get(0).getResult());
        assertEquals("Temperature does not support addition operation", measurements.get(0).getError());
    }
}
