package io.github.m1theus.simianapi.core.usecase;

import io.github.m1theus.simianapi.core.domain.entity.Dna;
import io.github.m1theus.simianapi.core.domain.repository.DnaRepository;
import org.springframework.stereotype.Component;

@Component
public class DnaSimianUseCase {
    private final DnaRepository dnaRepository;

    public DnaSimianUseCase(DnaRepository dnaRepository) {
        this.dnaRepository = dnaRepository;
    }

    public boolean execute(IDna input) {
        var dna = new Dna(input.getDna());
        dnaRepository.saveDnaAsync(dna);
        return dna.isSimian();
    }
}
