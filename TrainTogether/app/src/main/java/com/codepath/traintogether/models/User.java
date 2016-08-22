package com.codepath.traintogether.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by santoshag on 8/5/16.
 */
@IgnoreExtraProperties
public class User{

    public String uid;
    public String emailId;
    public String photoUrl;
    public String displayName;

    public String getUid() {
        return uid;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void User(){

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("emailId", emailId);
        result.put("photoUrl", photoUrl);
        result.put("displayName", displayName);

        return result;
    }
}
