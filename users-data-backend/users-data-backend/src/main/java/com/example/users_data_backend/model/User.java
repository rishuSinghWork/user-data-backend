package com.example.users_data_backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Entity
@Indexed
@Table(name = "users")
public class User {

    @Id
    private Long id;
    
    @FullTextField
    private String firstName;
    
    @FullTextField
    private String lastName;
    
    @FullTextField
    private String ssn;
    
    private String email;
    private String image;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
