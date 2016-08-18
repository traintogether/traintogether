package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:42 PM.
 */
public class AssetCategory {

    private String sequence;
    private Category category;

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
     * The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     *
     * @param category
     * The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

}