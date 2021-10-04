package io.github.m1theus.simianapi.core.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DnaStats {
    private final Long humanCount;
    private final Long simianCount;

    public DnaStats(Long humanCount, Long simianCount) {
        this.humanCount = humanCount;
        this.simianCount = simianCount;
    }

    public Long getHumanCount() {
        return humanCount;
    }

    public Long getSimianCount() {
        return simianCount;
    }

    public BigDecimal ratio() {
        if (0 == humanCount) {
            return BigDecimal.ZERO;
        }

        return BigDecimal
                .valueOf(simianCount)
                .divide(BigDecimal.valueOf(humanCount), 2, RoundingMode.HALF_UP);
    }
}
