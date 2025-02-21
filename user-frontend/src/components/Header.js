import React from 'react';
import SearchBar from './SearchBar';
import { useNavigate } from 'react-router-dom';

const Header = () => {
  const navigate = useNavigate();

  const handleUserSelect = (user) => {
    navigate(`/user/${user.id}`);
  };

  return (
    <header style={{
      padding: '20px',
      textAlign: 'center',
      backgroundColor: '#f7f7f7',
      marginBottom: '20px'
    }}>
      <h1>Users Directory</h1>
      <SearchBar onSelectUser={handleUserSelect} />
    </header>
  );
};

export default Header;
