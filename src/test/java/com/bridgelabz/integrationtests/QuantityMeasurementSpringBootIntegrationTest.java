package com.bridgelabz.integrationtests;

import com.bridgelabz.repository.QuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QuantityMeasurementSpringBootIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuantityMeasurementRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void givenAddRequest_whenApiCalled_thenOperationPersistsThroughJpa() throws Exception {
        String request = """
                {
                  "firstQuantity": {"value": 1, "unit": "FEET", "measurementType": "length"},
                  "secondQuantity": {"value": 12, "unit": "INCHES", "measurementType": "length"},
                  "targetUnit": "FEET"
                }
                """;

        mockMvc.perform(post("/api/quantity-measurements/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operation").value("ADD"))
                .andExpect(jsonPath("$.result.value").value(2.0));

        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void givenSavedOperation_whenHistoryCalled_thenHistoryIsReturned() throws Exception {
        String request = """
                {
                  "firstQuantity": {"value": 1, "unit": "FEET", "measurementType": "length"},
                  "secondQuantity": {"value": 12, "unit": "INCHES", "measurementType": "length"}
                }
                """;

        mockMvc.perform(post("/api/quantity-measurements/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/quantity-measurements/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("COMPARE"));
    }
}
