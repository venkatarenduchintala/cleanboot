package ch.zhaw.ssdd.bookstack.application.port.in;

import java.util.Optional;

import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;

public interface LookupBookUseCase {
    Optional<Book> lookupIsbn(Isbn isbn);
}
