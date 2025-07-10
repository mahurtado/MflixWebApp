package com.manolo.mflix.controller;

import com.manolo.mflix.model.User;
import com.manolo.mflix.repository.UserRepository;
import com.manolo.mflix.service.CacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final CacheService cacheService;


    @Autowired
    public UserController(UserRepository userRepository, CacheService cacheService) {
        this.userRepository = userRepository;
        this.cacheService = cacheService;
    }

    /**
     * Endpoint to get a random user from the pre-loaded static cache.
     * HTTP GET /api/v1/users/random
     *
     * @return A random user with a 200 OK status, or 404 Not Found if the cache is empty.
     */
    @GetMapping("/random")
    public ResponseEntity<User> getRandomUser() {
        // The logic is now cleaner, just a single call to the service.
        return cacheService.getRandomUser()
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // --- All of your previous controller methods (createUser, getAllUsers, etc.) remain here ---

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // NOTE: This new user will NOT be added to the running application's cache.
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/byEmail/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}