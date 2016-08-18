package com.codepath.traintogether.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ActiveClient client;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        unbinder = ButterKnife.bind(this, v);

        setupViews(getContext());

//        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());


        gson = Utils.getGsonInstance();
        client = new ActiveClient(getContext());

        loadEvents();

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed);
//        ButterKnife.bind(this);


    }

    private void setupViews(Context context) {
        events = new ArrayList<>();
        adapter = new EventsAdapter(context, events);
        rvEvents.setAdapter(adapter);

        rvEvents.setHasFixedSize(true);
        rvEvents.addItemDecoration(new ItemSpaceDecoration(5));
        rvEvents.setLayoutManager(new LinearLayoutManager(context));
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
                Toast.makeText(getContext(), "Problem Occurred. Try Later!", Toast.LENGTH_SHORT).show();
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
