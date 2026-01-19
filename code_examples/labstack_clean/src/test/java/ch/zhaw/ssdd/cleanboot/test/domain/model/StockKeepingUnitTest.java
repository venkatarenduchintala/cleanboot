package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.StockKeepingUnit;

class StockKeepingUnitTest {

    @Test
    void nullValueIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new StockKeepingUnit(null));
    }

    @Test
    void invalidCharactersAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new StockKeepingUnit("SKU#001"));
    }

    @Test
    void validValueIsAccepted() {
        StockKeepingUnit sku = new StockKeepingUnit("SKU-001_A");
        assertEquals("SKU-001_A", sku.value());
    }

    @Test
    void valueIsTrimmed() {
        StockKeepingUnit sku = new StockKeepingUnit("  SKU-001  ");
        assertEquals("SKU-001", sku.value());
    }
}
