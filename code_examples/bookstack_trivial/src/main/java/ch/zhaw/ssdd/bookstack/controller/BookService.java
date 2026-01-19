package ch.zhaw.ssdd.bookstack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.zhaw.ssdd.bookstack.entity.Book;
import ch.zhaw.ssdd.bookstack.entity.BookRespository;

@Service
public class BookService {
    
    @Autowired
    private BookRespository bookRespository;

    public List<Book> listAllBooks() {
        return bookRespository.findAll();
    }

    public Book createNewBook(String title, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        bookRespository.save(book);
        return book;
    }

    public Book bookInfo(String title) {
        return bookRespository.findByTitle(title);
    }

    @Transactional
    public void deleteBook(String title) {
        bookRespository.deleteByTitle(title);
    }

    @Transactional
    public Book updateISBN(String title, String newISBN) {
        Book b = bookRespository.findByTitle(title);
        b.setIsbn(newISBN);
        return b;
    }
}
