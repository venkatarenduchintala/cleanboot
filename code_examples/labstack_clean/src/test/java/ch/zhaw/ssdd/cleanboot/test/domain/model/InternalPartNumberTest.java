package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.InternalPartNumber;

class InternalPartNumberTest {

    @Test
    void nullValueIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new InternalPartNumber(null));
    }

    @Test
    void invalidCharactersAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new InternalPartNumber("ABC#123"));
    }

    @Test
    void validValueIsAccepted() {
        InternalPartNumber ipn = new InternalPartNumber("ABC-123_45");
        assertEquals("ABC-123_45", ipn.value());
    }

    @Test
    void valueIsTrimmed() {
        InternalPartNumber ipn = new InternalPartNumber("  ABC-123  ");
        assertEquals("ABC-123", ipn.value());
    }
}
