package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.Unit;

class UnitTest {

    @Test
    void identityUnitsToBaseAndBack() {
        Unit u = Unit.AMPERE;
        BigDecimal val = new BigDecimal("2.5");
        BigDecimal base = u.toBase(val);
        BigDecimal result = u.fromBase(base);
        assertEquals(val, result);
    }

    @Test
    void scaledUnitsConversion() {
        BigDecimal milli = new BigDecimal("1500"); // 1500 mA
        BigDecimal expectedAmpere = new BigDecimal("1.5");
        assertEquals(0, expectedAmpere.compareTo(Unit.MILLIAMPERE.toBase(milli)));

        BigDecimal back = Unit.MILLIAMPERE.fromBase(expectedAmpere);
        assertEquals(0, milli.compareTo(back));
    }

    @Test
    void offsetUnitsConversion() {
        BigDecimal celsius = new BigDecimal("25");
        BigDecimal expectedKelvin = new BigDecimal("298.15"); // 25 + 273.15
        assertEquals(0, expectedKelvin.compareTo(Unit.CELSIUS.toBase(celsius)));

        BigDecimal back = Unit.CELSIUS.fromBase(expectedKelvin);
        assertEquals(0, celsius.compareTo(back));
    }

    @Test
    void inversionPropertyHolds() {
        BigDecimal val = new BigDecimal("123.456");
        for (Unit unit : Unit.values()) {
            BigDecimal base = unit.toBase(val);
            BigDecimal result = unit.fromBase(base);
            // Use compareTo to ignore BigDecimal scale differences
            assertEquals(0, val.compareTo(result), "Failed for unit: " + unit);
        }
    }
}
