package br.com.hcbtechsolution.booking.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

public class PropertyTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Should Create a Property Instance when all attributes are provided and check results")
    void shouldCreateAPropertyInstance_whenAllAttributesAreProvided() {
        Property property = new Property("1", "Beach House", "A beautiful beach house", 4, 200);

        assertEquals("1", property.getId());
        assertEquals("Beach House", property.getName());
        assertEquals("A beautiful beach house", property.getDescription());
        assertEquals(4, property.getMaxGuests());
        assertEquals(200, property.getPricePerNight());
    }

    @ParameterizedTest
    @DisplayName("Should ThrowException when Name is null or empty or blank")
    @MethodSource("provideInvalidNameScenarios")
    void shouldThrowException_whenNameIsNullOrEmptyOrBlank(String name) {
        Property property = new Property("2", name, "A beautiful country house", 6, 250);

        Set<ConstraintViolation<Property>> violations = validator.validate(property);

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

    @ParameterizedTest
    @DisplayName("Should ThrowException when Max Guests is less than 1")
    @MethodSource("provideInvalidMaxGuestsScenarios")
    void shouldThrowException_whenMaxGuestsIsLessThan1(int maxGuests) {
        Property property = new Property("2", "Country house", "A beautiful country house", maxGuests, 250);

        Set<ConstraintViolation<Property>> violations = validator.validate(property);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(
                "The field 'maxGuests' should be greater than 0")));
    }

    static Stream<Arguments> provideInvalidMaxGuestsScenarios() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(-1),
                Arguments.of(-17),
                Arguments.of(-352));
    }

    @ParameterizedTest
    @DisplayName("Should ThrowException when Number of Guests is greater than max Guests  ")
    @MethodSource("provideInvalidNumberOfGuests")
    void shouldThrowException_whenNumberOfGuestsIsGreaterThanMaxGuests(int numberOfGuests) {
        Property property = new Property("2", "Country house", "A beautiful country house", 6, 250);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> property.validateGuestCount(numberOfGuests));

        String expectedMessage = "Maximum number of guests exceeded. Max Guests allowed: " + property.getMaxGuests();
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    static Stream<Arguments> provideInvalidNumberOfGuests() {
        return Stream.of(
                Arguments.of(7),
                Arguments.of(12),
                Arguments.of(15));
    }

    @ParameterizedTest
    @DisplayName("Should Not Apply Discount when Total Nights Is Less Than 7")
    @MethodSource("provideTotalPriceWithoutDiscountWhenTotalNightsIsLessThan7")
    void shouldNotApplyDiscount_whenTotalNightsIsLessThan7(int totalNights, double expectedTotalPrice) {
        Property property = new Property("3", "Apartment", "A beautiful apartment", 2, 100);

        double totalPrice = property.calculateTotalPrice(totalNights);

        assertEquals(expectedTotalPrice, totalPrice);
    }

    static Stream<Arguments> provideTotalPriceWithoutDiscountWhenTotalNightsIsLessThan7() {
        return Stream.of(
                Arguments.of(2, 200.00),
                Arguments.of(4, 400.00),
                Arguments.of(6, 600.00));
    }

    @ParameterizedTest
    @DisplayName("Should Apply Discount when Total Nights Is Greater Equal Than 7")
    @MethodSource("provideTotalPriceWithDiscountWhenTotalNightsIsGreaterEqualThan7")
    void shouldApplyDiscount_whenTotalNightsIsGreaterEqualThan7(int totalNights, double expectedTotalPrice) {
        Property property = new Property("3", "Apartment", "A beautiful apartment", 2, 100);

        double totalPrice = property.calculateTotalPrice(totalNights);

        assertEquals(expectedTotalPrice, totalPrice);
    }

    static Stream<Arguments> provideTotalPriceWithDiscountWhenTotalNightsIsGreaterEqualThan7() {
        return Stream.of(
                Arguments.of(7, 630.00),
                Arguments.of(10, 900.00),
                Arguments.of(15, 1350.00));
    }
}
