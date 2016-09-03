package com.codepath.traintogether.activities;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.RequestsAdapter;
import com.codepath.traintogether.models.Request;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestActivity extends BaseActivity {

    private static final String TAG = "RequestActivity";

    @BindView(R.id.lvRequests)
    ListView lvRequests;

    List<Request> requests;
    private FirebaseUser loggedUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabaseReference;

    ArrayAdapter requestsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);

        requests = new ArrayList<>();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        loggedUser = mAuth.getCurrentUser();

        Query qry = mFirebaseDatabaseReference.child("requests").orderByChild("toId").equalTo(loggedUser.getUid());

        requestsAdapter = new RequestsAdapter(this, requests);
        lvRequests.setAdapter(requestsAdapter);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Request request = dataSnapshot.getValue(Request.class);

                if (!request.getStatus()) {
                    requestsAdapter.add(request);
                }

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


        lvRequests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String group = (String) adapterView.getItemAtPosition(i);
            }
        });
    }
}
