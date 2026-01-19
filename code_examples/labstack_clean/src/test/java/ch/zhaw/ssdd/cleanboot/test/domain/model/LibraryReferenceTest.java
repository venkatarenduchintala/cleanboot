package ch.zhaw.ssdd.cleanboot.test.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.cleanboot.domain.model.LibraryReference;

class LibraryReferenceTest {

    @Test
    void nullNameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LibraryReference(null));
    }

    @Test
    void invalidCharactersAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> new LibraryReference("ABC@:DEF+"));
        assertThrows(IllegalArgumentException.class, () -> new LibraryReference("ABC123DEF456"));
    }

    @Test
    void validNameIsAccepted() {
        LibraryReference d = new LibraryReference("Device:R");
        assertEquals("Device:R", d.value());
    }

    @Test
    void nameIsTrimmed() {
        LibraryReference d = new LibraryReference("  Device:R  ");
        assertEquals("Device:R", d.value());
    }
}
