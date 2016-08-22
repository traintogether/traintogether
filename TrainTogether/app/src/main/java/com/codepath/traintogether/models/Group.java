package com.codepath.traintogether.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by santoshag on 8/5/16.
 */
@IgnoreExtraProperties
public class Group {


    public String key;
    public String eventId;
    public List<User> users;

    public String getKey() {
        return key;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("eventId", eventId);
        HashMap<String, Object> userMap = new HashMap<>();
        for(User user:users){
            userMap.put("id", user.getUid());
            userMap.put("emailId", user.getEmailId());
        }
        result.put("users", userMap);
        return result;
    }
}
