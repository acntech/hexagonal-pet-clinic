package no.acntech.hexapetclinic.test.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import no.acntech.hexapetclinic.domain.model.Gender;
import no.acntech.hexapetclinic.domain.model.PetIdentifier;
import no.acntech.hexapetclinic.utils.validation.ValidationException;
import org.junit.jupiter.api.Test;

@Slf4j
class PetIdentifierTest {

    @Test
    void shouldGenerateValidPetID() {
        PetIdentifier petIdentifier = PetIdentifier.generate(2024, 3, 12, Gender.MALE, 12345);
        assertNotNull(petIdentifier);
        assertEquals(16, petIdentifier.getPrimitive().length());
        log.debug("Generated Pet ID: {}", petIdentifier);
    }

    @Test
    void shouldGenerateValidRandomPetID() {
        PetIdentifier petIdentifier = PetIdentifier.generate(2024, 3, 12, Gender.FEMALE);
        assertNotNull(petIdentifier);
        assertEquals(16, petIdentifier.getPrimitive().length());
        log.debug("Generated Random Pet ID: {}", petIdentifier);
    }

    @Test
    void shouldThrowExceptionForInvalidDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PetIdentifier.generate(2024, 2, 30, Gender.MALE, 54321));
        assertTrue(exception.getMessage().contains("Invalid date"));
    }

    @Test
    void shouldThrowExceptionForInvalidUniqueNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PetIdentifier.generate(2024, 3, 12, Gender.FEMALE, 100001));
        assertTrue(exception.getMessage().contains("Unique number must be between 1 and 100000"));
    }

    @Test
    void shouldThrowExceptionForInvalidGender() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Gender.fromCode(3));
        assertTrue(exception.getMessage().contains("Invalid gender code"));
    }

    @Test
    void shouldValidateCorrectFormat() {
        PetIdentifier petIdentifier = PetIdentifier.generate(2024, 5, 20, Gender.MALE, 98765);
        assertNotNull(petIdentifier);
        assertTrue(petIdentifier.getPrimitive().matches("^(\\d{4})(\\d{2})(\\d{2})([12])(\\d{6})(\\d)$"));
    }

    @Test
    void shouldThrowExceptionForInvalidFormat() {
        Exception exception = assertThrows(ValidationException.class, () ->
                PetIdentifier.of("202405201X987652"));
        assertTrue(exception.getMessage().contains("PetIdentifier does not match the required format"));
    }

    @Test
    void shouldCreatePetIDFromExistingValidString() {
        String validPetIDString = PetIdentifier.generate(2023, 10, 5, Gender.FEMALE, 54321).getPrimitive();
        PetIdentifier petIdentifier = PetIdentifier.of(validPetIDString);
        assertNotNull(petIdentifier);
        assertEquals(validPetIDString, petIdentifier.getPrimitive());
    }

    @Test
    void shouldThrowExceptionForInvalidExistingPetID() {
        Exception exception = assertThrows(ValidationException.class, () ->
                PetIdentifier.of("2024130112345678")); // Invalid month 13
        assertTrue(exception.getMessage().contains("Invalid date or gender in PetIdentifier"));
    }

    @Test
    void shouldCorrectlyValidateLeapYear() {
        PetIdentifier petIdentifier = PetIdentifier.generate(2024, 2, 29, Gender.MALE, 12345);
        assertNotNull(petIdentifier);
        assertEquals(16, petIdentifier.getPrimitive().length());
    }

    @Test
    void shouldRejectNonLeapYearFeb29() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                PetIdentifier.generate(2023, 2, 29, Gender.FEMALE, 54321));
        assertTrue(exception.getMessage().contains("Invalid date"));
    }
}
