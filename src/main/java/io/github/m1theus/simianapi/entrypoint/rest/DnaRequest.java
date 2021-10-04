package io.github.m1theus.simianapi.entrypoint.rest;

import io.github.m1theus.simianapi.commons.validator.ValidDnaSequence;
import io.github.m1theus.simianapi.core.usecase.IDna;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;

public class DnaRequest implements IDna, Serializable {

    @NotNull
    @NotEmpty
    @ValidDnaSequence
    private String[] dna;

    public DnaRequest(String[] dna) {
        this.dna = dna;
    }

    public DnaRequest() {
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return this.dna;
    }

    @Override
    public String toString() {
        return "DnaRequest{" +
                "dna=" + Arrays.toString(dna) +
                '}';
    }
}
