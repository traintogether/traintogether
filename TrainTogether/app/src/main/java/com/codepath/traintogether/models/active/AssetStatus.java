package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:45 PM.
 */
public class AssetStatus {

    private String assetStatusId;
    private String assetStatusName;

    /**
     *
     * @return
     * The assetStatusId
     */
    public String getAssetStatusId() {
        return assetStatusId;
    }

    /**
     *
     * @param assetStatusId
     * The assetStatusId
     */
    public void setAssetStatusId(String assetStatusId) {
        this.assetStatusId = assetStatusId;
    }

    /**
     *
     * @return
     * The assetStatusName
     */
    public String getAssetStatusName() {
        return assetStatusName;
    }

    /**
     *
     * @param assetStatusName
     * The assetStatusName
     */
    public void setAssetStatusName(String assetStatusName) {
        this.assetStatusName = assetStatusName;
    }

}