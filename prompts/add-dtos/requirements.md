# Requirements for Adding DTOs to Beer API

## Overview
This document outlines the requirements for refactoring the Beer API to use Data Transfer Objects (DTOs) instead of directly exposing JPA entities. This change will improve API design by separating the persistence layer from the presentation layer.

## Objectives
- Decouple the API contract from the database schema
- Improve API maintainability and flexibility
- Implement proper separation of concerns
- Enable independent evolution of the API and database schema

## Technical Requirements

### 1. Add Required Dependencies
- Add MapStruct dependencies to pom.xml:
  ```xml
  <!-- MapStruct -->
  <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId>
      <version>1.5.5.Final</version>
  </dependency>
  ```
- Update the maven-compiler-plugin configuration to include MapStruct annotation processor:
  ```xml
  <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <configuration>
          <annotationProcessorPaths>
              <path>
                  <groupId>org.projectlombok</groupId>
                  <artifactId>lombok</artifactId>
                  <version>${lombok.version}</version>
              </path>
              <path>
                  <groupId>org.mapstruct</groupId>
                  <artifactId>mapstruct-processor</artifactId>
                  <version>1.5.5.Final</version>
              </path>
              <path>
                  <groupId>org.projectlombok</groupId>
                  <artifactId>lombok-mapstruct-binding</artifactId>
                  <version>0.2.0</version>
              </path>
          </annotationProcessorPaths>
      </configuration>
  </plugin>
  ```

### 2. Create DTO Class
- Create a new package `guru.springframework.juniemvc.models`
- Create a new class `BeerDto` with the following properties:
  - Integer id
  - Integer version
  - String beerName
  - String beerStyle
  - String upc
  - Integer quantityOnHand
  - BigDecimal price
  - LocalDateTime createdDate
  - LocalDateTime updateDate
- Apply Lombok annotations:
  - @Data (or @Getter, @Setter)
  - @Builder
  - @NoArgsConstructor
  - @AllArgsConstructor
- Add validation annotations where appropriate:
  - @NotBlank for required String fields
  - @NotNull for required non-String fields
  - @Positive for numeric fields that should be positive

### 3. Create MapStruct Mapper
- Create a new package `guru.springframework.juniemvc.mappers`
- Create a new interface `BeerMapper` with the following methods:
  ```java
  @Mapper(componentModel = "spring")
  public interface BeerMapper {
      BeerDto beerToBeerDto(Beer beer);
      
      @Mapping(target = "id", ignore = true)
      @Mapping(target = "createdDate", ignore = true)
      @Mapping(target = "updateDate", ignore = true)
      Beer beerDtoToBeer(BeerDto beerDto);
  }
  ```

### 4. Update Service Layer
- Modify the `BeerService` interface to use DTOs:
  ```java
  public interface BeerService {
      List<BeerDto> getAllBeers();
      Optional<BeerDto> getBeerById(Integer id);
      BeerDto saveBeer(BeerDto beerDto);
      Optional<BeerDto> updateBeer(Integer id, BeerDto beerDto);
      boolean deleteBeer(Integer id);
  }
  ```
- Update the `BeerServiceImpl` to:
  - Inject the `BeerMapper`
  - Convert between DTOs and entities using the mapper
  - Maintain the same business logic

### 5. Update Controller Layer
- Modify the `BeerController` to use DTOs:
  - Update method signatures to accept and return DTOs
  - Update request/response handling to work with DTOs
  - Add validation for incoming DTOs using `@Valid` annotation

### 6. Update Tests
- Update controller tests to work with DTOs
- Update service tests to work with DTOs
- Ensure all tests pass with the new implementation

## Acceptance Criteria
1. All API endpoints accept and return DTOs instead of entities
2. MapStruct correctly maps between DTOs and entities
3. When converting from DTO to entity, id, createdDate, and updateDate are ignored
4. Service layer handles the conversion between DTOs and entities
5. All tests pass with the new implementation
6. API functionality remains the same from the client perspective

## Implementation Guidelines
- Follow the existing code style and patterns
- Use constructor injection for dependencies
- Add appropriate JavaDoc comments
- Ensure proper error handling
- Follow REST API best practices