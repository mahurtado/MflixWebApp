// In src/main/java/com/manolo/mflix/service/CacheService.java
package com.manolo.mflix.service;

import com.manolo.mflix.model.Movie;
import com.manolo.mflix.model.User;
import com.manolo.mflix.repository.MovieRepository;
import com.manolo.mflix.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
    private static final Random RANDOM = new Random();

    private static final List<User> USER_CACHE = new ArrayList<>();
    private static final List<String> MOVIE_ID_CACHE = new ArrayList<>();

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public CacheService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * A single method, run at startup, to load all our caches.
     */
    @PostConstruct
    public void loadCaches() {
        // Load User Cache
        LOGGER.info("Loading all users into the static cache...");
        List<User> allUsers = userRepository.findAll();
        if (!allUsers.isEmpty()) {
            USER_CACHE.addAll(allUsers);
            LOGGER.info("Successfully loaded {} users into the cache.", USER_CACHE.size());
        } else {
            LOGGER.warn("No users found in the database to load into the cache.");
        }

        // Load Movie ID Cache
        LOGGER.info("Starting to load movie IDs into static cache...");
        List<String> movieIds = movieRepository.findAllIds()
                .stream()
                .map(Movie::getId)
                .collect(Collectors.toList());
        if (!movieIds.isEmpty()) {
            MOVIE_ID_CACHE.addAll(movieIds);
            LOGGER.info("Finished loading {} movie IDs into cache.", MOVIE_ID_CACHE.size());
        } else {
            LOGGER.warn("No movie IDs found in database to cache.");
        }
    }

    /**
     * Safely gets a random user from the cache.
     * @return An Optional containing a User, or an empty Optional if the cache is empty.
     */
    public Optional<User> getRandomUser() {
        if (USER_CACHE.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(USER_CACHE.get(RANDOM.nextInt(USER_CACHE.size())));
    }

    /**
     * Safely gets a random movie ID from the cache.
     * @return An Optional containing a movie ID string, or an empty Optional if the cache is empty.
     */
    public Optional<String> getRandomMovieId() {
        if (MOVIE_ID_CACHE.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(MOVIE_ID_CACHE.get(RANDOM.nextInt(MOVIE_ID_CACHE.size())));
    }
}