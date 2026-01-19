package ch.zhaw.ssdd.bookstack.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zhaw.ssdd.bookstack.adapters.out.memory.InMemoryBookPersistenceAdapter;
import ch.zhaw.ssdd.bookstack.application.port.in.ListAllBooksUseCase;
import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.application.service.QueryBooksService;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;

public class ListAllBooksUseCaseTest {

    private BookPersistence bookPersistence;
    private ListAllBooksUseCase usecase;
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
    void test_successfull() {
        List<Book> result = usecase.listAllBooks();
        assertEquals(List.of(book1, book2), result);
    }
}
