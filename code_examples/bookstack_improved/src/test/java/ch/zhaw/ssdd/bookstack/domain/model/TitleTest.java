package ch.zhaw.ssdd.bookstack.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.bookstack.domain.exception.DomainValidationException;

class TitleTest {
    
    @Test
    void rejects_null_title() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Title(null)
        );
    }

    @Test
    void rejects_too_short_title() {
        assertThrows(
                DomainValidationException.class,
                () -> new Title("DDD")
        );
    }

    @Test
    void rejects_invalid_characters() {
        assertThrows(
                DomainValidationException.class,
                () -> new Title("Clean Code @")
        );
    }
    @Test
    void accepts_invalid_title_with_trim() {
        var title = new Title(" Java ist auch eine Insel ");
        assertEquals("Java ist auch eine Insel", title.text());
    }
}
