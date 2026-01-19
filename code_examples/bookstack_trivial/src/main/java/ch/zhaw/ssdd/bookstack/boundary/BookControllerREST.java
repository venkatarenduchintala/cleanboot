package ch.zhaw.ssdd.bookstack.boundary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.ssdd.bookstack.controller.BookService;
import ch.zhaw.ssdd.bookstack.entity.Book;

@RestController
@RequestMapping("/api/book")
public class BookControllerREST {
    
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.listAllBooks();
    }

    @PutMapping("/{title}")
    public Book updateISBN(@PathVariable String title, @RequestBody String isbn) {
        return bookService.updateISBN(title, isbn);
    }

    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title) {
        bookService.deleteBook(title);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createNewBook(book.getTitle(), book.getIsbn());
    }


}
