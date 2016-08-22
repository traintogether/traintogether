package com.codepath.traintogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.GroupsAdapter;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity {

    private static final String TAG = "EventDetailActivity";
    @BindView(R.id.tvEventName)
    TextView tvEventName;

    @BindView(R.id.lvGroups)
    ListView lvGroups;

    ArrayList<Group> groups;
    private DatabaseReference mFirebaseDatabaseReference;

    ArrayAdapter groupsAdapter;

//    private List<Group> mGroups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String eventId = intent.getStringExtra("eventId");
        tvEventName.setText(eventName);

        groups = new ArrayList<>();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        System.out.println(eventId);
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

                //groupsAdapter.notifyDataSetChanged();
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

//        groupsAdapter = new GroupsAdapter(this, mFirebaseDatabaseReference);


        lvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String group = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, group);
            }
        });
    }
}
