//#package com.example.Backend.entity;

//#public class Role {

//}
//Put this file in: src/main/java/com/example/myproject/entity/Role.java

package com.example.Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, etc.
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
@Entity // Tells JPA this class is a table in the database
@Table(name = "roles") // Specifies the actual table name
public class Role {

 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

@Id // Marks this field as the primary key
 @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the ID
 private Long id;

 @Column(nullable = false, unique = true, length = 50) // Adds constraints: not null, must be unique
 private String name; // e.g., "ROLE_ADMIN", "ROLE_MEMBER"
}