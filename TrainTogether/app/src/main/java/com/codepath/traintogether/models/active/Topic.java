package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:53 PM.
 */
public class Topic {

    private String topicId;
    private String topicName;
    private String topicTaxonomy;

    /**
     *
     * @return
     * The topicId
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     *
     * @param topicId
     * The topicId
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    /**
     *
     * @return
     * The topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     *
     * @param topicName
     * The topicName
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     *
     * @return
     * The topicTaxonomy
     */
    public String getTopicTaxonomy() {
        return topicTaxonomy;
    }

    /**
     *
     * @param topicTaxonomy
     * The topicTaxonomy
     */
    public void setTopicTaxonomy(String topicTaxonomy) {
        this.topicTaxonomy = topicTaxonomy;
    }

}