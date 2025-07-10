package com.manolo.mflix.model;

import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

public class Tomatoes {
    private Viewer viewer;
    private Date dvd;
    @Field("lastUpdated") // Map to 'lastUpdated' field in the document
    private Date lastUpdated;

    // Getters and Setters
    public Viewer getViewer() { return viewer; }
    public void setViewer(Viewer viewer) { this.viewer = viewer; }
    public Date getDvd() { return dvd; }
    public void setDvd(Date dvd) { this.dvd = dvd; }
    public Date getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
}