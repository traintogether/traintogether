package com.codepath.traintogether.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class User {

    public String uid;
    public String emailId;
    public String photoUrl;
    public String displayName;

    public String getUid() {
        return uid;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User() {

    }

    String name;
    String location;
    int rank;
    int miles;
    int runs;
    private String profileImageUrl;
    public List<String> groups = new ArrayList<>();

    public List<String> getGroups() {
        return groups;
    }

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
