package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.Currency;
import ch.zhaw.ssdd.cleanboot.domain.model.UnitPrice;

class UnitPriceTest {

    @Test
    void nullValueThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new UnitPrice(null, Currency.USD));
    }

    @Test
    void negativeValueThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new UnitPrice(BigDecimal.valueOf(-1), Currency.USD));
    }

    @Test
    void valueExceedingMaxPriceThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new UnitPrice(BigDecimal.valueOf(1000.0001), Currency.USD));
    }

    @Test
    void nullCurrencyThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new UnitPrice(BigDecimal.valueOf(1.23), null));
    }

    @Test
    void roundedValueCannotBeZero() {
        // 0.00005 rounds to 0.0001 at 4 decimals
        UnitPrice price = new UnitPrice(BigDecimal.valueOf(0.00005), Currency.USD);
        assertNotNull(price);
        assertEquals(new BigDecimal("0.0001"), price.value());
    }

    @Test
    void priceCannotBeTooLow() {
        assertThrows(IllegalArgumentException.class, 
            () -> new UnitPrice(BigDecimal.valueOf(0.00004), Currency.USD));
    }

    @Test
    void validPriceIsAccepted() {
        UnitPrice price = new UnitPrice(BigDecimal.valueOf(123.4567), Currency.EUR);
        assertNotNull(price);
        // Should round to 4 decimals
        assertEquals(new BigDecimal("123.4567"), price.value());
        assertEquals(Currency.EUR, price.currency());
    }
}
