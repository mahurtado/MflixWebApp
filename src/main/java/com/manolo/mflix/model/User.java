package com.manolo.mflix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// The @Document annotation marks this class as a domain object that will be persisted to MongoDB.
// The 'collection' attribute specifies the name of the collection in the database.
@Document(collection = "users")
public class User {

    // The @Id annotation marks this field as the primary key.
    // It will be mapped to MongoDB's native '_id' field.
    @Id
    private String id;

    private String name;
    private String email;
    private String password;

    // A no-argument constructor is good practice, though not strictly required by Spring Data MongoDB.
    public User() {
    }

    // A constructor with all fields can be convenient.
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // --- Standard Getters and Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}'; // Note: Password is not included in toString() for security.
    }
}