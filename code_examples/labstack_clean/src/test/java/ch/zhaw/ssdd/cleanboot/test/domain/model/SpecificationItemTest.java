package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.SpecificationItem;
import ch.zhaw.ssdd.cleanboot.domain.model.SpecifiedProperty;
import ch.zhaw.ssdd.cleanboot.domain.model.Unit;

class SpecificationItemTest {

    @Test
    void nullPropertyThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new SpecificationItem(null, BigDecimal.ONE, Unit.VOLT));
    }

    @Test
    void nullValueThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new SpecificationItem(SpecifiedProperty.SUPPLY_VOLTAGE_MAX, null, Unit.VOLT));
    }

    @Test
    void nullUnitThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new SpecificationItem(SpecifiedProperty.SUPPLY_VOLTAGE_MAX, BigDecimal.ONE, null));
    }

    @Test
    void unitQuantityMismatchThrows() {
        // Temperature property, voltage unit
        assertThrows(IllegalArgumentException.class,
                () -> new SpecificationItem(SpecifiedProperty.OPERATING_TEMPERATURE_MAX, BigDecimal.ONE, Unit.VOLT));
    }

    @Test
    void negativeValueForDisallowedQuantityThrows() {
        // POWER does not allow negative
        assertThrows(IllegalArgumentException.class,
                () -> new SpecificationItem(SpecifiedProperty.POWER_DISSIPATION_MAX, BigDecimal.valueOf(-5),
                        Unit.WATT));
    }

    @Test
    void validSpecificationItemIsAccepted() {
        SpecificationItem item = new SpecificationItem(
                SpecifiedProperty.OPERATING_VOLTAGE_MAX,
                BigDecimal.valueOf(5),
                Unit.VOLT);
        assertEquals(BigDecimal.valueOf(5), item.value());
        assertEquals(Unit.VOLT, item.unit());
        assertEquals(SpecifiedProperty.OPERATING_VOLTAGE_MAX, item.property());
    }

    @Test
    void valueInBaseUnitReturnsCorrectValue() {
        SpecificationItem item = new SpecificationItem(
                SpecifiedProperty.OPERATING_TEMPERATURE_MIN,
                BigDecimal.valueOf(25),
                Unit.CELSIUS);
        assertEquals(new BigDecimal("298.15"), item.valueInBaseUnit());
    }
}
