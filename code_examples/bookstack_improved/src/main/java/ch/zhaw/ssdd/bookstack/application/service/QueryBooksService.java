package ch.zhaw.ssdd.bookstack.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.zhaw.ssdd.bookstack.application.port.in.ListAllBooksUseCase;
import ch.zhaw.ssdd.bookstack.application.port.in.LookupBookUseCase;
import ch.zhaw.ssdd.bookstack.application.port.out.BookPersistence;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;

@Service
public class QueryBooksService implements ListAllBooksUseCase, LookupBookUseCase {

    private final BookPersistence findBook;

    public QueryBooksService(BookPersistence findBook) {
        this.findBook = findBook;
    }

    @Override
    public List<Book> listAllBooks() {
        return findBook.findAll();
    }

    @Override
    public Optional<Book> lookupIsbn(Isbn isbn) {
        return findBook.findBy(isbn);
    }
    
}
