package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:46 PM.
 */
public class AssetTopic {

    private String sequence;
    private Topic topic;

    /**
     *
     * @return
     * The sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     *
     * @param sequence
     * The sequence
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     *
     * @return
     * The topic
     */
    public Topic getTopic() {
        return topic;
    }

    /**
     *
     * @param topic
     * The topic
     */
    public void setTopic(Topic topic) {
        this.topic = topic;
    }

}