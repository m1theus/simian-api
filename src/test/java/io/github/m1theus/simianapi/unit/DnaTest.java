package io.github.m1theus.simianapi.unit;

import io.github.m1theus.simianapi.core.domain.entity.Dna;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DnaTest {
    @Test
    void dna_sequence_should_be_simian_and_return_true() {
        final Dna dna = new Dna(new String[]{"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"});

        assertTrue(dna.isSimian());
    }

    @Test
    void dna_sequence_should_be_human_and_return_false() {
        final Dna dna = new Dna(new String[]{"ATAAA", "AGGGG", "ATGGG", "AGGGG", "CGCTG"});

        assertTrue(dna.isSimian());
    }

    @Test
    void should_sanitize_dna_sequence() {
        final Dna dna = new Dna(new String[]{"ATAAA", "AGGGG", "ATGGG", "AGGGG", "CGCTG"});

        assertEquals("ATAAAAGGGGATGGGAGGGGCGCTG", dna.getDnaSequence());
    }
}
