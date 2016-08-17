package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:53 PM.
 */
public class SourceSystem {

    private String sourceSystemName;
    private String legacyGuid;
    private String affiliate;

    /**
     *
     * @return
     * The sourceSystemName
     */
    public String getSourceSystemName() {
        return sourceSystemName;
    }

    /**
     *
     * @param sourceSystemName
     * The sourceSystemName
     */
    public void setSourceSystemName(String sourceSystemName) {
        this.sourceSystemName = sourceSystemName;
    }

    /**
     *
     * @return
     * The legacyGuid
     */
    public String getLegacyGuid() {
        return legacyGuid;
    }

    /**
     *
     * @param legacyGuid
     * The legacyGuid
     */
    public void setLegacyGuid(String legacyGuid) {
        this.legacyGuid = legacyGuid;
    }

    /**
     *
     * @return
     * The affiliate
     */
    public String getAffiliate() {
        return affiliate;
    }

    /**
     *
     * @param affiliate
     * The affiliate
     */
    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

}