# Juniemvc Project Guidelines

## Project Overview
Juniemvc is a Spring Boot application that manages beer entities through a RESTful API. It provides CRUD operations for beer entities and follows a layered architecture pattern.

## Project Structure
The project follows a standard Spring Boot application structure:

```
src/
├── main/
│   ├── java/
│   │   └── guru/springframework/juniemvc/
│   │       ├── controllers/    # REST API endpoints
│   │       ├── entities/       # JPA entity classes
│   │       ├── repositories/   # Spring Data JPA repositories
│   │       ├── services/       # Business logic layer
│   │       └── JuniemvcApplication.java  # Main application class
│   └── resources/
│       └── application.properties  # Application configuration
└── test/
    ├── java/
    │   └── guru/springframework/juniemvc/
    │       ├── controllers/    # Controller tests
    │       ├── repositories/   # Repository tests
    │       ├── services/       # Service tests
    │       └── JuniemvcApplicationTests.java
    └── resources/
        └── application.properties  # Test configuration
```

## Tech Stack
- **Java 21**: Programming language
- **Spring Boot 3.4.5**: Application framework
- **Spring MVC**: Web layer
- **Spring Data JPA**: Data access layer
- **H2 Database**: In-memory database for development and testing
- **Lombok**: Reduces boilerplate code
- **Flyway**: Database migration tool
- **Maven**: Build and dependency management
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for testing

## Development Workflow

### Setting Up the Project
1. Clone the repository
2. Import as a Maven project in your IDE
3. Build the project: `mvn clean install`

### Making Changes
1. Follow the existing package structure
2. Create new entities in the `entities` package
3. Create repositories in the `repositories` package
4. Implement business logic in the `services` package
5. Expose APIs in the `controllers` package

### Running the Application
```
mvn spring-boot:run
```

## Running Tests
Run all tests:
```
mvn test
```

Run specific test class:
```
mvn test -Dtest=BeerControllerTest
```

Run specific test method:
```
mvn test -Dtest=BeerControllerTest#testGetBeerById
```

## Best Practices
1. **Layer Separation**: Controllers should only use services, and services should use repositories
2. **Testing**: Write tests for all layers (controllers, services, repositories)
3. **Documentation**: Add JavaDoc comments to service methods
4. **Validation**: Use Bean Validation for input validation
5. **Error Handling**: Implement proper exception handling
6. **Lombok**: Use Lombok annotations to reduce boilerplate code
7. **Immutability**: Use immutable objects where possible
8. **Naming Conventions**: Follow standard Java naming conventions