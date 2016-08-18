package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:45 PM.
 */
public class AssetPrice {

    private String effectiveFromVolume;
    private double priceAmt;
    private Object maxPriceAmt;
    private Object effectiveUntilDate;
    private Object minPriceAmt;
    private String effectiveToVolume;
    private String dynamicPricing;
    private String volumePricing;

    /**
     *
     * @return
     * The effectiveFromVolume
     */
    public String getEffectiveFromVolume() {
        return effectiveFromVolume;
    }

    /**
     *
     * @param effectiveFromVolume
     * The effectiveFromVolume
     */
    public void setEffectiveFromVolume(String effectiveFromVolume) {
        this.effectiveFromVolume = effectiveFromVolume;
    }

    /**
     *
     * @return
     * The priceAmt
     */
    public double getPriceAmt() {
        return priceAmt;
    }

    /**
     *
     * @param priceAmt
     * The priceAmt
     */
    public void setPriceAmt(double priceAmt) {
        this.priceAmt = priceAmt;
    }

    /**
     *
     * @return
     * The maxPriceAmt
     */
    public Object getMaxPriceAmt() {
        return maxPriceAmt;
    }

    /**
     *
     * @param maxPriceAmt
     * The maxPriceAmt
     */
    public void setMaxPriceAmt(Object maxPriceAmt) {
        this.maxPriceAmt = maxPriceAmt;
    }

    /**
     *
     * @return
     * The effectiveUntilDate
     */
    public Object getEffectiveUntilDate() {
        return effectiveUntilDate;
    }

    /**
     *
     * @param effectiveUntilDate
     * The effectiveUntilDate
     */
    public void setEffectiveUntilDate(Object effectiveUntilDate) {
        this.effectiveUntilDate = effectiveUntilDate;
    }

    /**
     *
     * @return
     * The minPriceAmt
     */
    public Object getMinPriceAmt() {
        return minPriceAmt;
    }

    /**
     *
     * @param minPriceAmt
     * The minPriceAmt
     */
    public void setMinPriceAmt(Object minPriceAmt) {
        this.minPriceAmt = minPriceAmt;
    }

    /**
     *
     * @return
     * The effectiveToVolume
     */
    public String getEffectiveToVolume() {
        return effectiveToVolume;
    }

    /**
     *
     * @param effectiveToVolume
     * The effectiveToVolume
     */
    public void setEffectiveToVolume(String effectiveToVolume) {
        this.effectiveToVolume = effectiveToVolume;
    }

    /**
     *
     * @return
     * The dynamicPricing
     */
    public String getDynamicPricing() {
        return dynamicPricing;
    }

    /**
     *
     * @param dynamicPricing
     * The dynamicPricing
     */
    public void setDynamicPricing(String dynamicPricing) {
        this.dynamicPricing = dynamicPricing;
    }

    /**
     *
     * @return
     * The volumePricing
     */
    public String getVolumePricing() {
        return volumePricing;
    }

    /**
     *
     * @param volumePricing
     * The volumePricing
     */
    public void setVolumePricing(String volumePricing) {
        this.volumePricing = volumePricing;
    }

}