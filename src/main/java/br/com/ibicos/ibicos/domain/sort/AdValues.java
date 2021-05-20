package br.com.ibicos.ibicos.domain.sort;

public enum AdValues {
    EVALUATION("evaluation"),
    EVALUATIONS_COUNTER("evaluationsCounter");

    public final String label;

    AdValues(String label) {
        this.label = label;
    }
}
