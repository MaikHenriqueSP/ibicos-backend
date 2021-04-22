package br.com.ibicos.ibicos.enums;

public enum AdSortByValues {
    EVALUATION("evaluation"),
    EVALUATIONS_COUNTER("evaluationsCounter");

    public final String label;

    AdSortByValues(String label) {
        this.label = label;
    }
}
