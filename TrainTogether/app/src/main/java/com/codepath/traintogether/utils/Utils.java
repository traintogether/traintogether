package com.codepath.traintogether.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    public static String getDaysToEvent(String rawJsonDate) {
        String eventDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(eventDateFormat, Locale.ENGLISH);
        sf.setLenient(true);

        long dateMillis = 0;

        try {
            dateMillis = sf.parse(rawJsonDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime dateTime = new DateTime(dateMillis);
        DateTime now = DateTime.now();

        Period period = new Period(now, dateTime, PeriodType.dayTime());

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays().appendSuffix(" day ", " days ")
                .toFormatter();

        return formatter.print(period);
    }

    public static String getEventDate(String rawJsonDate) {
        String eventDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(eventDateFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(
                    dateMillis,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
            ).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}