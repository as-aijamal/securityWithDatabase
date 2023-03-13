package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
