package ch.zhaw.ssdd.bookstack.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import ch.zhaw.ssdd.bookstack.adapters.out.persistance.JpaBookPersistenceAdapter;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JpaBookPersistenceAdapterTest {
    
    @Autowired
    JpaBookPersistenceAdapter adapter;

    @Test
    void persists_and_loads_book() {
        Book book = new Book(
                new Title("Effective Java"),
                new Isbn("9780134685991")
        );

        adapter.save(book);
        assertTrue(adapter.findBy(book.isbn()).isPresent());
    }
}
