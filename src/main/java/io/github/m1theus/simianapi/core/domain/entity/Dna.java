package io.github.m1theus.simianapi.core.domain.entity;

public class Dna {
    private final String dnaSequence;
    private final boolean simian;

    public Dna(String[] dna) {
        this.dnaSequence = sanitizeDna(dna);
        this.simian = this.isSimian(dna);
    }

    public String getDnaSequence() {
        return dnaSequence;
    }

    public boolean isSimian() {
        return simian;
    }

    private String sanitizeDna(String[] dna) {
        return String.join("", dna);
    }

    private boolean isSimian(String[] dna) {
        int n = dna.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (horizontalSearch(dna, n, i, j)) {
                    return true;
                }

                if (verticalSearch(dna, n, i, j)) {
                    return true;
                }

                if (diagonalSearch(dna, n, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean horizontalSearch(String[] dna, int n, int i, int j) {
        if (j < n - 3) {
            return ("" + dna[i].charAt(j) +
                    dna[i].charAt(j + 1) +
                    dna[i].charAt(j + 2) +
                    dna[i].charAt(j + 3))
                    .replace(String.valueOf(dna[i].charAt(j)), "").length() == 0;
        }
        return false;
    }

    private boolean verticalSearch(String[] dna, int n, int i, int j) {
        if ((i < n - 3) && (j < n - 3)) {
            return ("" + dna[i].charAt(j) +
                    dna[i + 1].charAt(j) +
                    dna[i + 2].charAt(j) +
                    dna[i + 3].charAt(j))
                    .replace(String.valueOf(dna[i].charAt(j)), "").length() == 0;
        }
        return false;
    }

    private boolean diagonalSearch(String[] dna, int n, int i, int j) {
        if ((i < n - 3) && (j < n - 3)) {
            return ("" + dna[i].charAt(j) +
                    dna[i + 1].charAt(j + 1) +
                    dna[i + 2].charAt(j + 2) +
                    dna[i + 3].charAt(j + 3))
                    .replace(String.valueOf(dna[i].charAt(j)), "").length() == 0;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Dna{" +
                ", sequence='" + dnaSequence + '\'' +
                ", isSimian=" + simian +
                '}';
    }
}
