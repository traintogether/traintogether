package com.codepath.traintogether.utils.client;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:56 PM.
 */
public class ActiveRequest {

    private String query;
    private String latLon;
    private String radius;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String startDate;

    public ActiveRequest() {

    }

    public ActiveRequest(String query, String latLon, String radius, String city, String state, String zip, String country, String startDate) {
        this.query = query;
        this.latLon = latLon;
        this.radius = radius;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.startDate = startDate;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
