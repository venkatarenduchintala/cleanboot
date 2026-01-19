package ch.zhaw.ssdd.bookstack.application;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.bookstack.adapters.out.memory.InMemoryBookPersistenceAdapter;
import ch.zhaw.ssdd.bookstack.application.port.in.RegisterBookUseCase;
import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.application.service.RegisterBookService;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;

public class RegisterBookUseCaseTest {
    
    private static BookPersistence bookPersistence;
    private static RegisterBookUseCase usecase;

    @BeforeEach
    void setup() {
        bookPersistence = new InMemoryBookPersistenceAdapter();
        usecase = new RegisterBookService(bookPersistence);
    }

        @Test
    void registers_book_if_not_existing() {
        var book = new Book(
                new Title("Domain-Driven Design"),
                new Isbn("9780321125217")
        );
        usecase.register(book);
        assertTrue(bookPersistence.findBy(book.isbn()).isPresent());
    }

    @Test
    void rejects_book_with_existing_isbn() {
        var book = new Book(
                new Title("Domain-Driven Design"),
                new Isbn("9780321125217")
        );
        usecase.register(book);
        assertThrows(
                IllegalStateException.class,
                () -> usecase.register(book)
        );
    }
}
