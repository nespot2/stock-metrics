## Project overview

- This project is a web service that allows users to search for companies and view key valuation metrics in a single, concise interface.
Users can search by company name or stock code (ticker) to retrieve essential financial indicators such as PER, PBR, and PSR.

## Project Features & Architecture

- This project is built using Spring Boot 4.0.2 and follows a modular, layered architecture designed to enforce clear separation of concerns, testability, and long-term maintainability.

### Technical Stack

- **Java**: 21
- **Spring Boot**: 4.0.2
- **Spring Dependency Management**: 1.1.7
- **Key Libraries**:
  - Spring Boot Starter
  - Spring Boot Actuator
  - Micrometer Tracing with OpenTelemetry
  - OpenTelemetry Exporter (Zipkin)
  - Lombok
  - JUnit 5 (Jupiter)

- The system is organized into four primary modules:
  - bootstrap
  - adapter
  - application
    - required
    - provided
  - domain
- Each module has a well-defined responsibility and a strict dependency direction.

## Module overview

### bootstrap

- The entry point module of the application.

#### Responsibilities

- Application startup and configuration 
- Spring Boot initialization 
- Wiring and assembling all modules 
- Environment-specific configuration (profiles, runtime settings)


### adapter

- The infrastructure and interface layer.

#### Responsibilities

- External interfaces and I/O handling
- REST APIs (controllers)
- Persistence implementations (JPA, repositories)
- Integration with external systems (APIs, data providers, messaging, etc)

### application

- The use-case orchestration layer.

#### Responsibilities
- Application services (use cases)
- Transaction boundaries
- Coordination of domain logic
- Definition of ports (interfaces) used by adapters

#### Provided & Required Interfaces
- The application module defines explicit boundaries for communication with other modules through two types of interfaces:
  - Provided interfaces
  - Required interfaces
- Provided Interfaces
  - Provided interfaces expose application services (use cases) to outer layers.
- Required Interfaces
  - Required interfaces define services that the application module needs but does not implement itself.

### domain

- The core business domain.

#### Responsibilities
- Domain models (entities, value objects)
- Business rules and invariants
- Domain-level interfaces

#### etc
- all entity should extend BaseEntity which has id, createdAt, updatedAt fields.

### Dependency Direction

```
bootstrap
↓
adapter
↓
application
↓
domain
```