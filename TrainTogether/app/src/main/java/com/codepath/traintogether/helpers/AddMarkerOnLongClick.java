package com.codepath.traintogether.helpers;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

public class AddMarkerOnLongClick implements OnMap.Listener {

    private final Context mContext;
    private final PlaceManager mPlaceManager;

    public AddMarkerOnLongClick(Context context, PlaceManager manager) {
        mContext = context;
        mPlaceManager = manager;
    }

    // TODO Set long click listener
    // Call showAlertDialog method.
    @Override
    public void onMap(final GoogleMap map) {
    }

}
