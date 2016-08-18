package com.codepath.traintogether.utils.client;

import com.codepath.traintogether.models.active.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:39 PM.
 */
public class ActiveResponse {

    private Long retries;
    private Long totalResults;
    private Long itemsPerPage;
    private Long startIndex;
    private List<Object> facets = new ArrayList<Object>();
    private List<Object> facetValues = new ArrayList<Object>();
    private List<Object> suggestions = new ArrayList<Object>();
    private List<Result> results = new ArrayList<Result>();
    private Long radius;
    private String startDate;
    private Object sort;

    /**
     *
     * @return
     * The retries
     */
    public Long getRetries() {
        return retries;
    }

    /**
     *
     * @param retries
     * The retries
     */
    public void setRetries(Long retries) {
        this.retries = retries;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public Long getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     * The itemsPerPage
     */
    public Long getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     *
     * @param itemsPerPage
     * The items_per_page
     */
    public void setItemsPerPage(Long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     *
     * @return
     * The startIndex
     */
    public Long getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @param startIndex
     * The start_index
     */
    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return
     * The facets
     */
    public List<Object> getFacets() {
        return facets;
    }

    /**
     *
     * @param facets
     * The facets
     */
    public void setFacets(List<Object> facets) {
        this.facets = facets;
    }

    /**
     *
     * @return
     * The facetValues
     */
    public List<Object> getFacetValues() {
        return facetValues;
    }

    /**
     *
     * @param facetValues
     * The facet_values
     */
    public void setFacetValues(List<Object> facetValues) {
        this.facetValues = facetValues;
    }

    /**
     *
     * @return
     * The suggestions
     */
    public List<Object> getSuggestions() {
        return suggestions;
    }

    /**
     *
     * @param suggestions
     * The suggestions
     */
    public void setSuggestions(List<Object> suggestions) {
        this.suggestions = suggestions;
    }

    /**
     *
     * @return
     * The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The radius
     */
    public Long getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     * The radius
     */
    public void setRadius(Long radius) {
        this.radius = radius;
    }

    /**
     *
     * @return
     * The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     * The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     * The sort
     */
    public Object getSort() {
        return sort;
    }

    /**
     *
     * @param sort
     * The sort
     */
    public void setSort(Object sort) {
        this.sort = sort;
    }

}









