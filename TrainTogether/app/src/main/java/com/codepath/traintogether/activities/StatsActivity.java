package com.codepath.traintogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.codepath.traintogether.R;
import com.codepath.traintogether.models.GroupStat;
import com.codepath.traintogether.models.Run;
import com.codepath.traintogether.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {
    private static final String TAG = "StatsActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabaseReference;
    @BindView(R.id.tvStats)
    TextView tvStats;
    GroupStat groupStat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        setUpStats();

    }

    private void setUpStats() {
        //TODO: replace hardcoded group key ""-KQe8VxYSJ19IAaCcAJ0" with user's only group
        Query qry = mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child("-KQe8VxYSJ19IAaCcAJ0").child(Constants.GROUP_STAT_CHILD);

        qry.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "called at: " + System.currentTimeMillis());
                groupStat = dataSnapshot.getValue(GroupStat.class);
                if(groupStat != null) {
                    //No recorded runs yet
                    Log.i(TAG, "groustat;: " + groupStat);
                    tvStats.setText("Group statistics: \ncalories: " + groupStat.getCalories() + " \ndistance: " + groupStat.getDistance());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


    public void startActivityTracking(View view) {
        Intent intent = new Intent(this, TrackActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && requestCode == 1) {
                Run run = Parcels.unwrap(data.getParcelableExtra("run"));
                if(run != null) {

                    setUpStats();
                    //first run in thr group
                    if(groupStat == null){
                        groupStat = new GroupStat();
                    }

                    //include current run in the stats
                    groupStat.addRun(run);
                    mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child("-KQe8VxYSJ19IAaCcAJ0").child(Constants.GROUP_STAT_CHILD).setValue(groupStat);
//                    setUpStats();
                }
            } else if (resultCode == Constants.RESULT_NO_USER) {
                Log.e("GoogleFit", "Current user in not logged in!");
            }

        }
    }

    public void sendNotif(View view) {

    }

}