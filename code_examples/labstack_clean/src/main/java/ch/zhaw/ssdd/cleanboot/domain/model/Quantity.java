package ch.zhaw.ssdd.cleanboot.domain.model;

public record Quantity(
        int value) {
    public Quantity {
        if (value < 0) {
            throw new IllegalArgumentException("A quantity must be positive");
        }
    }
}
