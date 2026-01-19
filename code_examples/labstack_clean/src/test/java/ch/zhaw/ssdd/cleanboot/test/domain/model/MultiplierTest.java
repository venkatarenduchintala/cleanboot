package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.Multiplier;

class MultiplierTest {

    @Test
    void zeroMultiplierIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Multiplier(0));
    }

    @Test
    void negativeMultiplierIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Multiplier(-1));
    }

    @Test
    void multiplierOfOneIsAllowed() {
        Multiplier multiplier = new Multiplier(1);
        assertEquals(1, multiplier.value());
    }

    @Test
    void multiplierGreaterThanOneIsAllowed() {
        Multiplier multiplier = new Multiplier(10);
        assertEquals(10, multiplier.value());
    }
}
