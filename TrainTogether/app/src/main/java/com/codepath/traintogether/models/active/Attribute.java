package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:46 PM.
 */
public class Attribute {

    private String tagId;
    private String attributeValue;
    private String attributeType;

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
     * The attributeValue
     */
    public String getAttributeValue() {
        return attributeValue;
    }

    /**
     *
     * @param attributeValue
     * The attributeValue
     */
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    /**
     *
     * @return
     * The attributeType
     */
    public String getAttributeType() {
        return attributeType;
    }

    /**
     *
     * @param attributeType
     * The attributeType
     */
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

}