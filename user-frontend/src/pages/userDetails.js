import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const UserDetails = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const apiUrl = process.env.REACT_APP_API_URL;

  useEffect(() => {
    axios.get(`${apiUrl}/${id}`)
      .then(response => setUser(response.data))
      .catch(error => console.error("Error fetching user details:", error));
  }, [id, apiUrl]);

  if (!user) {
    return (
      <div style={{ textAlign: 'center', padding: '20px' }}>
        Loading user details...
      </div>
    );
  }

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h2 style={{ textAlign: 'center' }}>{user.firstName} {user.lastName}</h2>
      <div style={{ textAlign: 'center', marginBottom: '20px' }}>
        <img 
          src={user.image} 
          alt={`${user.firstName} ${user.lastName}`} 
          style={{ maxWidth: '200px', borderRadius: '8px' }}
        />
      </div>
      <p><strong>ID:</strong> {user.id}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>SSN:</strong> {user.ssn}</p>
    </div>
  );
};

export default UserDetails;
