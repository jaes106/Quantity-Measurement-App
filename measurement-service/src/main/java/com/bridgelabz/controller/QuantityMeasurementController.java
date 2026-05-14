package com.bridgelabz.controller;

import com.bridgelabz.dto.QuantityInputDTO;
import com.bridgelabz.dto.QuantityMeasurementDTO;
import com.bridgelabz.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quantity-measurements")
@Tag(name = "Quantity Measurements", description = "Compare, convert, calculate, and view quantity operation history")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> compare(@Valid @RequestBody QuantityInputDTO request) {
        return ResponseEntity.ok(service.compare(request.getFirstQuantity(), request.getSecondQuantity()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<QuantityMeasurementDTO> convert(@Valid @RequestBody QuantityInputDTO request) {
        return ResponseEntity.ok(service.convert(request.getQuantity(), request.getTargetUnit()));
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> add(@Valid @RequestBody QuantityInputDTO request) {
        return ResponseEntity.ok(service.add(
                request.getFirstQuantity(),
                request.getSecondQuantity(),
                request.getTargetUnit()
        ));
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> subtract(@Valid @RequestBody QuantityInputDTO request) {
        return ResponseEntity.ok(service.subtract(
                request.getFirstQuantity(),
                request.getSecondQuantity(),
                request.getTargetUnit()
        ));
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> divide(@Valid @RequestBody QuantityInputDTO request) {
        return ResponseEntity.ok(service.divide(request.getFirstQuantity(), request.getSecondQuantity()));
    }

    @GetMapping("/history")
    @Operation(summary = "Fetch operation history")
    public ResponseEntity<List<QuantityMeasurementDTO>> history() {
        return ResponseEntity.ok(service.history());
    }
}
