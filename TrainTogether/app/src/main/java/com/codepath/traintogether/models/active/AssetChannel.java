package com.codepath.traintogether.models.active;

/**
 * Created by ameyapandilwar on 8/17/16 at 1:42 PM.
 */
public class AssetChannel {

    private String sequence;
    private Channel channel;

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
     * The channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     *
     * @param channel
     * The channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}