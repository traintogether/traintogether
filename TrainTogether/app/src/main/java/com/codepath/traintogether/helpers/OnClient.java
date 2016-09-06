package com.codepath.traintogether.helpers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.traintogether.activities.TrackActivity;
import com.google.android.gms.common.api.GoogleApiClient;

public class OnClient implements GoogleApiClient.ConnectionCallbacks {

    private final GoogleApiClient mGoogleApiClient;
    private final Listener[] mListeners;

    public OnClient(GoogleApiClient client, Listener... listeners) {
        mGoogleApiClient = client;
        mListeners = listeners;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TrackActivity.TAG, "Client connected!");
        for (Listener listener : mListeners) {
            listener.onClient(mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        for (Listener listener : mListeners) {
            listener.onClient(null);
        }
    }

    public interface Listener {
        void onClient(@Nullable GoogleApiClient client);
    }

}
