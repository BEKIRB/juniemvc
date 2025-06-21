# Implementation Plan for Adding DTOs to Beer API

## Overview
This document outlines the detailed implementation plan for refactoring the Beer API to use Data Transfer Objects (DTOs) instead of directly exposing JPA entities. The plan includes a timeline, specific tasks, dependencies, testing strategy, and risk mitigation.

## Timeline
The implementation is divided into the following phases:

### Phase 1: Setup and Preparation (Day 1)
- Add required dependencies to pom.xml
- Create the BeerDto class
- Create the MapStruct mapper interface

### Phase 2: Service Layer Refactoring (Day 2)
- Update the BeerService interface to use DTOs
- Update the BeerServiceImpl to use the mapper and DTOs

### Phase 3: Controller Layer Refactoring (Day 3)
- Update the BeerController to use DTOs
- Add validation for incoming DTOs

### Phase 4: Testing and Validation (Day 4)
- Update controller tests to work with DTOs
- Update service tests to work with DTOs
- Run all tests to ensure functionality is maintained

## Detailed Tasks

### Phase 1: Setup and Preparation

#### Task 1.1: Add MapStruct Dependencies
- Add MapStruct dependency to pom.xml
- Update maven-compiler-plugin configuration to include MapStruct annotation processor
- Ensure Lombok and MapStruct integration is properly configured

#### Task 1.2: Create BeerDto Class
- Create a new package `guru.springframework.juniemvc.models`
- Create BeerDto class with the same properties as the Beer entity
- Add Lombok annotations (@Data, @Builder, @NoArgsConstructor, @AllArgsConstructor)
- Add validation annotations (@NotBlank, @NotNull, @Positive) where appropriate

#### Task 1.3: Create MapStruct Mapper
- Create a new package `guru.springframework.juniemvc.mappers`
- Create BeerMapper interface with methods to convert between Beer and BeerDto
- Configure mapping to ignore id, createdDate, and updateDate when converting from DTO to entity

### Phase 2: Service Layer Refactoring

#### Task 2.1: Update BeerService Interface
- Modify method signatures to use BeerDto instead of Beer
- Update JavaDoc comments to reflect the changes

#### Task 2.2: Update BeerServiceImpl
- Inject the BeerMapper
- Update method implementations to use the mapper for conversions
- Ensure business logic remains the same
- Update JavaDoc comments to reflect the changes

### Phase 3: Controller Layer Refactoring

#### Task 3.1: Update BeerController
- Update method signatures to accept and return DTOs
- Add @Valid annotation to validate incoming DTOs
- Update request/response handling to work with DTOs
- Ensure proper error handling for validation failures

### Phase 4: Testing and Validation

#### Task 4.1: Update Controller Tests
- Update test setup to use DTOs
- Update test assertions to verify DTO properties
- Add tests for validation failures

#### Task 4.2: Update Service Tests
- Update test setup to use DTOs
- Update test assertions to verify DTO properties
- Add tests for mapper functionality

#### Task 4.3: Run All Tests
- Run all tests to ensure functionality is maintained
- Fix any issues that arise

## Dependencies Between Tasks
- Task 1.1 must be completed before Tasks 1.2 and 1.3
- Tasks 1.2 and 1.3 must be completed before Phase 2
- Phase 2 must be completed before Phase 3
- Phases 1, 2, and 3 must be completed before Phase 4

## Testing Strategy

### Unit Testing
- Update existing unit tests to work with DTOs
- Add new tests for mapper functionality
- Add tests for validation failures

### Integration Testing
- Ensure controller endpoints work correctly with DTOs
- Verify that validation is working as expected
- Test error handling for validation failures

### Manual Testing
- Test API endpoints using Postman or curl
- Verify that responses contain the expected data
- Test validation by sending invalid data

## Potential Risks and Mitigation Strategies

### Risk 1: Mapper Configuration Issues
- **Risk**: MapStruct might not generate the mapper implementation correctly.
- **Mitigation**: Verify the maven-compiler-plugin configuration, check the target/generated-sources directory for generated code, and add explicit mapping configurations if needed.

### Risk 2: Validation Failures
- **Risk**: Adding validation might break existing functionality.
- **Mitigation**: Start with minimal validation and gradually add more, test thoroughly after each addition.

### Risk 3: Test Failures
- **Risk**: Refactoring might break existing tests.
- **Mitigation**: Update tests incrementally, run tests frequently, and fix issues as they arise.

### Risk 4: Performance Impact
- **Risk**: Adding DTO conversion might impact performance.
- **Mitigation**: Use MapStruct which generates efficient mapping code at compile time, and consider performance testing for critical endpoints.

## Acceptance Criteria
The implementation will be considered complete when:
1. All API endpoints accept and return DTOs instead of entities
2. MapStruct correctly maps between DTOs and entities
3. When converting from DTO to entity, id, createdDate, and updateDate are ignored
4. Service layer handles the conversion between DTOs and entities
5. All tests pass with the new implementation
6. API functionality remains the same from the client perspective

## Conclusion
This implementation plan provides a structured approach to refactoring the Beer API to use DTOs. By following this plan, we can ensure that the refactoring is done systematically and that the API functionality is maintained throughout the process.