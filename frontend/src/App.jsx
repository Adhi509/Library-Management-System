import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link, useNavigate } from 'react-router-dom';

// Component Imports
import Login from './components/Login';
import Register from './components/Register';
import ProtectedRoute from './components/ProtectedRoute';
import BookList from './components/BookList';   // <-- IMPORT THIS
import AddBook from './components/AddBook';     // <-- IMPORT THIS
// import EditBook from './components/EditBook'; // <-- We'll add this later

// Context Import
import { AuthProvider, useAuth } from './context/AuthContext';

// CSS Import
import './App.css'; 

// --- A. Placeholder Dashboard component ---
const Dashboard = () => {
  const { user, logout } = useAuth();
  return (
    <div style={{ padding: '2rem' }}>
      <h2>Dashboard</h2>
      <p>Welcome! You are logged in.</p>
      {user && (
        <div>
          <h3>Your Details:</h3>
          <p>Email: {user.sub}</p> 
          {/* We'll read the roles from the 'roles' claim we added to the JWT */}
          <p>Roles: {user.roles}</p> 
        </div>
      )}
      <button onClick={logout}>Logout</button>
    </div>
  );
};

// --- B. Updated Navigation component ---
const Navigation = () => {
  // 1. Get the 'user' object from our auth context
  const { isAuthenticated, logout, user } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login'); // Redirect to login page after logout
  };

  // 2. Check if the user has 'ADMIN' or 'LIBRARIAN' role
  // (Assumes roles are a comma-separated string in the JWT 'roles' claim)
  const isAdminOrLibrarian = user && 
                              (user.roles.includes('ROLE_ADMIN') || 
                               user.roles.includes('ROLE_LIBRARIAN'));

  return (
    <nav className="navbar">
      <div className="nav-links-left">
        <Link className="nav-brand" to={isAuthenticated ? "/dashboard" : "/"}>
          Library
        </Link>
        
        {/* 3. Add links for all authenticated users */}
        {isAuthenticated && (
          <>
            <Link to="/books">Book Catalog</Link>
            
            {/* 4. Only show "Add Book" if user is ADMIN or LIBRARIAN */}
            {isAdminOrLibrarian && (
              <Link to="/books/add">Add Book</Link>
            )}
          </>
        )}
      </div>

      <div className="nav-links">
        {isAuthenticated ? (
          <button onClick={handleLogout} className="nav-button">Logout</button>
        ) : (
          <>
            <Link to="/login">Login</Link>
            <Link to="/register">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
};

// --- C. Placeholder Home page ---
const HomePage = () => (
  <div style={{ textAlign: 'center', padding: '2rem' }}>
    <h1>Welcome to the Library!</h1>
  </div>
);

// --- D. Main App Component (Updated Routes) ---
function App() {
  return (
    <AuthProvider>
      <Router>
        <Navigation /> {/* Add the navigation bar */}
        <main>
          <Routes>
            {/* --- Public Routes --- */}
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            
            {/* --- Protected Routes --- */}
            <Route element={<ProtectedRoute />}>
              {/* All routes nested inside will be protected */}
              <Route path="/dashboard" element={<Dashboard />} />
              
              {/* --- NEW BOOK ROUTES --- */}
              <Route path="/books" element={<BookList />} />
              <Route path="/books/add" element={<AddBook />} />
              {/* <Route path="/books/edit/:id" element={<EditBook />} /> */}
            
            </Route>
          </Routes>
        </main>
      </Router>
    </AuthProvider>
  );
}

export default App;