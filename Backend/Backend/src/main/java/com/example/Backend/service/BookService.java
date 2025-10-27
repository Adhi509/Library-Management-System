//package com.example.Backend.service;

//public class BookService {

//}
// In: src/main/java/com/example/myproject/service/BookService.java

package com.example.Backend.service;

import com.example.Backend.dto.BookDto;
import com.example.Backend.entity.Book;
import com.example.Backend.entity.Category;
import com.example.Backend.exception.ResourceNotFoundException;
import com.example.Backend.repository.BookRepository;
import com.example.Backend.repository.CategoryRepository; // <-- You need to create this
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    // --- GET (All with Filters) ---
    public List<Book> getAllBooks(String title, String author) {
        if (title != null) {
            return bookRepository.findByTitleContaining(title);
        }
        if (author != null) {
            return bookRepository.findByAuthorContaining(author);
        }
        return bookRepository.findAll();
    }

    // --- GET (by ID) ---
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    // --- POST ---
    public Book createBook(BookDto bookDto) {
        Book book = new Book();
        mapDtoToEntity(bookDto, book);
        book.setAvailableCopies(bookDto.getTotalCopies()); // Initially, all copies are available
        return bookRepository.save(book);
    }

    // --- PUT ---
    public Book updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));

        // Note: This logic for available copies might need to be more complex
        // e.g., (new total) - (total - current available)
        int borrowedCopies = book.getTotalCopies() - book.getAvailableCopies();
        book.setAvailableCopies(bookDto.getTotalCopies() - borrowedCopies);

        mapDtoToEntity(bookDto, book);
        return bookRepository.save(book);
    }

    // --- DELETE ---
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
        bookRepository.delete(book);
    }

    // --- Helper Method ---
    private void mapDtoToEntity(BookDto bookDto, Book book) {
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setTotalCopies(bookDto.getTotalCopies());

        // Find categories by their IDs and set them
        Set<Category> categories = bookDto.getCategoryIds().stream()
                .map(catId -> categoryRepository.findById(catId)
                        .orElseThrow(() -> new ResourceNotFoundException("Category", "id", catId)))
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }
}

// *** ACTION REQUIRED ***
// You also need to create CategoryRepository:
// In `com.example.myproject.repository.CategoryRepository.java`
// public interface CategoryRepository extends JpaRepository<Category, Long> {}