package ch.zhaw.ssdd.bookstack.domain.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void rejects_null_title() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(null, new Isbn("9780321125217"))
        );
    }
      @Test
    void rejects_null_isbn() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Book(new Title("Java ist auch eine Insel"), null)
        );
    }
}
