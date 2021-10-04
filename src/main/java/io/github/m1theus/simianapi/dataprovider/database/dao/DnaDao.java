package io.github.m1theus.simianapi.dataprovider.database.dao;

import io.github.m1theus.simianapi.dataprovider.database.entity.DnaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DnaDao extends MongoRepository<DnaEntity, String> {
    Optional<DnaEntity> findByDnaSequence(String dna);

    @Query(value = "{'simian': false}", count = true)
    Long countHuman();

    @Query(value = "{'simian': true}", count = true)
    Long countSimian();
}
