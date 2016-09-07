package com.codepath.traintogether.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.traintogether.R;
import com.codepath.traintogether.TrainTogetherApplication;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.Request;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.utils.Constants;
import com.codepath.traintogether.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RequestsAdapter extends ArrayAdapter<Request> {

    private static final String TAG = "RequestsAdapter";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser loggedUser;
    private FirebaseAuth mAuth;
    String[] names = new String[]{"Carley", "Adrien", "Adisson","Adlai","Ado","Adonia","Adria","Adrian","Adrie","Adrien","Adya","Aeriel"};

    public RequestsAdapter(Context context, List<Request> requests) {
        super(context, 0, requests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Request request = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_request, parent, false);
        }
        // Lookup view for data population
        TextView tvFromRequestId = (TextView) convertView.findViewById(R.id.tvFromRequestId);
        Button btnJoin = (Button) convertView.findViewById(R.id.btnJoin);

        StringBuilder sb = new StringBuilder();
        mAuth = FirebaseAuth.getInstance();
        loggedUser = mAuth.getCurrentUser();

        tvFromRequestId.setText(names[Utils.randomWithRange(0, names.length-1)] + " is requesting to join.");

        // Populate the data into the template view using the data object

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setStatus(true);
                mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                mFirebaseDatabaseReference.child(Constants.REQUESTS_CHILD).child(request.getKey()).setValue(request);

                isLastPendingRequest(request.getFromId(), request.getGroupKey());

                btnJoin.setEnabled(false);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void getUser(String fromId, String groupKey) {
        Query qry = mFirebaseDatabaseReference.child(Constants.USERS_CHILD).orderByChild("uid").equalTo(fromId);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                User user = dataSnapshot.getValue(User.class);
                addUserToGroup(user, groupKey);

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
    }

    private void addUserToGroup(User user, String groupKey) {
        Query qry = mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).orderByChild("key").equalTo(groupKey);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Group group = dataSnapshot.getValue(Group.class);
                user.groups.add(group.key);

                group.users.add(user);
                TrainTogetherApplication.setCurrentUser(user);
                mFirebaseDatabaseReference.child(Constants.USERS_CHILD).child(user.getUid()).setValue(user);

                mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child(groupKey).setValue(group);

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
    }

    private void isLastPendingRequest(String fromId, String groupKey) {
        Query qry = mFirebaseDatabaseReference.child(Constants.REQUESTS_CHILD).orderByChild("fromId").equalTo(fromId);

        qry.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean anyPendingReq = true;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                    Request request = postSnapshot.getValue(Request.class);

                    if (!request.getStatus()) {
                        anyPendingReq = true;
                        break;
                    }
                    anyPendingReq = false;

                }
                if(!anyPendingReq) {
                    getUser(fromId, groupKey);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

}