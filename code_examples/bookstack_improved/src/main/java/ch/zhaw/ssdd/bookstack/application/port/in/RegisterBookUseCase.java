package ch.zhaw.ssdd.bookstack.application.port.in;

import ch.zhaw.ssdd.bookstack.domain.model.Book;

public interface RegisterBookUseCase {
    void register(Book book);
}
