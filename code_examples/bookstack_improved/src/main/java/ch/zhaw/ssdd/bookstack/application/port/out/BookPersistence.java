package ch.zhaw.ssdd.bookstack.application.port.out;

import java.util.List;
import java.util.Optional;

import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;

public interface BookPersistence {
    List<Book> findAll();
    Optional<Book> findBy(Isbn isbn);
    void save(Book book);
}