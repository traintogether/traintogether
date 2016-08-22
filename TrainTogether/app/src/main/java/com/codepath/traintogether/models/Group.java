package com.codepath.traintogether.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by santoshag on 8/5/16.
 */
public class Group {


    public String gid;
    public String eventId;
    public List<User> users;

    public String getGid() {
        return gid;
    }

    public String getEventId() {
        return eventId;
    }

    public List<User> getUsers() {
        return users;
    }

    public Group(){
        users = new ArrayList<>();

    }


}
