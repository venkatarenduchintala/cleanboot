package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.MeasuredQuantity;

class MeasuredQuantityTest {

    @Test
    void negativeAllowedQuantitiesAreAccepted() {
        MeasuredQuantity[] allowNegative = {
                MeasuredQuantity.CURRENT,
                MeasuredQuantity.VOLTAGE,
                MeasuredQuantity.TEMPERATURE,
                MeasuredQuantity.DIMENSIONLESS
        };

        for (MeasuredQuantity q : allowNegative) {
            assertDoesNotThrow(() -> q.validate(BigDecimal.valueOf(-1)));
            assertDoesNotThrow(() -> q.validate(BigDecimal.ZERO));
            assertDoesNotThrow(() -> q.validate(BigDecimal.ONE));
        }
    }

    @Test
    void negativeDisallowedQuantitiesAreRejected() {
        MeasuredQuantity[] disallowNegative = {
                MeasuredQuantity.POWER,
                MeasuredQuantity.RESISTANCE,
                MeasuredQuantity.CAPACITANCE,
                MeasuredQuantity.INDUCTANCE,
                MeasuredQuantity.FREQUENCY,
                MeasuredQuantity.TIME,
                MeasuredQuantity.LENGTH
        };

        for (MeasuredQuantity q : disallowNegative) {
            assertThrows(IllegalArgumentException.class, () -> q.validate(BigDecimal.valueOf(-1)));
            assertDoesNotThrow(() -> q.validate(BigDecimal.ZERO));
            assertDoesNotThrow(() -> q.validate(BigDecimal.ONE));
        }
    }
}
