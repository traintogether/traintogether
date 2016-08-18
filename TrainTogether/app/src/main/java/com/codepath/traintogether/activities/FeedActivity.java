package com.codepath.traintogether.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.EventsAdapter;
import com.codepath.traintogether.models.active.ActiveRequest;
import com.codepath.traintogether.models.active.ActiveResponse;
import com.codepath.traintogether.models.active.Result;
import com.codepath.traintogether.utils.ActiveClient;
import com.codepath.traintogether.utils.ItemSpaceDecoration;
import com.codepath.traintogether.utils.Utils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.rvEvents)
    RecyclerView rvEvents;

    ArrayList<Result> events;
    EventsAdapter adapter;

    Gson gson;

    String query = "running";

    ActiveClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);

        setupViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());


        gson = Utils.getGsonInstance();
        client = new ActiveClient(this);

        loadEvents();
    }

    private void setupViews() {
        events = new ArrayList<>();
        adapter = new EventsAdapter(this, events);
        rvEvents.setAdapter(adapter);

        rvEvents.setHasFixedSize(true);
        rvEvents.addItemDecoration(new ItemSpaceDecoration(5));
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadEvents() {
        ActiveRequest request = new ActiveRequest();
        request.setQuery(query);

        client.loadEvents(request, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                loadResponseIntoFeed(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "Problem Occurred. Try Later!", Toast.LENGTH_SHORT).show();
            }
        });

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
