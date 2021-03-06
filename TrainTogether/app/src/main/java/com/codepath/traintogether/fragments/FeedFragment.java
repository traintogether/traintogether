package com.codepath.traintogether.fragments;

import com.google.gson.Gson;

import com.codepath.traintogether.R;
import com.codepath.traintogether.activities.EventDetailActivity;
import com.codepath.traintogether.adapters.EventsAdapter;
import com.codepath.traintogether.models.active.Result;
import com.codepath.traintogether.utils.ItemClickSupport;
import com.codepath.traintogether.utils.ItemSpaceDecoration;
import com.codepath.traintogether.utils.Utils;
import com.codepath.traintogether.utils.client.ActiveClient;
import com.codepath.traintogether.utils.client.ActiveRequest;
import com.codepath.traintogether.utils.client.ActiveResponse;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

/**
 * Created by ameyapandilwar on 8/18/16 at 1:03 AM.
 */
public class FeedFragment extends BaseFragment {

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;

    ArrayList<Result> events;
    EventsAdapter adapter;

    Gson gson;

    String query = "running";

    public void setQuery(String query) {
        this.query = query;
    }

    ActiveClient client;

    private Unbinder unbinder;

    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        unbinder = ButterKnife.bind(this, v);

        setupViews(getContext());

        gson = Utils.getGsonInstance();
        client = new ActiveClient(getContext());

        preferences = getContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        loadEvents();

        return v;
    }

    private void setupViews(Context context) {
        events = new ArrayList<>();
        adapter = new EventsAdapter(context, events);
        rvEvents.setAdapter(adapter);

        rvEvents.setHasFixedSize(true);
        rvEvents.addItemDecoration(new ItemSpaceDecoration(10));
        rvEvents.setLayoutManager(new LinearLayoutManager(context));

        ItemClickSupport.addTo(rvEvents).setOnItemClickListener((recyclerView, position, v) -> {
            Result event = events.get(position);
            Intent intent = new Intent(getContext(), EventDetailActivity.class);
            intent.putExtra("eventId", event.getAssetGuid());
            intent.putExtra("eventName", event.getAssetName());
            intent.putExtra("eventLogoUrlAdr", event.getLogoUrlAdr());
            try {
                intent.putExtra("cityName", event.getPlace().getCityName());
            } catch (Exception e) {

            }
            startActivity(intent);
        });
    }

    private void loadEvents() {
        ActiveRequest request = new ActiveRequest(
                query,
                preferences.getString("lat_lon", ""),
                preferences.getString("radius", ""),
                preferences.getString("city", ""),
                preferences.getString("state", ""),
                preferences.getString("zip", ""),
                preferences.getString("country", ""),
                preferences.getString("start_date", "")
        );

        client.loadEvents(request, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                loadResponseIntoFeed(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getContext(), "Problem Occurred. Try Later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void refreshEvents() {
        events.clear();
        loadEvents();
    }

    private void loadResponseIntoFeed(String jsonResponse) {
        try {
            ActiveResponse response = gson.fromJson(jsonResponse, ActiveResponse.class);
            events.addAll(response.getResults());
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
