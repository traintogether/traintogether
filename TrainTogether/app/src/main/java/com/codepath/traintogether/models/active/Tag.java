package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:53 PM.
 */
public class Tag {

    private String tagId;
    private String tagName;
    private String tagDescription;

    /**
     *
     * @return
     * The tagId
     */
    public String getTagId() {
        return tagId;
    }

    /**
     *
     * @param tagId
     * The tagId
     */
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    /**
     *
     * @return
     * The tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     *
     * @param tagName
     * The tagName
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     *
     * @return
     * The tagDescription
     */
    public String getTagDescription() {
        return tagDescription;
    }

    /**
     *
     * @param tagDescription
     * The tagDescription
     */
    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

}