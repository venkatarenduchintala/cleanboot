package ch.zhaw.ssdd.bookstack.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.zhaw.ssdd.bookstack.application.port.in.RegisterBookUseCase;
import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.domain.model.Book;

@Service
public final class RegisterBookService implements RegisterBookUseCase {

    private final BookPersistence bookPersistence;

    public RegisterBookService(
            BookPersistence bookPersistence
    ) {
        this.bookPersistence = bookPersistence;
    }

    @Override
    @Transactional
    public void register(Book book) {
        if (bookPersistence.findBy(book.isbn()).isPresent()) {
            throw new IllegalStateException(
                "Book with ISBN " + book.isbn().value() + " already exists"
            );
        }
        bookPersistence.save(book);
    }
}
