package com.example.users_data_backend;

import com.example.users_data_backend.model.User;
import com.example.users_data_backend.service.UserService;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional  // Each test runs within a transaction.
public class UserServiceTests {

    @Autowired
    private UserService userService;
    
    @Autowired
    private EntityManager entityManager;
    
    @BeforeEach
    public void setUp() throws Exception {
        // Create and load a sample user
        User user = new User();
        user.setId(1L);
        user.setFirstName("Emily");
        user.setLastName("Johnson");
        user.setSsn("900-590-289");
        user.setEmail("emily.johnson@x.dummyjson.com");
        user.setImage("https://dummyjson.com/icon/emilys/128");
        
        userService.loadUsers(List.of(user));
        
        // Flush and clear so that changes are committed and visible to Hibernate Search
        entityManager.flush();
        entityManager.clear();
        
        // Rebuild the search index manually (if not relying on automatic indexing)
        SearchSession searchSession = Search.session(entityManager);
        searchSession.massIndexer().startAndWait();
    }
    
    @Test
    public void testSearchUsers() {
        List<User> results = userService.searchUsers("Emi");
        Assertions.assertFalse(results.isEmpty(), "Search results should not be empty");
        Assertions.assertEquals("Emily", results.get(0).getFirstName());
    }
    
    @Test
    public void testGetUserById() {
        User user = userService.getUserById(1L);
        Assertions.assertNotNull(user, "User should exist");
        Assertions.assertEquals("Emily", user.getFirstName());
    }
    
    @Test
    public void testGetUserByEmail() {
        User user = userService.getUserByEmail("emily.johnson@x.dummyjson.com");
        Assertions.assertNotNull(user, "User should exist");
        Assertions.assertEquals(1L, user.getId());
    }
    
    @Test
    public void testGetAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        Assertions.assertFalse(allUsers.isEmpty(), "Should return at least one user");
    }
}
