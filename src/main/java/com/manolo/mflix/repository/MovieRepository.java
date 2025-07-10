package com.manolo.mflix.repository;

import com.manolo.mflix.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for the Movie entity.
 * Extending MongoRepository provides a full set of CRUD operations for the movies collection.
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {

    /**
     * A custom derived query method to find movies by their exact title.
     * Spring Data will automatically generate the implementation for this method.
     *
     * @param title The title of the movie to search for.
     * @return A list of movies that match the given title.
     */
    List<Movie> findByTitle(String title);

    /**
     * An efficient query to fetch only the '_id' field of all documents in the collection.
     * value='{}' is an empty query, meaning it matches all documents.
     * fields='{ "_id" : 1 }' is a projection that includes only the _id field.
     * @return A list of Movie objects where only the 'id' field is populated.
     */
    @Query(value = "{}", fields = "{ \"_id\" : 1 }")
    List<Movie> findAllIds();

}