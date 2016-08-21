package com.codepath.traintogether.models;

/**
 * Created by alishaalam on 8/18/16.
 */
public class User {

    String name;
    String location;
    int rank;
    int miles;
    int runs;
    private String profileImageUrl;

    public User(String name, String location, int rank, int miles, int runs) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.miles = miles;
        this.runs = runs;
    }

    public User(String name, String location, int rank, int miles, int runs, String profileImageUrl) {
        this.name = name;
        this.location = location;
        this.rank = rank;
        this.miles = miles;
        this.runs = runs;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public String getProfileImageUrl() {

        //return profileImageUrl;
        return "http://www.paradigmresource.com/wp-content/uploads/2014/12/download.jpg";
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", rank=" + rank +
                ", miles=" + miles +
                ", runs=" + runs +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                '}';
    }
}
