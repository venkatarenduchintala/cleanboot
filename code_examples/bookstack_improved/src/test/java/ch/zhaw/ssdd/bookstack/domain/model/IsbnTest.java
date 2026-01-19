package ch.zhaw.ssdd.bookstack.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.bookstack.domain.exception.DomainValidationException;

class IsbnTest {

    @Test
    void accepts_valid_isbn_13() {
        var isbn = new Isbn("978-0-321-12521-7");
        assertEquals("9780321125217", isbn.value());
    }

    @Test
    void accepts_isbn_10_with_x() {
        var isbn = new Isbn("0-321-14653-X");
        assertEquals("032114653X", isbn.value());
    }

    @Test
    void rejects_invalid_format() {
        assertThrows(
                DomainValidationException.class,
                () -> new Isbn("123")
        );
    }

    @Test
    void rejects_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Isbn(null)
        );
    }
}
