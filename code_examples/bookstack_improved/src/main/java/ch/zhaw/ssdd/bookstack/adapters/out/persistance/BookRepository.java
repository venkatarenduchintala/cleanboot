package ch.zhaw.ssdd.bookstack.adapters.out.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, String> {
    
}
