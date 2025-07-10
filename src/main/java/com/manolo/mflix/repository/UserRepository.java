package com.manolo.mflix.repository;

import com.manolo.mflix.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository is an interface that extends MongoRepository.
 * Spring Data MongoDB will automatically create a proxy implementation for this interface.
 * It provides all the necessary CRUD (Create, Read, Update, Delete) operations for the User entity.
 *
 * @param <User> The domain type the repository manages.
 * @param <String> The type of the id of the domain type.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Spring Data MongoDB will automatically implement this method based on its name.
    // It will find a user by their email address.
    // We use Optional<User> to handle cases where no user is found for the given email.
    Optional<User> findByEmail(String email);

}