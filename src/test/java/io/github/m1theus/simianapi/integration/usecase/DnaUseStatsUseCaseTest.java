package io.github.m1theus.simianapi.integration.usecase;

import io.github.m1theus.simianapi.core.domain.entity.DnaStats;
import io.github.m1theus.simianapi.core.domain.repository.DnaRepository;
import io.github.m1theus.simianapi.core.usecase.DnaStatsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class DnaUseStatsUseCaseTest {

    @Mock
    private DnaRepository dnaRepositoryMock;

    private DnaStatsUseCase dnaStatsUseCaseMock;

    @BeforeEach
    void setup() {
        dnaStatsUseCaseMock = new DnaStatsUseCase(dnaRepositoryMock);
    }

    @Test
    void findDnaStats() {
        final DnaStats savedDnaStatsMock = new DnaStats(39L, 52L);
        when(dnaRepositoryMock.stats()).thenReturn(savedDnaStatsMock);

        final DnaStats dnaStats = dnaStatsUseCaseMock.execute();

        assertThat(dnaStats.getHumanCount()).isEqualTo(savedDnaStatsMock.getHumanCount());
        assertThat(dnaStats.getSimianCount()).isEqualTo(savedDnaStatsMock.getSimianCount());
        assertThat(dnaStats.ratio()).isEqualTo(savedDnaStatsMock.ratio());
    }
}
