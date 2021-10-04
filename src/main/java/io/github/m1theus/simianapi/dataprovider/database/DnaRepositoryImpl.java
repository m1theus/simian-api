package io.github.m1theus.simianapi.dataprovider.database;

import io.github.m1theus.simianapi.core.domain.entity.Dna;
import io.github.m1theus.simianapi.core.domain.entity.DnaStats;
import io.github.m1theus.simianapi.core.domain.repository.DnaRepository;
import io.github.m1theus.simianapi.dataprovider.database.dao.DnaDao;
import io.github.m1theus.simianapi.dataprovider.database.entity.DnaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Repository
public class DnaRepositoryImpl implements DnaRepository {
    private static final Logger logger = LoggerFactory.getLogger(DnaRepositoryImpl.class);

    private final DnaDao dao;

    public DnaRepositoryImpl(DnaDao dao) {
        this.dao = dao;
    }

    @Override
    public void saveDnaAsync(Dna dna) {
        supplyAsync(() -> dao.findByDnaSequence(dna.getDnaSequence()).orElseGet(() -> {
            logger.info("m=checkDna stage=saving_new_dna entity={}", dna);
            var entity = DnaEntity.of(dna);
            return dao.save(entity);
        }));
    }

    @Override
    public DnaStats stats() {
        Long humanCount = dao.countHuman();
        Long simianCount = dao.countSimian();

        return new DnaStats(humanCount, simianCount);
    }

}
