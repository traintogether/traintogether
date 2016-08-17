package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:43 PM.
 */
public class AssetDescription {

    private DescriptionType descriptionType;
    private String description;

    /**
     *
     * @return
     * The descriptionType
     */
    public DescriptionType getDescriptionType() {
        return descriptionType;
    }

    /**
     *
     * @param descriptionType
     * The descriptionType
     */
    public void setDescriptionType(DescriptionType descriptionType) {
        this.descriptionType = descriptionType;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}