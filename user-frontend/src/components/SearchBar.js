import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './SearchBar.css';

const SearchBar = ({ onSelectUser }) => {
  const [query, setQuery] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const apiUrl = process.env.REACT_APP_API_URL; 

  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      if (query.length >= 3) {
        axios.get(`${apiUrl}/search`, { params: { query } })
          .then(response => {
            setSuggestions(response.data);
          })
          .catch(error => {
            console.error("Error fetching search results:", error);
            setSuggestions([]);
          });
      } else {
        setSuggestions([]);
      }
    }, 300);

    return () => clearTimeout(delayDebounceFn);
  }, [query, apiUrl]);

  return (
    <div className="search-bar">
      <input 
        type="text"
        placeholder="Search by firstName, lastName or SSN..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />
      {suggestions.length > 0 && (
        <ul className="suggestions">
          {suggestions.map(user => (
            <li key={user.id} onClick={() => {
              setQuery('');
              setSuggestions([]);
              onSelectUser(user);
            }}>
              {user.id} &mdash; {user.firstName} {user.lastName} &mdash; {user.ssn} &mdash; {user.email}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SearchBar;
