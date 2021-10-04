package io.github.m1theus.simianapi.entrypoint.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.m1theus.simianapi.core.domain.entity.DnaStats;

import java.math.BigDecimal;

public class DnaStatsResponse {
    @JsonProperty("count_mutant_dna")
    private Long simianCount;
    @JsonProperty("count_human_dna")
    private Long humanCount;
    @JsonProperty("ratio")
    private BigDecimal ratio;

    public DnaStatsResponse(DnaStats dnaStats) {
        this.simianCount = dnaStats.getSimianCount();
        this.humanCount = dnaStats.getHumanCount();
        this.ratio = dnaStats.ratio();
    }

    @Override
    public String toString() {
        return "DnaStatsResponse{" +
                "simianCount=" + simianCount +
                ", humanCount=" + humanCount +
                ", ratio=" + ratio +
                '}';
    }

}
