package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.ManufacturerPartNumber;

class ManufacturerPartNumberTest {

    @Test
    void nullValueIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ManufacturerPartNumber(null));
    }

    @Test
    void invalidCharactersAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new ManufacturerPartNumber("MPN#123"));
    }

    @Test
    void validValueIsAccepted() {
        ManufacturerPartNumber mpn = new ManufacturerPartNumber("SN74HC00N");

        assertEquals("SN74HC00N", mpn.value());
    }

    @Test
    void valueIsTrimmed() {
        ManufacturerPartNumber mpn = new ManufacturerPartNumber("  SN74HC00N  ");

        assertEquals("SN74HC00N", mpn.value());
    }
}
