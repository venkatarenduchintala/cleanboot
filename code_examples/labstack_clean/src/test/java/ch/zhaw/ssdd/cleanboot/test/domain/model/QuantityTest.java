package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.Quantity;

class QuantityTest {

    @Test
    void negativeQuantityIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(-1));
    }

    @Test
    void zeroQuantityIsAllowed() {
        Quantity quantity = new Quantity(0);
        assertEquals(0, quantity.value());
    }

    @Test
    void positiveQuantityIsAllowed() {
        Quantity quantity = new Quantity(10);
        assertEquals(10, quantity.value());
    }
}
