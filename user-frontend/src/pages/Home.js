import React, { useState } from 'react';
import Header from '../components/Header';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const Home = () => {
  const apiUrl = process.env.REACT_APP_API_URL; 
  const [idSearch, setIdSearch] = useState('');
  const [emailSearch, setEmailSearch] = useState('');
  const [directError, setDirectError] = useState('');

  const navigate = useNavigate();

  const handleSearchById = () => {
    setDirectError('');
    if (!idSearch) return;
    axios.get(`${apiUrl}/${idSearch}`)
      .then(response => {
        navigate(`/user/${response.data.id}`);
      })
      .catch(error => {
        console.error("Error fetching user by ID:", error);
        setDirectError(`User not found with ID: ${idSearch}`);
      });
  };

  const handleSearchByEmail = () => {
    setDirectError('');
    if (!emailSearch) return;
    axios.get(`${apiUrl}/find`, { params: { email: emailSearch } })
      .then(response => {
        navigate(`/user/${response.data.id}`);
      })
      .catch(error => {
        console.error("Error fetching user by email:", error);
        setDirectError(`User not found with email: ${emailSearch}`);
      });
  };

  return (
    <div>
      <Header />
      <main style={{ padding: '20px', textAlign: 'center' }}>
        <h2>Welcome to the Users Directory</h2>
        <p>Start typing in the search bar above to find a user by name or SSN.</p>
        <hr style={{ margin: '20px 0' }} />
        <h3>Direct Search by ID or Email</h3>
        {directError && <p style={{ color: 'red' }}>{directError}</p>}
        <div style={{ margin: '20px auto', maxWidth: '400px' }}>
          <input 
            type="text"
            placeholder="Search by ID"
            value={idSearch}
            onChange={(e) => setIdSearch(e.target.value)}
            style={{ marginBottom: '10px', width: '100%', padding: '8px' }}
          />
          <button 
            onClick={handleSearchById} 
            style={{ width: '100%', padding: '8px', marginBottom: '10px' }}>
            Find by ID
          </button>
          <input 
            type="email"
            placeholder="Search by Email"
            value={emailSearch}
            onChange={(e) => setEmailSearch(e.target.value)}
            style={{ marginBottom: '10px', width: '100%', padding: '8px' }}
          />
          <button 
            onClick={handleSearchByEmail} 
            style={{ width: '100%', padding: '8px' }}>
            Find by Email
          </button>
        </div>
      </main>
    </div>
  );
};

export default Home;
