//package com.example.Backend.repository;

//public interface BookRepository {

//}
// In: src/main/java/com/example/myproject/repository/BookRepository.java

package com.example.Backend.repository;

import com.example.Backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Spring Data JPA query for searching by title
    List<Book> findByTitleContaining(String title);

    // Spring Data JPA query for searching by author
    List<Book> findByAuthorContaining(String author);
}