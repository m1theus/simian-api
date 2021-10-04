package io.github.m1theus.simianapi.unit;

import io.github.m1theus.simianapi.commons.validator.DnaSequenceValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DnaSequenceValidatorTest {
    @Test
    void should_return_false_when_dna_value_is_null() {
        final DnaSequenceValidator dnaSequenceValidator = new DnaSequenceValidator();

        final boolean isValid = dnaSequenceValidator.isValid(null, null);

        assertFalse(isValid);
    }

    @Test
    void should_return_false_when_dna_value_is_invalid() {
        final DnaSequenceValidator dnaSequenceValidator = new DnaSequenceValidator();

        final String[] dna = {"ATAAX", "XGGGG", "ATXGG", "AGGGG", "CGXGG"};
        final boolean isValid = dnaSequenceValidator.isValid(dna, null);

        assertFalse(isValid);
    }

    @Test
    void should_return_true_when_dna_value_is_invalid() {
        final DnaSequenceValidator dnaSequenceValidator = new DnaSequenceValidator();

        final String[] dna = new String[]{"ATAAA", "AGGGG", "ATGGG", "AGGGG", "CGCTG"};
        final boolean isValid = dnaSequenceValidator.isValid(dna, null);

        assertTrue(isValid);
    }
}
