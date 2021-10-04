package io.github.m1theus.simianapi.unit;

import io.github.m1theus.simianapi.core.domain.entity.DnaStats;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DnaStatsTest {
    @Test
    void test_dna_valid_ratio() {
        final DnaStats dnaStats = new DnaStats(5L, 3L);
        final var expected = new BigDecimal("0.60").setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected, dnaStats.ratio());
    }

    @Test
    void test_dna_invalid_ratio() {
        final DnaStats dnaStats = new DnaStats(5L, 0L);
        final var expected = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected, dnaStats.ratio());
    }
}
