package ch.zhaw.ssdd.bookstack.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRespository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
    void deleteByTitle(String title);
}
