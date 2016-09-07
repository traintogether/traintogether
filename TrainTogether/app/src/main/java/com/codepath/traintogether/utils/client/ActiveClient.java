package com.codepath.traintogether.utils.client;

import com.codepath.traintogether.R;
import com.codepath.traintogether.utils.Constants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.text.TextUtils;

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
        if (!TextUtils.isEmpty(request.getLatLon())) {
            params.put("lat_lon", request.getLatLon());
        }
        if (!TextUtils.isEmpty(request.getRadius())) {
            params.put("radius", request.getRadius());
        }
        if (!TextUtils.isEmpty(request.getCity())) {
            params.put("city", request.getCity());
        }
        if (!TextUtils.isEmpty(request.getState())) {
            params.put("state", request.getState());
        }
        if (!TextUtils.isEmpty(request.getZip())) {
            params.put("zip", request.getZip());
        }
        if (!TextUtils.isEmpty(request.getCountry())) {
            params.put("country", request.getCountry());
        }
        if (!TextUtils.isEmpty(request.getStartDate())) {
            params.put("start_date", request.getStartDate());
        }
        client.get(Constants.API_ENDPOINT_URL, params, handler);
    }

}