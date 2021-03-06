package com.codepath.traintogether.activities;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.GroupsAdapter;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends BaseActivity {

    private static final String TAG = "EventDetailActivity";
//    @BindView(R.id.tvEventName)
//    TextView tvEventName;

    @BindView(R.id.lvGroups)
    ListView lvGroups;

    @BindView(R.id.ivBackdrop)
    ImageView ivBackdrop;

    ArrayList<Group> groups;
    private DatabaseReference mFirebaseDatabaseReference;

    ArrayAdapter groupsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String eventId = intent.getStringExtra("eventId");
        String eventLogoUrlAdr = intent.getStringExtra("eventLogoUrlAdr");
        String cityName = intent.getStringExtra("cityName");
//        tvEventName.setText(eventName);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(eventName);

        loadBackdrop(cityName);

        setTitle(eventName);

        groups = new ArrayList<>();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query qry = mFirebaseDatabaseReference.child("groups").orderByChild("eventId").equalTo(eventId);

        groupsAdapter = new GroupsAdapter(this, groups);
        lvGroups.setAdapter(groupsAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Group group = dataSnapshot.getValue(Group.class);
                Log.i(TAG, group.getKey() + " " + group.users.size());
                for(User u:group.users){
                    Log.i(TAG, u.getEmailId());

                }
                groupsAdapter.add(group);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        qry.addChildEventListener(childEventListener);

        lvGroups.setOnItemClickListener((adapterView, view, i, l) -> {
            String group = (String) adapterView.getItemAtPosition(i);
            Log.i(TAG, group);
        });
    }

    private void loadBackdrop(String cityName) {
        ivBackdrop.setImageResource(0);

        if (cityName.equalsIgnoreCase("boston")) {
            ivBackdrop.setImageResource(R.drawable.boston);
        } else if (cityName.equalsIgnoreCase("san francisco")) {
            ivBackdrop.setImageResource(R.drawable.san_francisco);
        } else if (cityName.equalsIgnoreCase("san diego")) {
            ivBackdrop.setImageResource(R.drawable.san_diego);
        } else {
            ivBackdrop.setImageResource(R.drawable.default_marathon);
        }
    }

}