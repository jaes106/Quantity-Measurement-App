# Quantity Measurement Application

## Overview

The Quantity Measurement Application is a Java-based system designed to perform operations on different measurable quantities such as length, weight, volume, and temperature. The application supports comparison, unit conversion, and arithmetic operations while ensuring type safety and correctness across measurement categories.

This project evolves incrementally through multiple use cases and follows clean architecture principles. As of UC15, the application is refactored into an N-Tier architecture to improve maintainability, scalability, and testability.

---

## Architecture (UC15)

The application follows a layered (N-Tier) architecture:

### 1. Application Layer
- Entry point of the system
- Initializes dependencies (repository, service, controller)
- Delegates execution to the controller

### 2. Controller Layer
- Handles input and output interactions
- Delegates all business logic to the service layer
- Acts as a facade for the system

### 3. Service Layer
- Contains all business logic
- Performs:
  - Unit conversion
  - Equality checks
  - Arithmetic operations (add, subtract, divide)
- Validates operations and handles exceptions
- Interacts with the repository layer

### 4. Repository Layer
- Stores operation results
- Current implementation uses in-memory storage (cache)
- Designed to support future database integration

### 5. Entity / Model Layer
- **DTO (QuantityDTO)** → Used for input/output between layers
- **Model (QuantityModel)** → Internal representation for processing
- **Entity (QuantityMeasurementEntity)** → Stores operation history and results

### 6. Domain Layer (Core Logic)
- Contains core measurement logic:
  - `Quantity` (generic operations)
  - `IMeasurable` interface
  - Unit enums (Length, Weight, Volume, Temperature)
- Handles conversions and arithmetic rules

---

## Features

- Type-safe quantity comparisons
- Unit conversions across supported measurement types
- Arithmetic operations:
  - Addition
  - Subtraction
  - Division
- Prevention of invalid operations (e.g., cross-category operations)
- Temperature-specific constraints (no arithmetic allowed)
- Centralized exception handling
- Operation history tracking via repository

---

## Supported Measurement Types

- Length (Feet, Inches, Yards, Centimeters)
- Weight (Kilogram, Gram, Pound)
- Volume (Litre, Millilitre, Gallon)
- Temperature (Celsius, Fahrenheit, Kelvin)

---

## Design Principles

- Separation of Concerns (N-Tier Architecture)
- Single Responsibility Principle (SRP)
- Open/Closed Principle (OCP)
- Interface Segregation Principle (ISP)
- Dependency Injection (manual, constructor-based)

---

## Data Flow
Controller → Service → Domain (Quantity) → Repository → Response

---

## Future Scope

- Spring Boot REST API integration
- Persistent database storage
- Input validation using annotations
- Global exception handling with standardized responses

---

## Conclusion

This application demonstrates a structured approach to building scalable Java systems by progressively evolving from a monolithic design to a layered architecture. The UC15 refactoring ensures that the system is modular, testable, and ready for real-world extensions such as REST APIs and database integration.
