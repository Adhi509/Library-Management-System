//package com.example.Backend.entity;

//public class Book {

//}
// Put this file in: src/main/java/com/example/myproject/entity/Book.java

package com.example.Backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true)
    private String isbn;

    private int totalCopies; // Total copies of the book owned
    private int availableCopies; // Copies currently available for borrowing

    // --- Relationship ---

    @ManyToMany(fetch = FetchType.LAZY) // Fetch.LAZY = "only load categories when we ask for them"
    @JoinTable(
        name = "book_categories", // Name of the new (third) table
        joinColumns = @JoinColumn(name = "book_id"), // Column in this new table for the book's ID
        inverseJoinColumns = @JoinColumn(name = "category_id") // Column for the category's ID
    )
    // Use Set to prevent duplicate categories for a single book
    private Set<Category> categories = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getTotalCopies() {
		return totalCopies;
	}

	public void setTotalCopies(int totalCopies) {
		this.totalCopies = totalCopies;
	}

	public int getAvailableCopies() {
		return availableCopies;
	}

	public void setAvailableCopies(int availableCopies) {
		this.availableCopies = availableCopies;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	} 
    
}