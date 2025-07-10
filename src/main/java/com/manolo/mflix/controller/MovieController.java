package com.manolo.mflix.controller;

import com.manolo.mflix.model.Movie;
import com.manolo.mflix.repository.MovieRepository;
import com.manolo.mflix.service.CacheService;
import com.manolo.mflix.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing movies.
 * Exposes API endpoints to retrieve movie data from the 'movies' collection.
 */
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final CacheService cacheService;
    private final MovieService movieService; 

    @Autowired
    public MovieController(MovieRepository movieRepository, CacheService cacheService, MovieService movieService) {
        this.movieRepository = movieRepository;
        this.cacheService = cacheService;
        this.movieService = movieService;
    }

    /**
     * A flexible search endpoint to find movies by title, genre, and/or year.
     * All parameters are optional.
     * HTTP GET /api/v1/movies/search?title=...&genre=...&year=...
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Movie>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year,
            Pageable pageable) {
        if("All Genres".equals(genre))
                genre = null;
        Page<Movie> movies = movieService.searchMovies(title, genre, year, pageable);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    /**
     * Endpoint to get a random movie by picking a random ID from the cache
     * and then fetching the full document.
     * HTTP GET /api/v1/movies/randomFromCache
     */
    @GetMapping("/randomFromCache")
    public ResponseEntity<Movie> getRandomMovieFromCache() {
        // 1. Get a random ID from the central cache service.
        Optional<String> randomIdOptional = cacheService.getRandomMovieId();

        if (randomIdOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 2. Use the ID to fetch the movie from the repository.
        return movieRepository.findById(randomIdOptional.get())
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to retrieve a single movie by its unique ID.
     * HTTP GET /api/v1/movies/{id}
     *
     * @param id The MongoDB ObjectId of the movie.
     * @return The found movie with a 200 OK status, or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        return movieOptional
                .map(movie -> new ResponseEntity<>(movie, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint to retrieve a list of movies by their exact title.
     * HTTP GET /api/v1/movies/byTitle/{title}
     *
     * @param title The title of the movie(s) to search for.
     * @return A list of movies matching the title with a 200 OK status. Can be an empty list.
     */
    @GetMapping("/byTitle/{title}")
    public ResponseEntity<List<Movie>> getMoviesByTitle(@PathVariable String title) {
        List<Movie> movies = movieRepository.findByTitle(title);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}