import React, { createContext, useState, useContext, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode'; // You might need to install jwt-decode: npm install jwt-decode

// 1. Create the context
const AuthContext = createContext(null);

// 2. Create the AuthProvider component
export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [user, setUser] = useState(null);

  // 3. useEffect to run on initial load
  // This checks if a token exists in localStorage and sets the user state
  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken) {
      try {
        const decodedUser = jwtDecode(storedToken);
        setUser(decodedUser);
        setToken(storedToken);
      } catch (error) {
        console.error("Invalid token:", error);
        localStorage.removeItem('token'); // Clear invalid token
      }
    }
  }, []);

  // 4. Login function
  const login = (newToken) => {
    localStorage.setItem('token', newToken);
    setToken(newToken);
    try {
      const decodedUser = jwtDecode(newToken);
      setUser(decodedUser);
    } catch (error) {
      console.error("Failed to decode token on login:", error);
      setUser(null);
    }
  };

  // 5. Logout function
  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
  };

  // The value provided to the context consumers
  const value = {
    token,
    user,
    login,
    logout,
    isAuthenticated: !!token, // A simple boolean flag
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

// 6. Custom hook to use the auth context
export const useAuth = () => {
  return useContext(AuthContext);
};