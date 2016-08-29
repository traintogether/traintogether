package com.codepath.traintogether.models;

/**
 * Created by alishaalam on 8/28/16.
 */
public class Days {

    String day;
    String date;
    String status;
    String time_mile_status;
    String workout;

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getTime_mile_status() {
        return time_mile_status;
    }

    public String getWorkout() {
        return workout;
    }

    public Days(String day, String date, String status, String time_mile_status, String workout) {
        this.day = day;
        this.date = date;
        this.status = status;
        this.time_mile_status = time_mile_status;
        this.workout = workout;
    }
}
