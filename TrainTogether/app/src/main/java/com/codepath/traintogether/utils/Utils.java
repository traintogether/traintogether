package com.codepath.traintogether.utils;

import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

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

    public static final HashMap<Double, String> paceColors =
            new HashMap<Double, String>() {{
                put(0.0, "#00ff80");
                put(1.0, "#00ff80");
                put(2.0, "#00ff40");
                put(3.0, "#00ff00");

                put(4.0, "#40ff00");
                put(5.0, "#80ff00");
                put(6.0, "#bfff00");

                put(7.0, "##ffff");
                put(8.0, "#ffbf00");
                put(9.0, "#ff8000");
                put(10.0, "#ff4000");

                put(11.0, "#ff0000");
                put(12.0, "#ff0000");
                put(13.0, "#ff0000");
                put(14.0, "#ff0000");
                put(15.0, "#ff0000");
                put(16.0, "#ff0000");
                put(17.0, "#ff0000");
                put(18.0, "#ff0000");
                put(19.0, "#ff0000");
                put(20.0, "#ff0000");

            }};



    public static int getPaceColor(double pace){
        if(pace >= 1.0 && pace <= 21.0) {
            return Color.parseColor(paceColors.get(Math.floor(pace)));
        }
        return Color.RED;
    }
}