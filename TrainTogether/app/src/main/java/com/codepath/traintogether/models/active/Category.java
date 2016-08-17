package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:46 PM.
 */
public class Category {

    private String categoryName;
    private String categoryId;
    private String categoryTaxonomy;

    /**
     *
     * @return
     * The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     *
     * @param categoryName
     * The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The categoryTaxonomy
     */
    public String getCategoryTaxonomy() {
        return categoryTaxonomy;
    }

    /**
     *
     * @param categoryTaxonomy
     * The categoryTaxonomy
     */
    public void setCategoryTaxonomy(String categoryTaxonomy) {
        this.categoryTaxonomy = categoryTaxonomy;
    }

}