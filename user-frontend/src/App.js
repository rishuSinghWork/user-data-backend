import React, { Suspense, lazy } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './pages/Home';

// Lazy-load the UserDetails page
const UserDetails = lazy(() => import('./pages/userDetails'));

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route 
          path="/user/:id" 
          element={
            <Suspense fallback={<div style={{ textAlign: 'center', padding: '20px' }}>Loading user details...</div>}>
              <UserDetails />
            </Suspense>
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
