# Users Data Backend (Spring Boot)

## Overview

This Spring Boot application is the backend for the Users Directory system. It automatically loads user data from an external API into an in-memory H2 database when the application starts. The backend provides REST API endpoints to:

- **Search Users by Free Text:**  
  Allows searching by first name, last name, or SSN using Hibernate Search with wildcard matching.

- **Retrieve User by ID:**  
  Provides an endpoint to get a user’s details by their ID.

- **Retrieve User by Email:**  
  Provides an endpoint to get a user’s details by their email.

Additional features include clean modular code, logging, exception handling, environment-specific configuration, and Swagger/OpenAPI documentation.




## How to Run

Follow these steps to get the backend up and running:

1. **Clone the Repository:**

   Open your terminal and run:
   git clone <repository-url>
   cd users-backend-springboot

2. **Build the Project:**
	mvn clean install
	
3. **Run the Application:**
	mvn spring-boot:run
	or 
	java -jar target/users-data-backend-springboot-1.0.0.jar
	
4. **Verify the endpoints:**  
	*Search user by free text :* GET http://localhost:8080/api/users/search?query=emi  
	*Search user by ID :* GET http://localhost:8080/api/users/{id}  
	*Search user by Email :* GET http://localhost:8080/api/users/find?email=emily.johnson@x.dummyjson.com
	