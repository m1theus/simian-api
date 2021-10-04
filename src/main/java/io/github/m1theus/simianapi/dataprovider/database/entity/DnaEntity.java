package io.github.m1theus.simianapi.dataprovider.database.entity;

import io.github.m1theus.simianapi.core.domain.entity.Dna;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "dna")
public class DnaEntity {
    @Id
    private String dnaSequence;
    private boolean simian;
    @Field("created_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @PersistenceConstructor
    public DnaEntity(String dnaSequence, boolean simian) {
        this.dnaSequence = dnaSequence;
        this.simian = simian;
    }

    public static DnaEntity of(final Dna dna) {
        return new DnaEntity(dna.getDnaSequence(), dna.isSimian());
    }

    public boolean isSimian() {
        return this.simian;
    }

    public boolean isHuman() {
        return !this.simian;
    }

}
