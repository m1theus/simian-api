package io.github.m1theus.simianapi.core.usecase;

import io.github.m1theus.simianapi.core.domain.entity.DnaStats;
import io.github.m1theus.simianapi.core.domain.repository.DnaRepository;
import org.springframework.stereotype.Component;

@Component
public class DnaStatsUseCase {
    private final DnaRepository repository;

    public DnaStatsUseCase(DnaRepository repository) {
        this.repository = repository;
    }

    public DnaStats execute() {
        return repository.stats();
    }
}
