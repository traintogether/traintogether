package com.codepath.traintogether.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ameyapandilwar on 8/18/16 at 2:43 AM.
 */
public class SpinnerAdapter<String> extends ArrayAdapter<String> {
    Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Fanwood.otf");

    public SpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
//        view.setTypeface(typeface);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
//        view.setTypeface(typeface);
        return view;
    }
}