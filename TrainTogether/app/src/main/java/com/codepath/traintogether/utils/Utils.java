package com.codepath.traintogether.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.format.DateUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.MetricAffectingSpan;

import com.codepath.traintogether.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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

    public static final HashMap<Double, String> paceColors =
            new HashMap<Double, String>() {{
                put(0.0, "#00ff80");
                put(1.0, "#00ff80");
                put(2.0, "#00ff40");
                put(3.0, "#00ff00");

                put(4.0, "#40ff00");
                put(5.0, "#80ff00");
                put(6.0, "#bfff00");

                put(7.0, "#ffff00");
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


    public static int getPaceColor(double pace) {
        if (pace >= 1.0 && pace <= 21.0) {
            return Color.parseColor(paceColors.get(Math.floor(pace)));
        }
        return Color.RED;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        DateTime dateTime = new DateTime(dateMillis);
        DateTime now = DateTime.now();

        Period period = new Period(now, dateTime, PeriodType.dayTime());

        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendDays().appendSuffix(" day", " days")
                .toFormatter();

        String result = formatter.print(period);

        return result.contains("-") ? String.format("%s ago", result.replace("-", "")) : String.format("in %s", result);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static SpannableStringBuilder getCustomTextForTitle(Context context, String str, int size) {

        // Use a SpannableStringBuilder so that both the text and the spans are mutable
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);


        AbsoluteSizeSpan absoluteSizeSpan40 = new AbsoluteSizeSpan(size);
        // Apply the typeface span
        ssb.setSpan(
                absoluteSizeSpan40,            // the span to add
                0,                                 // the start of the span (inclusive)
                ssb.length(),                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder


        Typeface mediumTypeface = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
        CustomTypefaceSpan ctf = new CustomTypefaceSpan(mediumTypeface);

        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later

        // Apply the typeface span
        ssb.setSpan(
                ctf,            // the span to add
                0,                                 // the start of the span (inclusive)
                ssb.length(),                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder


        // Create a span that will make the text red
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.themeColor1));

        // Apply the color span
        ssb.setSpan(
                foregroundColorSpan,            // the span to add
                0,                                 // the start of the span (inclusive)
                2,                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder

        // Create a span that will make the text red
        foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.themeColor2));

        // Apply the color span
        ssb.setSpan(
                foregroundColorSpan,            // the span to add
                2,                                 // the start of the span (inclusive)
                4,                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder


        // Create a span that will make the text red
        foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.themeColor3));

        // Apply the color span
        ssb.setSpan(
                foregroundColorSpan,            // the span to add
                4,                                 // the start of the span (inclusive)
                6,                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder

        // Create a span that will make the text red
        foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.themeColor4));

        // Apply the color span
        ssb.setSpan(
                foregroundColorSpan,            // the span to add
                6,                                 // the start of the span (inclusive)
                8,                      // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder



        return ssb;
    }


    public static class CustomTypefaceSpan extends MetricAffectingSpan {
        private final Typeface typeface;

        public CustomTypefaceSpan(final Typeface typeface) {
            this.typeface = typeface;
        }

        @Override
        public void updateDrawState(final TextPaint drawState) {
            apply(drawState);
        }

        @Override
        public void updateMeasureState(final TextPaint paint) {
            apply(paint);
        }

        private void apply(final Paint paint) {
            final Typeface oldTypeface = paint.getTypeface();
            final int oldStyle = oldTypeface != null ? oldTypeface.getStyle() : 0;
            final int fakeStyle = oldStyle & ~typeface.getStyle();

            if ((fakeStyle & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fakeStyle & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(typeface);
        }
    }

}