package ch.zhaw.ssdd.bookstack.adapters.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.ssdd.bookstack.application.port.in.ListAllBooksUseCase;
import ch.zhaw.ssdd.bookstack.application.port.in.LookupBookUseCase;
import ch.zhaw.ssdd.bookstack.application.port.in.RegisterBookUseCase;
import ch.zhaw.ssdd.bookstack.domain.model.Book;
import ch.zhaw.ssdd.bookstack.domain.model.Isbn;
import ch.zhaw.ssdd.bookstack.domain.model.Title;

@RestController
@RequestMapping("/api/books")
class BookController {

    private final RegisterBookUseCase registerBookUseCase;
    private final ListAllBooksUseCase listAllBooksUseCase;
    private final LookupBookUseCase lookupBookUseCase;

    BookController(
            RegisterBookUseCase registerBook,
            ListAllBooksUseCase listAllBooksUseCase,
            LookupBookUseCase lookupBookUseCase) {
        this.registerBookUseCase = registerBook;
        this.listAllBooksUseCase = listAllBooksUseCase;
        this.lookupBookUseCase = lookupBookUseCase;
    }
    @GetMapping
    List<BookDto> getAllBooks() {
        return listAllBooksUseCase.listAllBooks()
                .stream()
                .map(this::fromDomain)
                .toList();
    }
    @GetMapping("/{isbn}")
    ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
        return ResponseEntity.of(
                lookupBookUseCase.lookupIsbn(new Isbn(isbn))
                        .map(this::fromDomain));
    }
    @PostMapping
    void register(@RequestBody BookDto dto) {
        var book = new Book(
                new Title(dto.title()),
                new Isbn(dto.isbn()));

        registerBookUseCase.register(book);
    }
    private BookDto fromDomain(Book book) {
        return new BookDto(
                book.title().text(),
                book.isbn().value());
        }
}
