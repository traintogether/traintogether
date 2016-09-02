package com.codepath.traintogether.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameyapandilwar on 8/26/16.
 */

public class GroupStat {


    String key;
    Float distance = new Float(0.0);
    int totalRuns = 0;

    public Float getDistance() {
        return distance;
    }

    public int getTotalRuns() {
        return totalRuns;
    }

    public Double getCalories() {
        return calories;
    }

    List<String> runKeys = new ArrayList<>();


    public GroupStat(Float distance, Double calories) {
        this.distance = distance;
        this.calories = calories;
        this.runKeys = new ArrayList<>();
    }

    public Double calories = 0.0;

    public String getKey() {
        return key;
    }

    public List<String> getRunKeys() {
        return runKeys;
    }


    public Boolean containsRun(String key) {
        return runKeys.contains(key);
    }

    public void addRun(Run run) {
        distance += run.getDistance();
        calories += run.getCalories();
        totalRuns += 1;
        runKeys.add(run.getKey());
    }

    public GroupStat() {


    }

}
