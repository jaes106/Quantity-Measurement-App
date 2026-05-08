package com.bridgelabz.controller;

import com.bridgelabz.dto.QuantityDTO;
import com.bridgelabz.dto.QuantityMeasurementDTO;
import com.bridgelabz.entity.OperationType;
import com.bridgelabz.service.IQuantityMeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuantityMeasurementController.class)
@Import(com.bridgelabz.config.SecurityConfig.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Test
    void givenValidAddRequest_whenPostAdd_thenReturnsResult() throws Exception {
        when(service.add(any(QuantityDTO.class), any(QuantityDTO.class), eq("FEET")))
                .thenReturn(new QuantityMeasurementDTO(
                        1L,
                        OperationType.ADD,
                        new QuantityDTO(2.0, "FEET", "length"),
                        null,
                        LocalDateTime.now()
                ));

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
                .andExpect(jsonPath("$.result.value").value(2.0))
                .andExpect(jsonPath("$.result.unit").value("FEET"));
    }

    @Test
    void givenInvalidMeasurementType_whenPostConvert_thenReturnsBadRequest() throws Exception {
        String request = """
                {
                  "quantity": {"value": 1, "unit": "FEET", "measurementType": "distance"},
                  "targetUnit": "INCHES"
                }
                """;

        mockMvc.perform(post("/api/quantity-measurements/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void givenHistoryExists_whenGetHistory_thenReturnsHistory() throws Exception {
        when(service.history()).thenReturn(List.of(new QuantityMeasurementDTO(
                1L,
                OperationType.COMPARE,
                true,
                null,
                LocalDateTime.now()
        )));

        mockMvc.perform(get("/api/quantity-measurements/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operation").value("COMPARE"))
                .andExpect(jsonPath("$[0].result").value(true));
    }
}
