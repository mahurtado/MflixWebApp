// In src/main/java/com/manolo/mflix/service/CommentService.java
package com.manolo.mflix.service;

import com.manolo.mflix.model.Comment;
import com.manolo.mflix.model.Movie;
import com.manolo.mflix.model.User;
import com.manolo.mflix.repository.CommentRepository;
import com.manolo.mflix.repository.MovieRepository;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);
    private final Lorem lorem = LoremIpsum.getInstance();

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final CacheService cacheService; 

    @Autowired
    public CommentService(CommentRepository commentRepository, MovieRepository movieRepository, CacheService cacheService) {
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.cacheService = cacheService; 
    }

    /**
     * Creates and saves a random comment using users and movies from the in-memory cache.
     */
    public Optional<Comment> createRandomComment() {
        // 1. Get a random user from the cache
        Optional<User> userOptional = cacheService.getRandomUser();
        if (userOptional.isEmpty()) {
            LOGGER.error("Could not create random comment because the user cache is empty.");
            return Optional.empty();
        }
        User randomUser = userOptional.get();

        // 2. Get a random movie ID from the cache
        Optional<String> movieIdOptional = cacheService.getRandomMovieId();
        if (movieIdOptional.isEmpty()) {
            LOGGER.error("Could not create random comment because the movie ID cache is empty.");
            return Optional.empty();
        }
        String randomMovieId = movieIdOptional.get();
        
        // 3. Fetch the full movie object using the ID
        Optional<Movie> movieOptional = movieRepository.findById(randomMovieId);
        if(movieOptional.isEmpty()){
            // This can happen if a movie was deleted after the cache was loaded
            LOGGER.error("Could not find movie with cached ID: {}. It may have been deleted.", randomMovieId);
            return Optional.empty();
        }
        Movie randomMovie = movieOptional.get();

        // 4. Create and save the new comment
        Comment comment = new Comment();
        comment.setName(randomUser.getName());
        comment.setEmail(randomUser.getEmail());
        comment.setMovieId(randomMovie.getId());
        comment.setText(lorem.getWords(15, 25));
        comment.setDate(new Date());

        Comment savedComment = commentRepository.save(comment);
        LOGGER.info("Successfully created a random comment by '{}' on movie '{}' using cache.", randomUser.getName(), randomMovie.getTitle());

        return Optional.of(savedComment);
    }
}