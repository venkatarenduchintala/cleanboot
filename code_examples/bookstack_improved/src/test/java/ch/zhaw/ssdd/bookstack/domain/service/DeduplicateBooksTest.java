package ch.zhaw.ssdd.bookstack.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;

public class DeduplicateBooksTest {

    @Test
    void execute_removesDuplicatesByTitle() {

        Book book1 = new Book(new Title("Clean Code"), new Isbn("978-0-13-235088-4"));
        Book book2 = new Book(new Title("Effective Java"), new Isbn("978-0-13-468599-1"));
        
        // Duplicate Entry to 1 with modified ISBN
        Book book3 = new Book(new Title("Clean Code"), new Isbn("978-0-13-235088-5"));

        List<Book> input = List.of(book1, book2, book3);

        List<Book> result = DeduplicateBooks.execute(input);

        assertEquals(2, result.size());

        assertEquals(book1, result.get(0));
        assertEquals(book2, result.get(1));
    }
}
