package io.github.m1theus.simianapi.entrypoint.rest;

public class DnaResponse {
    private boolean simian;

    public DnaResponse(boolean simian) {
        this.simian = simian;
    }

    public boolean isSimian() {
        return simian;
    }
}
