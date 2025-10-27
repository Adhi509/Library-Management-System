//package com.example.Backend.entity;

//public class User {

//}
// Put this file in: src/main/java/com/example/myproject/entity/User.java

//package com.example.Backend.entity;

//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//public class User {

   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
  //  private Long id;

  //  @Column(nullable = false, unique = true, length = 100)
    //private String email;

   // @Column(nullable = false)
   // private String password; // This will store the hashed password

    // --- Relationship ---
    
    // A User has one Role. A Role can have many Users.
   // @ManyToOne(fetch = FetchType.EAGER) // Fetch.EAGER means "always load the Role with the User"
   // @JoinColumn(name = "role_id", nullable = false) // This creates the 'role_id' foreign key column
   // private Role role;
//}
package com.example.Backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data; // <-- IMPORT THIS
import lombok.NoArgsConstructor;

@Data // <-- ADD THIS ANNOTATION
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email; // @Data will create getEmail()

    @Column(nullable = false)
    private String password; // @Data will create getPassword()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // @Data will create getRole()
}