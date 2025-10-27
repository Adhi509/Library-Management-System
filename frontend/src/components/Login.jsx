import React, { useState } from 'react';
import './Form.css';
import api from '../services/api';
import { useAuth } from '../context/AuthContext'; // <-- Import useAuth
import { useNavigate } from 'react-router-dom'; // <-- Import useNavigate

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  
  const { login } = useAuth(); // <-- Get the login function from context
  const navigate = useNavigate(); // <-- Get the navigation function

  const handleSubmit = async (e) => {
    e.preventDefault();
    // ... (Your existing validation logic) ...

    const validationErrors = {};
    if (!email) validationErrors.email = 'Email is required';
    if (!password) validationErrors.password = 'Password is required';
    
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      try {
        const response = await api.post('/auth/login', {
          email: email,
          password: password,
        });

        const token = response.data.accessToken;

        // --- THIS IS THE UPDATE ---
        // 1. Call the context login function
        login(token); 
        
        // 2. Redirect to the dashboard
        navigate('/dashboard'); 
        // --- END OF UPDATE ---

      } catch (error) {
        console.error('Login failed:', error);
        setErrors({ form: 'Invalid email or password.' });
      }
    }
  };

  return (
    <div className="auth-container">
      <form className="auth-form" onSubmit={handleSubmit}>
        <h2>Login</h2>
        {errors.form && <p className="error-message">{errors.form}</p>}
        {/* ... (rest of your form inputs) ... */}
        
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
        
        <button type="submit" className="auth-button">Login</button>
      </form>
    </div>
  );
};

export default Login;