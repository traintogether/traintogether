package com.codepath.traintogether.models;

/**
 * Created by ameyapandilwar on 8/18/16 at 3:07 AM.
 */
public class FilterSettings {
    private String latLon;
    private String radius;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String startDate;

    public FilterSettings(String latLon, String radius, String city, String state, String zip, String country, String startDate) {
        this.latLon = latLon;
        this.radius = radius;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.startDate = startDate;
    }

    public String getLatLon() {
        return latLon;
    }

    // lat_lon=43.2,-118
    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getRadius() {
        return radius;
    }

    // radius=50
    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCity() {
        return city;
    }

    // city=san+diego
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    // state=CA
    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    // zip=92101
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    // country=United%20States
    public void setCountry(String country) {
        this.country = country;
    }

    public String getStartDate() {
        return startDate;
    }

    // start_date=2016-08-20
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
