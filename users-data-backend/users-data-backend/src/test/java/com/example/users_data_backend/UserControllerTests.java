package com.example.users_data_backend;

import com.example.users_data_backend.model.User;
import com.example.users_data_backend.service.UserService;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @BeforeEach
    public void setUp() throws Exception {
        // Use TransactionTemplate to load data within a transaction.
        TransactionTemplate txTemplate = new TransactionTemplate(transactionManager);
        txTemplate.execute(status -> {
            User user = new User();
            user.setId(1L);
            user.setFirstName("Emily");
            user.setLastName("Johnson");
            user.setSsn("900-590-289");
            user.setEmail("emily.johnson@x.dummyjson.com");
            user.setImage("https://dummyjson.com/icon/emilys/128");
            userService.loadUsers(List.of(user));
            return null;
        });
        
        // Now run indexing in a separate transaction.
        TransactionTemplate txTemplate2 = new TransactionTemplate(transactionManager);
        txTemplate2.execute(status -> {
            entityManager.flush();
            entityManager.clear();
            SearchSession searchSession = Search.session(entityManager);
            try {
                searchSession.massIndexer().startAndWait();
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }
    
    @Test
    public void testSearchEndpoint() throws Exception {
        mockMvc.perform(get("/api/users/search")
                    .param("query", "Emi")
                    .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].firstName").value("Emily"));
    }
    
    @Test
    public void testGetUserByIdEndpoint() throws Exception {
        mockMvc.perform(get("/api/users/1")
                    .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName").value("Emily"));
    }
    
    @Test
    public void testGetAllUsersEndpoint() throws Exception {
        mockMvc.perform(get("/api/users")
                    .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }
}
