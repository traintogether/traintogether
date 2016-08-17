package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:49 PM.
 */
public class MediaType {

    private String mediaTypeName;
    private String mediaTypeDsc;
    private String mediaTypeId;

    /**
     *
     * @return
     * The mediaTypeName
     */
    public String getMediaTypeName() {
        return mediaTypeName;
    }

    /**
     *
     * @param mediaTypeName
     * The mediaTypeName
     */
    public void setMediaTypeName(String mediaTypeName) {
        this.mediaTypeName = mediaTypeName;
    }

    /**
     *
     * @return
     * The mediaTypeDsc
     */
    public String getMediaTypeDsc() {
        return mediaTypeDsc;
    }

    /**
     *
     * @param mediaTypeDsc
     * The mediaTypeDsc
     */
    public void setMediaTypeDsc(String mediaTypeDsc) {
        this.mediaTypeDsc = mediaTypeDsc;
    }

    /**
     *
     * @return
     * The mediaTypeId
     */
    public String getMediaTypeId() {
        return mediaTypeId;
    }

    /**
     *
     * @param mediaTypeId
     * The mediaTypeId
     */
    public void setMediaTypeId(String mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

}