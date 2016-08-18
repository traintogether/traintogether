package com.codepath.traintogether.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ameyapandilwar on 8/17/16 at 2:07 PM.
 */
public class Utils {

    private static Gson gson;

    public static Gson getGsonInstance() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder.create();
        }
        return gson;
    }
}