package com.codepath.traintogether.models;

import org.parceler.Parcel;

/**
 * Created by santoshag on 8/26/16.
 */

@Parcel
public class Run {

    String key;
    Float distance;
    Double calories;

    public Run() {
    }

    public Run(String key, Float distance, Double calories) {
        this.key = key;
        this.distance = distance;
        this.calories = calories;
    }

    public String getKey() {
        return key;
    }

    public Float getDistance() {
        return distance;
    }

    public Double getCalories() {
        return calories;
    }
}
