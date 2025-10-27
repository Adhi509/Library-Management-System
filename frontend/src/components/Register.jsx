// In: src/components/Register.jsx

import React, { useState } from 'react';
import './Form.css';
import api from '../services/api'; // <-- IMPORT API
import { useNavigate } from 'react-router-dom'; // <-- IMPORT NAVIGATE

const Register = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errors, setErrors] = useState({});
  
  const navigate = useNavigate(); // <-- Initialize navigate

  const handleSubmit = async (e) => { // <-- Make the function async
    e.preventDefault();

    // --- Client-Side Validation ---
    const validationErrors = {};
    if (!email) validationErrors.email = 'Email is required';
    if (!/\S+@\S+\.\S+/.test(email)) validationErrors.email = 'Email is not valid';
    if (!password) validationErrors.password = 'Password is required';
    if (password.length < 6) validationErrors.password = 'Password must be at least 6 characters';
    if (password !== confirmPassword) validationErrors.confirmPassword = 'Passwords do not match';
    setErrors(validationErrors);

    // 4. If no errors, proceed with API call
    if (Object.keys(validationErrors).length === 0) {
      try {
        // --- ADD THIS API CALL ---
        const registerData = { email, password };
        
        // Call the backend /api/auth/register endpoint
        await api.post('/auth/register', registerData);
        
        // If successful, navigate to the login page
        navigate('/login');
        
      } catch (error) {
        console.error("Registration failed:", error);
        if (error.response && error.response.data) {
          // Show backend error (e.g., "Email is already taken!")
          setErrors({ form: error.response.data });
        } else {
          setErrors({ form: "Registration failed. Please try again." });
        }
      }
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Register</h2>
        {/* Add this line to show form-level errors */}
        {errors.form && <p className="error-message">{errors.form}</p>}
        
        {/* ... (rest of your form is the same) ... */}

        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          {errors.email && <p className="error-message">{errors.email}</p>}
        </div>
        
        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          {errors.password && <p className="error-message">{errors.password}</p>}
        </div>
        
        <div className="form-group">
          <label>Confirm Password</label>
          <input
            type="password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
          {errors.confirmPassword && <p className="error-message">{errors.confirmPassword}</p>}
        </div>
        
        <button type="submit" className="auth-button">Register</button>
      </form>
    </div>
  );
};

export default Register;