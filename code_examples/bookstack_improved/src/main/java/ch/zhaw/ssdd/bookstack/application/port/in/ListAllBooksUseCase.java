package ch.zhaw.ssdd.bookstack.application.port.in;

import java.util.List;

import ch.zhaw.ssdd.bookstack.domain.model.Book;

public interface ListAllBooksUseCase {
    List<Book> listAllBooks();
}
