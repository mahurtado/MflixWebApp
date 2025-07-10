package com.manolo.mflix.service;

import com.manolo.mflix.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MovieService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Searches for movies based on a combination of title, genre, and year.
     * All parameters are optional.
     *
     * @param title    Part of the movie title (case-insensitive).
     * @param genre    A genre the movie must be associated with.
     * @param year     The year the movie was released.
     * @param pageable Pagination information.
     * @return A paginated list of movies matching the criteria.
     */
    public Page<Movie> searchMovies(String title, String genre, Integer year, Pageable pageable) {
        // We build our query dynamically based on the provided parameters.
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            // Case-insensitive regex match on the title
            criteria.add(Criteria.where("title").regex(title, "i"));
        }

        if (genre != null && !genre.isBlank()) {
            // The 'genres' field in the document must contain the provided genre
            criteria.add(Criteria.where("genres").in(genre));
        }

        if (year != null) {
            // Exact match on the year
            criteria.add(Criteria.where("year").is(year));
        }

        if (!criteria.isEmpty()) {
            // Add the criteria to the query, combining them with an "AND"
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        // We need to execute the count query separately for pagination to work correctly
        long count = mongoTemplate.count(query, Movie.class);
        List<Movie> movies = mongoTemplate.find(query, Movie.class);

        return new PageImpl<>(movies, pageable, count);
    }
}