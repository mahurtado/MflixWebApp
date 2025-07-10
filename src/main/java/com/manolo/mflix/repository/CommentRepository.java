package com.manolo.mflix.repository;

import com.manolo.mflix.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for the Comment entity.
 * It extends MongoRepository to provide standard CRUD functionality
 * and allows for custom derived query methods.
 */
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    /**
     * A custom derived query to find all comments associated with a specific movie ID.
     * This method supports pagination and sorting.
     *
     * @param movieId The ID of the movie for which to fetch comments.
     * @param pageable The pagination and sorting information.
     * @return A paginated list of comments for the given movie ID.
     */
    Page<Comment> findByMovieId(String movieId, Pageable pageable);

}