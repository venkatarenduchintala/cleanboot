package ch.zhaw.ssdd.bookstack.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.bookstack.adapters.out.memory.InMemoryBookPersistenceAdapter;
import ch.zhaw.ssdd.bookstack.application.port.in.LookupBookUseCase;
import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.application.service.QueryBooksService;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;


public class LookupBookUseCaseTest {

    private BookPersistence bookPersistence;
    private LookupBookUseCase usecase;
    private Book book1;
    private Book book2;
    

    @BeforeEach
    void setup() {
        bookPersistence = new InMemoryBookPersistenceAdapter();
        usecase = new QueryBooksService(bookPersistence);

        book1 = new Book(
                new Title("Domain Driven Design"),
                new Isbn("9780321125217"));
        book2 = new Book(
                new Title("Clean Architecture"),
                new Isbn("9780134494166"));

        bookPersistence.save(book1);
        bookPersistence.save(book2);
    }

    @Test
    void lookupIsbn_returns_book_if_found() {
        Optional<Book> result = usecase.lookupIsbn(new Isbn("9780321125217"));
        assertTrue(result.isPresent());
        assertEquals(book1, result.get());
    }

    @Test
    void lookupIsbn_returns_empty_if_book_not_found() {
       

        Isbn isbn = new Isbn("9780321125218");

        Optional<Book> result = usecase.lookupIsbn(isbn);

        assertTrue(result.isEmpty());
    }

}
