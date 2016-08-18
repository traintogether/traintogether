package com.codepath.traintogether.utils;

import android.content.Context;

import com.codepath.traintogether.Constants;
import com.codepath.traintogether.R;
import com.codepath.traintogether.models.active.ActiveRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by ameyapandilwar on 8/17/16 at 7:04 PM.
 */
public class ActiveClient {

    Context ctx;
    AsyncHttpClient client;

    public ActiveClient(Context context) {
        ctx = context;
        client = new AsyncHttpClient();
    }

    public void loadEvents(ActiveRequest request, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("api_key", ctx.getResources().getString(R.string.api_key));
        params.put("query", request.getQuery());

        client.get(Constants.API_ENDPOINT_URL, params, handler);
    }

}