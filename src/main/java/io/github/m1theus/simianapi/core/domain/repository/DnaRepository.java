package io.github.m1theus.simianapi.core.domain.repository;

import io.github.m1theus.simianapi.core.domain.entity.Dna;
import io.github.m1theus.simianapi.core.domain.entity.DnaStats;

public interface DnaRepository {
    void saveDnaAsync(Dna dna);
    DnaStats stats();
}
