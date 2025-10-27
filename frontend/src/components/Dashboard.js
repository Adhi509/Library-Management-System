import React from 'react';
import { useAuth } from '../context/AuthContext';

const Dashboard = () => {
  const { user } = useAuth(); // Get user details from the context

  return (
    <div style={{ padding: '2rem' }}>
      <h2>Dashboard</h2>
      <p>Welcome to your dashboard!</p>
      {user && (
        <div>
          <h3>Your Details:</h3>
          <p>Email: {user.sub}</p> {/* 'sub' is the standard claim for subject (username) in JWT */}
          {/* You can add more user details if you include them in your JWT payload */}
        </div>
      )}
    </div>
  );
};

export default Dashboard;