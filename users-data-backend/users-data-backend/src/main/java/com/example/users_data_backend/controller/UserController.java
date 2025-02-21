package com.example.users_data_backend.controller;

import com.example.users_data_backend.model.User;
import com.example.users_data_backend.service.UserService;
import com.example.users_data_backend.util.DataLoader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users API", description = "Endpoints for loading and retrieving users")
@Validated  // Enable validation at the controller level
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private EntityManager entityManager;
    
    // Utility class to load external user data if it does not automatically.
    private final DataLoader dataLoader = new DataLoader();

    @PostMapping("/load")
    @Operation(summary = "Load users from external API")
    public ResponseEntity<String> loadUsers() {
        try {
            List<User> users = dataLoader.fetchUsers();
            userService.loadUsers(users);
            return ResponseEntity.ok("Users loaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error loading users: " + e.getMessage());
        }
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search users by free text (firstName, lastName, ssn)")
    public ResponseEntity<List<User>> searchUsers(
        @RequestParam @NotBlank(message = "Query cannot be blank") 
                      @Size(min = 3, message = "Search query must be at least 3 characters") String query) {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id:[0-9]+}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/find")
    @Operation(summary = "Get user by email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    @Operation(summary = "Retrieve all users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    // Optional endpoint to manually rebuild the search index
    @PostMapping("/reindex")
    @Operation(summary = "Rebuild the Hibernate Search index")
    public ResponseEntity<String> reindex() {
        try {
            SearchSession searchSession = Search.session(entityManager);
            searchSession.massIndexer().startAndWait();
            return ResponseEntity.ok("Index rebuilt successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Indexing failed: " + e.getMessage());
        }
    }
}

