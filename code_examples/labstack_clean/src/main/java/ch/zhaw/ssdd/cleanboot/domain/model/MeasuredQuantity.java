package ch.zhaw.ssdd.cleanboot.domain.model;

import java.math.BigDecimal;

public enum MeasuredQuantity {

    CURRENT(true),
    VOLTAGE(true),
    TEMPERATURE(true),
    ABSOLUTE_TEMPERATURE(false),
    POWER(false),
    RESISTANCE(false),
    CAPACITANCE(false),
    INDUCTANCE(false),
    FREQUENCY(false),
    TIME(false),
    LENGTH(false),
    DIMENSIONLESS(true);

    private final boolean allowNegative;

    MeasuredQuantity(boolean allowNegative) {
        this.allowNegative = allowNegative;
    }

    public void validate(BigDecimal valueInBaseUnit) {
        if (!allowNegative && valueInBaseUnit.signum() < 0) {
            throw new IllegalArgumentException(
                    name() + " must not be negative: " + valueInBaseUnit
            );
        }
    }
}
