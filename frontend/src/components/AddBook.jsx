import React, { useState } from 'react';
import api from '../services/api';
import { useNavigate } from 'react-router-dom';
import './Form.css'; // Reuse our existing form styles

const AddBook = () => {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [isbn, setIsbn] = useState('');
  const [totalCopies, setTotalCopies] = useState(1);
  const [categoryIds, setCategoryIds] = useState(''); // Simple text input for demo
  
  const [errors, setErrors] = useState({});
  const [formError, setFormError] = useState(null);
  
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setFormError(null);

    // TODO: Add client-side validation here...

    // Convert comma-separated string of IDs to an array of numbers
    const categoryIdArray = categoryIds.split(',')
                                    .map(id => parseInt(id.trim()))
                                    .filter(id => !isNaN(id));

    if (categoryIdArray.length === 0) {
      setErrors({ categories: "At least one valid Category ID is required." });
      return;
    }

    const bookData = {
      title,
      author,
      isbn,
      totalCopies: parseInt(totalCopies),
      categoryIds: categoryIdArray,
    };

    try {
      // 1. Call the POST API endpoint
      await api.post('/books', bookData);
      
      // 2. Redirect to the book list on success
      navigate('/books');
      
    } catch (err) {
      console.error("Failed to add book:", err);
      if (err.response && err.response.data) {
        // Handle validation errors from the backend
        setErrors(err.response.data);
      } else {
        setFormError("An error occurred. Please try again.");
      }
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Add New Book</h2>
        {formError && <p className="error-message">{formError}</p>}

        <div className="form-group">
          <label>Title</label>
          <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
          {errors.title && <p className="error-message">{errors.title}</p>}
        </div>

        <div className="form-group">
          <label>Author</label>
          <input type="text" value={author} onChange={(e) => setAuthor(e.target.value)} />
          {errors.author && <p className="error-message">{errors.author}</p>}
        </div>

        <div className="form-group">
          <label>ISBN</label>
          <input type="text" value={isbn} onChange={(e) => setIsbn(e.target.value)} />
          {errors.isbn && <p className="error-message">{errors.isbn}</p>}
        </div>

        <div className="form-group">
          <label>Total Copies</label>
          <input type="number" min="1" value={totalCopies} onChange={(e) => setTotalCopies(e.target.value)} />
          {errors.totalCopies && <p className="error-message">{errors.totalCopies}</p>}
        </div>

        <div className="form-group">
          <label>Category IDs (comma-separated)</label>
          <input type="text" value={categoryIds} onChange={(e) => setCategoryIds(e.target.value)} />
          {errors.categoryIds && <p className="error-message">{errors.categoryIds}</p>}
          {errors.categories && <p className="error-message">{errors.categories}</p>}
          <small>Note: You'll need to know the IDs (e.g., 1, 2). A future update would use a dropdown.</small>
        </div>

        <button type="submit" className="auth-button">Add Book</button>
      </form>
    </div>
  );
};

export default AddBook;