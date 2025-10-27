import axios from 'axios';

// Create an Axios instance with a base URL
const api = axios.create({
  baseURL: 'http://localhost:8085/api', // Your Spring Boot backend URL
});

// Axios Request Interceptor
// This function runs BEFORE each request is sent
api.interceptors.request.use(
  (config) => {
    // 1. Get the token from localStorage
    const token = localStorage.getItem('token');

    // 2. If the token exists, add it to the Authorization header
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config; // Return the modified config
  },
  (error) => {
    // Handle request error here
    return Promise.reject(error);
  }
);

export default api;