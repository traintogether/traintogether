package com.codepath.traintogether.models;

/**
 * Created by ameyapandilwar on 8/26/16.
 */
public class Run {

    public String key;
    public Float distance;

    public Run(String key, Float distance, Double calories) {
        this.key = key;
        this.distance = distance;
        this.calories = calories;
    }

    public Double calories;

    public String getKey() {
        return key;
    }


    public Run() {

    }

}
