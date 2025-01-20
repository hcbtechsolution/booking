package br.com.hcbtechsolution.booking.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should Create a User Instance when Name and Id are Provided and check Results")
    void shouldCreateAUserInstance_whenNameAndIdAreProvided() {
        User user = new User("1", "John Doe");

        assertEquals("1", user.getId());
        assertEquals("John Doe", user.getName());
    }

    @ParameterizedTest
    @DisplayName("Should ThrowException when Name is null or empty or blank")
    @MethodSource("provideInvalidNameScenarios")
    void shouldThrowException_whenNameIsNullOrEmptyOrBlank(String name) {
        User user = new User("1", name);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(
                "The field 'name' cannot be null or empty or blank (only whitespace is not allowed).")));
    }

    static Stream<Arguments> provideInvalidNameScenarios() {
        return Stream.of(
                null,
                Arguments.of(""),
                Arguments.of("    "));
    }
}
