package com.codepath.traintogether.models.active;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:40 PM.
 */
public class ActivityRecurrence {

    private String frequencyInterval;
    private String activityStartDate;
    private List<Object> activityExclusions = new ArrayList<Object>();
    private String activityEndDate;
    private String days;
    private String startTime;
    private String endTime;
    private Frequency frequency;

    /**
     *
     * @return
     * The frequencyInterval
     */
    public String getFrequencyInterval() {
        return frequencyInterval;
    }

    /**
     *
     * @param frequencyInterval
     * The frequencyInterval
     */
    public void setFrequencyInterval(String frequencyInterval) {
        this.frequencyInterval = frequencyInterval;
    }

    /**
     *
     * @return
     * The activityStartDate
     */
    public String getActivityStartDate() {
        return activityStartDate;
    }

    /**
     *
     * @param activityStartDate
     * The activityStartDate
     */
    public void setActivityStartDate(String activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    /**
     *
     * @return
     * The activityExclusions
     */
    public List<Object> getActivityExclusions() {
        return activityExclusions;
    }

    /**
     *
     * @param activityExclusions
     * The activityExclusions
     */
    public void setActivityExclusions(List<Object> activityExclusions) {
        this.activityExclusions = activityExclusions;
    }

    /**
     *
     * @return
     * The activityEndDate
     */
    public String getActivityEndDate() {
        return activityEndDate;
    }

    /**
     *
     * @param activityEndDate
     * The activityEndDate
     */
    public void setActivityEndDate(String activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    /**
     *
     * @return
     * The days
     */
    public String getDays() {
        return days;
    }

    /**
     *
     * @param days
     * The days
     */
    public void setDays(String days) {
        this.days = days;
    }

    /**
     *
     * @return
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     * The endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return
     * The frequency
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     *
     * @param frequency
     * The frequency
     */
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

}