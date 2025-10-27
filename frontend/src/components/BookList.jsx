import React, { useState, useEffect } from 'react';
import api from '../services/api'; // Our configured Axios instance
import './BookList.css';

const BookList = () => {
  // 1. State for books, loading, errors, and search filters
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const [titleQuery, setTitleQuery] = useState('');
  const [authorQuery, setAuthorQuery] = useState('');

  // 2. useEffect to fetch data
  // This runs on component mount AND when titleQuery or authorQuery changes
  useEffect(() => {
    const fetchBooks = async () => {
      setLoading(true);
      try {
        // 3. Build query parameters
        const params = new URLSearchParams();
        if (titleQuery) {
          params.append('title', titleQuery);
        }
        if (authorQuery) {
          params.append('author', authorQuery);
        }

        // 4. Make API call with query parameters
        const response = await api.get(`/books?${params.toString()}`);
        setBooks(response.data);
        setError(null);
      } catch (err) {
        console.error("Failed to fetch books:", err);
        setError("Failed to fetch books. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    // We add a small delay (debounce) so the API isn't called on every keystroke
    const timer = setTimeout(() => {
      fetchBooks();
    }, 500); // 500ms delay

    // Cleanup function to clear the timer
    return () => clearTimeout(timer);

  }, [titleQuery, authorQuery]); // Dependency array

  // 5. Render the component UI
  return (
    <div className="book-list-container">
      <h2>Book Catalog</h2>

      <div className="book-filters">
        <input
          type="text"
          placeholder="Search by title..."
          value={titleQuery}
          onChange={(e) => setTitleQuery(e.target.value)}
        />
        <input
          type="text"
          placeholder="Search by author..."
          value={authorQuery}
          onChange={(e) => setAuthorQuery(e.target.value)}
        />
      </div>

      {loading && <p>Loading books...</p>}
      {error && <p className="error-message">{error}</p>}
      
      {!loading && !error && (
        <div className="book-grid">
          {books.length > 0 ? (
            books.map(book => (
              <div key={book.id} className="book-card">
                <h3>{book.title}</h3>
                <p>by {book.author}</p>
                <p>ISBN: {book.isbn}</p>
                <p>
                  <strong>Availability:</strong> {book.availableCopies} / {book.totalCopies}
                </p>
              </div>
            ))
          ) : (
            <p>No books found matching your criteria.</p>
          )}
        </div>
      )}
    </div>
  );
};

export default BookList;