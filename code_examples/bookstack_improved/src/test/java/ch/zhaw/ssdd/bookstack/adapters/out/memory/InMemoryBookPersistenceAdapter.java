package ch.zhaw.ssdd.bookstack.adapters.out.memory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;

@Component
@Profile("inmemorytest")
public final class InMemoryBookPersistenceAdapter
        implements BookPersistence {

    private final Map<Isbn, Book> store = new HashMap<>();

    @Override
    public Optional<Book> findBy(Isbn isbn) {
        return Optional.ofNullable(store.get(isbn));
    }

    @Override
    public void save(Book book) {
        store.put(book.isbn(), book);
    }

    @Override
    public List<Book> findAll() {
        return store.values().stream().toList();
    }
}
