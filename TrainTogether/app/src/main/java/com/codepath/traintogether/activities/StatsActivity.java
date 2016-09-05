package com.codepath.traintogether.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.CustomStatsPagerAdapter;
import com.codepath.traintogether.models.GroupStat;
import com.codepath.traintogether.models.Run;
import com.codepath.traintogether.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {
    private static final String TAG = "StatsActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabaseReference;

    @BindView(R.id.vpGroupStatsPager)
    ViewPager vpGroupStatsPager;
    @BindView(R.id.vpUserStatsPager)
    ViewPager vpUserStatsPager;
    @BindView(R.id.iGroupStat)
    InkPageIndicator inkPageIndicatorGroup;
    @BindView(R.id.iUserStat)
    InkPageIndicator inkPageIndicatorUser;
    @BindView(R.id.fabStartRun)
    FloatingActionButton fabStartRun;
    GroupStat groupStat;

    Context mContext;

    CustomStatsPagerAdapter vpGroupStatsPagerAdapter, vpUserStatsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mContext = this;

        //setup group charts view pager
        vpGroupStatsPagerAdapter = new CustomStatsPagerAdapter(this, Constants.VIEW_PAGER_GROUP_STAT);
        vpGroupStatsPager.setAdapter(vpGroupStatsPagerAdapter);
        inkPageIndicatorGroup.setViewPager(vpGroupStatsPager);

        //setup user charts view pager
        vpUserStatsPagerAdapter = new CustomStatsPagerAdapter(this, Constants.VIEW_PAGER_USER_STAT);
        vpUserStatsPager.setAdapter(vpUserStatsPagerAdapter);
        inkPageIndicatorUser.setViewPager(vpUserStatsPager);

        //setup fab
        fabStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && requestCode == 1) {
                Run run = Parcels.unwrap(data.getParcelableExtra("run"));
                if (run != null) {

//                    setUpStats();
                    //first run in thr group
                    if (groupStat == null) {
                        groupStat = new GroupStat();
                    }

                    //include current run in the stats
                    groupStat.addRun(run);
                    mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child("KQe8VxYSJ19IAaCcAJ0").child(Constants.GROUP_STAT_CHILD).setValue(groupStat);
//                    setUpStats();
                }
            } else if (resultCode == Constants.RESULT_NO_USER) {
                Log.e("GoogleFit", "Current user in not logged in!");
            }

        }
    }

}


