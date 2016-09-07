package com.codepath.traintogether.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.traintogether.R;
import com.codepath.traintogether.activities.TrackActivity;
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
import butterknife.Unbinder;

public class StatsFragment  extends BaseFragment {
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

    private Unbinder unbinder;

    Context mContext;

    CustomStatsPagerAdapter vpGroupStatsPagerAdapter, vpUserStatsPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_stats, container, false);

        unbinder = ButterKnife.bind(this, v);

        setupViews(getContext());

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        return v;

    }

    private void setupViews(Context context) {
        //setup group charts view pager
        vpGroupStatsPagerAdapter = new CustomStatsPagerAdapter(getContext(), Constants.VIEW_PAGER_GROUP_STAT);
        vpGroupStatsPager.setAdapter(vpGroupStatsPagerAdapter);
        inkPageIndicatorGroup.setViewPager(vpGroupStatsPager);

        //setup user charts view pager
        vpUserStatsPagerAdapter = new CustomStatsPagerAdapter(getContext(), Constants.VIEW_PAGER_USER_STAT);
        vpUserStatsPager.setAdapter(vpUserStatsPagerAdapter);
        inkPageIndicatorUser.setViewPager(vpUserStatsPager);

        //setup fab
        fabStartRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrackActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == getActivity().RESULT_OK && requestCode == 1) {
                Run run = Parcels.unwrap(data.getParcelableExtra("run"));
                if (run != null && run.getDistance() != 0 && run.getCalories() != 0) {

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


