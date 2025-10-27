//package com.example.Backend.repository;

//public interface UserRepository {

//}
// Put this file in: src/main/java/com/example/myproject/repository/UserRepository.java

package com.example.Backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.example.Backend.entity.Role;
import com.example.Backend.entity.User;

// This is an interface, not a class
// JpaRepository<[Entity Type], [Primary Key Type]>
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA will automatically create a query for us
    // based on the method name.
    // It will find a User by their 'email' field.
    Optional<User> findByEmail(String email);
    //Optional<Role> findByName(String name);
    Boolean existsByEmail(String email);
}