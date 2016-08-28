package com.codepath.traintogether.models;

/**
 * Created by ameyapandilwar on 8/26/16.
 */
public class Request {

    public String key;
    public String fromId;
    public String toId;
    public String groupKey;
    public boolean status;

    public String getKey() {
        return key;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Request() {

    }

    public Request(String toId, String fromId, String groupKey) {
        this.toId = toId;
        this.fromId = fromId;
        this.groupKey = groupKey;
        this.status = false;
    }
}
