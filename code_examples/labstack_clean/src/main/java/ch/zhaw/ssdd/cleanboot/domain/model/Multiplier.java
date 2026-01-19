package ch.zhaw.ssdd.cleanboot.domain.model;

public record Multiplier(
        int value) {
    public Multiplier {
        if (value < 1) {
            throw new IllegalArgumentException("A multiplier must be greater zero");
        }
    }
}
