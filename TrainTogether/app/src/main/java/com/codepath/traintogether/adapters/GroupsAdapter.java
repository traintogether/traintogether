package com.codepath.traintogether.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.traintogether.R;
import com.codepath.traintogether.models.Group;
import com.codepath.traintogether.models.Request;
import com.codepath.traintogether.models.User;
import com.codepath.traintogether.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GroupsAdapter extends ArrayAdapter<Group> {

    private static final String TAG = "GroupsAdapter";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseUser loggedUser;
    private FirebaseAuth mAuth;

    public GroupsAdapter(Context context, ArrayList<Group> groups) {
        super(context, 0, groups);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Group group = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, parent, false);
        }
        // Lookup view for data population
        TextView tvGroupDetails = (TextView) convertView.findViewById(R.id.tvGroupDetails);
        ImageView btnJoin = (ImageView) convertView.findViewById(R.id.btnJoin);

        StringBuilder sb = new StringBuilder();
        mAuth = FirebaseAuth.getInstance();
        loggedUser = mAuth.getCurrentUser();
        for(int i =0 ; i < group.users.size(); i++){
            User user = group.users.get(i);
            if(user.getDisplayName() == null){
                sb.append("Matt");
            }else {
                sb.append(user.getDisplayName());
            }
            //current user cannot join himslef to a group
            if(user.getEmailId() == loggedUser.getEmail()){
                btnJoin.setEnabled(false);
            }
            if(group.users.size() > 1 && i == 0){
                sb.append(" and ");
            }else if(group.users.size() > 1 && i == 1){
                sb.append(" are runnning together");
            }else if(group.users.size() == 1){
                sb.append(" is interested in this marathon.");
            }
        }

        tvGroupDetails.setText(sb);

        Log.i(TAG, "group key: " + group.getKey());

        // Populate the data into the template view using the data object

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();
                user.emailId = loggedUser.getEmail();
                user.displayName = loggedUser.getDisplayName();
                user.uid = loggedUser.getUid();

                mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                for (User u : group.getUsers()) {
                    sendNotificationToUser(u.getUid(), user.uid, group.getKey());
                }

//                group.users.add(user);

                Log.i(TAG, "group key: " + group.getKey());
//                mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child(group.getKey()).setValue(group);

                btnJoin.setEnabled(false);
//                btnJoin.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Request Sent.", Toast.LENGTH_SHORT).show();
                btnJoin.setImageResource(R.drawable.ic_add_to_photos_grey);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    private void sendNotificationToUser(String toUid, String fromUid, String groupKey) {
        Request request = new Request(toUid, fromUid, groupKey);
        String key = mFirebaseDatabaseReference.child(Constants.REQUESTS_CHILD).push().getKey();
        request.key = key;
        mFirebaseDatabaseReference.child(Constants.REQUESTS_CHILD).child(key).setValue(request);
    }
}