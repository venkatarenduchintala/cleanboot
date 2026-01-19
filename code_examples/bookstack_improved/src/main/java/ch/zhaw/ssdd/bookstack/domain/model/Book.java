package ch.zhaw.ssdd.bookstack.domain.model;

public record Book(
        Title title,
        Isbn isbn
) {
    
    public Book {
        if (title == null)
            throw new IllegalArgumentException("A book must have a non-null title");

        if (isbn == null)
            throw new IllegalArgumentException("A book must have a non-null ISBN");
       
    }
}
