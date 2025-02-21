package com.example.users_data_backend.util;


import com.example.users_data_backend.model.User;
import com.example.users_data_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserService userService;
    private final DataLoader dataLoader;

    public DataInitializer(UserService userService) {
        this.userService = userService;
        this.dataLoader = new DataLoader();
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("Loading users from external API at startup...");
            List<User> users = dataLoader.fetchUsers();
            userService.loadUsers(users);
            logger.info("Successfully loaded {} users.", users.size());
        } catch (Exception e) {
            logger.error("Error loading users on startup: {}", e.getMessage(), e);
        }
    }
}
