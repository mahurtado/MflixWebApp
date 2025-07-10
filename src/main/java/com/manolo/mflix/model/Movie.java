package com.manolo.mflix.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    private String plot;
    private List<String> genres;
    private int runtime;
    private List<String> cast;

    @Field("num_mflix_comments") // Maps this Java field to the 'num_mflix_comments' field in MongoDB
    private int numMflixComments;

    private String poster;
    private String title;
    private String fullplot;
    private List<String> countries;
    private Date released;
    private List<String> directors;
    private List<String> writers;
    private Awards awards;
    private String lastupdated;
    private int year;
    private Imdb imdb;
    private String type;
    private Tomatoes tomatoes;

    // --- Getters and Setters for all fields ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPlot() { return plot; }
    public void setPlot(String plot) { this.plot = plot; }
    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }
    public int getRuntime() { return runtime; }
    public void setRuntime(int runtime) { this.runtime = runtime; }
    public List<String> getCast() { return cast; }
    public void setCast(List<String> cast) { this.cast = cast; }
    public int getNumMflixComments() { return numMflixComments; }
    public void setNumMflixComments(int numMflixComments) { this.numMflixComments = numMflixComments; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getFullplot() { return fullplot; }
    public void setFullplot(String fullplot) { this.fullplot = fullplot; }
    public List<String> getCountries() { return countries; }
    public void setCountries(List<String> countries) { this.countries = countries; }
    public Date getReleased() { return released; }
    public void setReleased(Date released) { this.released = released; }
    public List<String> getDirectors() { return directors; }
    public void setDirectors(List<String> directors) { this.directors = directors; }
    public List<String> getWriters() { return writers; }
    public void setWriters(List<String> writers) { this.writers = writers; }
    public Awards getAwards() { return awards; }
    public void setAwards(Awards awards) { this.awards = awards; }
    public String getLastupdated() { return lastupdated; }
    public void setLastupdated(String lastupdated) { this.lastupdated = lastupdated; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public Imdb getImdb() { return imdb; }
    public void setImdb(Imdb imdb) { this.imdb = imdb; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Tomatoes getTomatoes() { return tomatoes; }
    public void setTomatoes(Tomatoes tomatoes) { this.tomatoes = tomatoes; }
}