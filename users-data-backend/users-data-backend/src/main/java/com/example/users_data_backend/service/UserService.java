package com.example.users_data_backend.service;

import com.example.users_data_backend.exception.ResourceNotFoundException;
import com.example.users_data_backend.model.User;
import com.example.users_data_backend.repository.UserRepository;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Transactional
    public void loadUsers(List<User> users) {
        logger.info("Loading {} users into the database", users.size());
        userRepository.saveAll(users);
        logger.info("Users loaded successfully");
    }
    
    @Transactional(readOnly = true)
    public List<User> searchUsers(String query) {
        logger.info("Searching for users with query: {}", query);
        if (query == null || query.length() < 3) {
            throw new IllegalArgumentException("Search query must be at least 3 characters");
        }
        SearchSession searchSession = Search.session(entityManager);
        List<User> hits = searchSession.search(User.class)
                .where(f -> f.wildcard()
                    .fields("firstName", "lastName", "ssn")
                    .matching(query.toLowerCase() + "*"))
                .fetchHits(20);
        logger.info("Found {} users matching query '{}'", hits.size(), query);
        return hits;
    }
    
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        logger.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
    
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email " + email);
        }
        return user;
    }
    
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }
}


