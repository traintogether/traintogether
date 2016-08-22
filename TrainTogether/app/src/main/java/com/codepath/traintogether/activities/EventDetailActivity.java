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
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity {

    private static final String TAG = "EventDetailActivity";
    @BindView(R.id.tvEventName)
    TextView tvEventName;

    @BindView(R.id.lvGroups)
    ListView lvGroups;

    List<String> groups;
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
        Query qry = mFirebaseDatabaseReference.child("groups");

//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println(dataSnapshot);
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Group group = snapshot.getValue(Group.class);
//                    groups.add(group.getEventId());
//                }
//                System.out.println(groups);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        mFirebaseDatabaseReference.addValueEventListener(postListener);

        groupsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, groups);
        lvGroups.setAdapter(groupsAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Group group = dataSnapshot.getValue(Group.class);
                System.out.println(group);
                // [START_EXCLUDE]
                // Update RecyclerView
//                mGroups.add(group);
                StringBuilder sb = new StringBuilder();
                if (group.getEventId().equalsIgnoreCase(eventId)) {
                    sb.append(group.getEventId()).append(" | ");
                    for (User user : group.getUsers()) {
                        sb.append(user.emailId);
                    }
                    groups.add(sb.toString());
                    groupsAdapter.notifyDataSetChanged();
                }
//                notifyItemInserted(mGroups.size() - 1);
                // [END_EXCLUDE]
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

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
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

            }
        });
    }
}
