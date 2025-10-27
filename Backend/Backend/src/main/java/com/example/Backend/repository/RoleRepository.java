//package com.example.Backend.repository;

//public interface RoleRepository {

//}
// In: src/main/java/com/example/myproject/repository/RoleRepository.java

package com.example.Backend.repository;

import com.example.Backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // Find a role by its name
    Optional<Role> findByName(String name);
}