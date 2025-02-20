# Hexagonal Pet Clinic

## Overview

Hexagonal Pet Clinic is a simple `Hello World`-style Pet Clinic system that demonstrates the **Hexagonal Architecture** (also known as the **Ports & Adapters Pattern**). This project is designed as a learning example to illustrate how business logic can be decoupled from external dependencies such as databases, frameworks, and external APIs.

### Goals:
- Demonstrate **Hexagonal Architecture** principles.
- Provide **JPA** and **MongoDB** as interchangeable persistence options.
- Ensure a clean separation between **domain**, **application**, and **infrastructure** layers.
- Facilitate easy testing and maintainability.

## Hexagonal Architecture

Hexagonal Architecture aims to separate business logic from external dependencies. It is based on **Ports** and **Adapters**, where:
- **Ports** are interfaces that define the operations available to the application.
- **Adapters** implement these ports, integrating with external dependencies (e.g., databases, APIs).

This ensures that the core business logic remains independent of storage, UI, or external APIs.

### Benefits:
- **Easier testing:** Business logic can be tested independently.
- **Replaceable dependencies:** Swap out infrastructure components without modifying the core logic.
- **Improved maintainability:** Code is modular and well-structured.

## Architecture Breakdown

The project follows the **Hexagonal Architecture** by organizing the code into distinct modules:

### 1. **Domain Module** (Core Business Logic)
- Contains **entities, value objects, domain services, and interfaces**.
- Defines the essential business rules and logic.
- Examples:
    - `RegistrationService.java` (use case for registering pets and visits)
    - `Owner.java`, `Pet.java`, `Visit.java` (domain models)
    - `PetDescriptionEnhancer.java` (port for description enhancement)

### 2. **Application Module** (Use Cases & Services)
- Implements **application services** that orchestrate business logic.
- Handles **DTO mapping** to keep domain entities separate from infrastructure concerns.
- Example classes:
    - `Mapper.java` (converts between DTOs and domain models)

### 3. **Infrastructure Module** (Adapters & Implementations)
- Implements **ports** and provides external integrations.
- Includes **JPA** and **MongoDB** adapters as separate persistence options.
- Contains configurations and external dependencies.
- Example classes:
    - `PetJpaRepository.java` (JPA implementation of pet repository)
    - `PetMongoRepository.java` (MongoDB implementation of pet repository)

### 4. **Utils Module** (Cross-Cutting Concerns)
- Contains reusable utilities such as logging, validation, and file handling.
- Examples:
    - `CollectionUtils.java` (helpers for handling collections)
    - `FileUtils.java` (helpers for file operations)

## Storage Implementations: JPA vs. MongoDB

This project includes two separate storage implementations:
- **JPA (Relational Database)**
- **MongoDB (NoSQL Database)**

Each implementation resides in the **infrastructure** layer and can be swapped without modifying the **domain** or **application** layers.

### Comparing Implementations:
| Feature         | JPA Implementation                    | MongoDB Implementation                 |
|---------------|---------------------------------|--------------------------------|
| **Repository** | `PetJpaRepository.java` | `PetMongoRepository.java` |
| **Configuration** | `H2TcpServerConfig.java` | `MongoConfig.java` |
| **Initializer** | `DatabaseInitializer.java` | `MongoDatabaseInitializer.java` |
| **Branch** | `main` branch (default) | `mongodb` branch |

### Demonstrating Hexagonal Strength:
- The **domain and application layers remain unchanged**, while only the **infrastructure layer** changes.
- Developers can **switch between JPA and MongoDB** by switching branches.
- **Encapsulation of storage logic** ensures minimal impact on core logic.

## Branching Strategy

- The **main** branch contains the JPA implementation.
- The **mongodb** branch contains the MongoDB implementation.
- Developers can switch between them by checking out the corresponding branch:
  ```sh
  git checkout main    # JPA (default)
  git checkout mongodb # MongoDB
  ```

## Value Object Framework

- Uses **immutable value objects** for domain concepts like `EmailAddress`, `PetIdentifier`, and `TelephoneNumber`.
- Ensures **data integrity** and encapsulation.
- Example:
  ```java
  public final class EmailAddress extends StringValueObject {
      public static EmailAddress of(String value) {
          return new EmailAddress(value);
      }
  }
  ```

## Database Converters for Value Objects

- Since JPA and MongoDB handle data differently, **custom converters** are provided.
- Converts **value objects** into persistable formats.
- Example (MongoDB Converter for EmailAddress):
  ```java
  public class EmailAddressReadConverter implements Converter<Document, EmailAddress> {
      public EmailAddress convert(Document source) {
          return EmailAddress.of(source.getString("email"));
      }
  }
  ```

## Technology Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA (H2 Database)**
- **Spring Data MongoDB**
- **Lombok**
- **Jackson (for DTO serialization/deserialization)**
- **JUnit (Testing)**

## Credits

Developed by [Thomas Muller](mailto:thomas.muller@accenture.com) for educational purposes.

