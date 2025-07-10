package com.manolo.mflix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Date;

/**
 * Represents a comment document from the 'comments' collection in the database.
 */
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String name;

    private String email;

    // We map the 'movie_id' field in the database to this 'movieId' field in our Java class.
    @Field(name = "movie_id", targetType = FieldType.OBJECT_ID)
    private String movieId;

    private String text;

    private Date date;


    // --- Getters and Setters ---

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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}