package com.manolo.mflix.controller;

import com.manolo.mflix.model.Comment;
import com.manolo.mflix.repository.CommentRepository;
import com.manolo.mflix.repository.MovieRepository;
import com.manolo.mflix.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentRepository commentRepository, MovieRepository movieRepository, CommentService commentService) {
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.commentService = commentService;
    }

    /**
     * Endpoint to generate and save a completely random comment.
     * This method cleanly delegates the entire complex operation to the CommentService.
     * HTTP POST /api/v1/comments/random
     */
    @PostMapping("/random")
    public ResponseEntity<Comment> postRandomComment() {
        return commentService.createRandomComment()
                .map(comment -> new ResponseEntity<>(comment, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Endpoint to create a specific comment from a request body.
     * This method handles its own logic, including validation.
     * HTTP POST /api/v1/comments
     */
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        // Validation: Check if the movie ID is provided and exists
        if (comment.getMovieId() == null || !movieRepository.existsById(comment.getMovieId())) {
            return new ResponseEntity<>("Movie not found with ID: " + comment.getMovieId(), HttpStatus.BAD_REQUEST);
        }

        comment.setDate(new Date());
        Comment savedComment = commentRepository.save(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all comments for a specific movie, with pagination.
     * HTTP GET /api/v1/comments/byMovie/{movieId}
     */
    @GetMapping("/byMovie/{movieId}")
    public ResponseEntity<Page<Comment>> getCommentsByMovieId(@PathVariable String movieId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByMovieId(movieId, pageable);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a single comment by its unique ID.
     * HTTP GET /api/v1/comments/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable String id) {
        return commentRepository.findById(id)
                .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}