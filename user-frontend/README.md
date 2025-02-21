# Users Frontend

## Overview

This ReactJS single-page application is part of a user directory system that interfaces with a backend API. The app allows users to:

- **Typeahead/Auto-Complete Search:**  
  Search for users by first name, last name, or SSN. When at least 3 characters are entered in the search bar (located in the header), the backend API is called to fetch matching users. The results display key attributes (id, firstName, lastName, ssn, and email) in a dropdown.

- **Direct Search:**  
  On the Home page, users can directly search for a user by ID or email. Upon clicking the respective search button, the app calls the appropriate backend API endpoint and navigates to the details page if a match is found.

- **User Details:**  
  The user details page displays additional information about the user, including their image. This page is lazy-loaded to enhance performance.

## Features

- **Typeahead/Auto-Complete:**  
  Dynamically fetch and display search suggestions as the user types.

- **Direct Search:**  
  Search by user ID or email with dedicated input fields.

- **Navigation:**  
  Clicking on a search result navigates to a detailed view of the user.

- **Lazy Loading:**  
  The user details page is loaded lazily using React’s `Suspense` and `React.lazy`.

- **Responsive Design:**  
  Basic responsive styling ensures a good user experience on various devices.

- **Environment Configuration:**  
  API endpoints are externalized via a `.env` file, making it easy to switch environments.


## How to Run

Follow these steps to get the project up and running:

1. **Clone the Repository:**

   Open your terminal and run:
   
   git clone <repository-url>
   cd users-frontend

2. **Install Dependencies:**
    npm install 

3. **Configure Environment variables:**
    Create a .env file in root directory and add this,
    REACT_APP_API_URL=http://localhost:8080/api/users

4. **Start the Development Server:**
    Launch the app 
    npm start