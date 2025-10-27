//package com.example.Backend.repository;

//public interface CategoryRepository {

//}
// In: src/main/java/com/example/Backend/repository/CategoryRepository.java

package com.example.Backend.repository;

import com.example.Backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // This interface is intentionally blank for now.
    // JpaRepository gives us findById(), findAll(), etc.
}
