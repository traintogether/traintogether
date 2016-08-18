package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:44 PM.
 */
public class AssetMediaType {

    private String sequence;
    private MediaType mediaType;

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
     * The mediaType
     */
    public MediaType getMediaType() {
        return mediaType;
    }

    /**
     *
     * @param mediaType
     * The mediaType
     */
    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

}