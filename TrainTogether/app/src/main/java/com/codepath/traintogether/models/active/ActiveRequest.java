package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:56 PM.
 */
public class ActiveRequest {

    String query;
    String category;
    String startDate;
    String near;
    String radius;

    public ActiveRequest() {

    }

    public ActiveRequest(String query, String category, String startDate, String near, String radius) {
        this.query = query;
        this.category = category;
        this.startDate = startDate;
        this.near = near;
        this.radius = radius;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNear() {
        return near;
    }

    public void setNear(String near) {
        this.near = near;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }
}
