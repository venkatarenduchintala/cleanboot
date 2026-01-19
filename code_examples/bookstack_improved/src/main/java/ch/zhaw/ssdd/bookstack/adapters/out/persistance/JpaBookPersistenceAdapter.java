package ch.zhaw.ssdd.bookstack.adapters.out.persistance;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;

@Service
@Profile("!inmemorytest")
public class JpaBookPersistenceAdapter
        implements BookPersistence {

    private final BookRepository bookRepository;

    public JpaBookPersistenceAdapter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Optional<Book> findBy(Isbn isbn) {
        return bookRepository.findById(isbn.value())
                .map(this::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll()
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public void save(Book book) {
        bookRepository.save(fromDomain(book));
    }

    private Book toDomain(BookEntity entity) {
        return new Book(
                new Title(entity.getTitle()),
                new Isbn(entity.getIsbn())
        );
    }

    private BookEntity fromDomain(Book book) {
        return new BookEntity(
                book.isbn().value(),
                book.title().text()
        );
    }
}