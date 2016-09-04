package com.codepath.traintogether.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.codepath.traintogether.R;
import com.codepath.traintogether.models.GroupStat;
import com.codepath.traintogether.models.Run;
import com.codepath.traintogether.utils.Constants;
import com.gigamole.library.ArcProgressStackView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {
    private static final String TAG = "StatsActivity";
    private FirebaseAuth mAuth;
    private DatabaseReference mFirebaseDatabaseReference;

    @BindView(R.id.tvStats)
    TextView tvStats;
    @BindView(R.id.apsv)
    ArcProgressStackView mArcProgressStackView;
    @BindView(R.id.vDotItem1)
    View vDotItem1;
    @BindView(R.id.vDotItem2)
    View vDotItem2;
    @BindView(R.id.vDotItem3)
    View vDotItem3;
    @BindView(R.id.lcUser)
    LineChart lcUser;
    GroupStat groupStat;
    public final static int MODEL_COUNT = 3;

    // Parsed colors
    private int[] mStartColors = new int[MODEL_COUNT];
    private int[] mEndColors = new int[MODEL_COUNT];

    int mFullSize = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        setUpStats();

    }

    private void setUpStats() {

        // Get APSV
        mArcProgressStackView = (ArcProgressStackView) findViewById(R.id.apsv);
        // Get colors
        final String[] startColors = getResources().getStringArray(R.array.colorful_waves);
        final String[] endColors = getResources().getStringArray(R.array.default_preview);
        final String[] bgColors = getResources().getStringArray(R.array.medical_express);

        // Parse colors
        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }

        // Set models
        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("hello", 0, Color.parseColor(bgColors[0]), mStartColors[0]));
        models.add(new ArcProgressStackView.Model("hello", 0, Color.parseColor(bgColors[1]), mStartColors[1]));
        models.add(new ArcProgressStackView.Model("hello", 0, Color.parseColor(bgColors[2]), mStartColors[2]));
        mArcProgressStackView.setModels(models);

        setmArcProgressStackView(Constants.APSV_SIZE, 800);
        setmArcProgressStackView(Constants.APSV_SHADOW_DISTANCE, 25);
        setmArcProgressStackView(Constants.APSV_DRAW_WIDTH, 50);
        setmArcProgressStackView(Constants.APSV_MODEL_OFFSET, 20);
        setmArcProgressStackView(Constants.APSV_START_ANGLE, 90);
        setmArcProgressStackView(Constants.APSV_SWEEP_ANGLE, 500);
        setmArcProgressStackView(Constants.APSV_ANIMATION_DURATION, 750);
        mArcProgressStackView.setTextColor(getResources().getColor(R.color.transparentColor));



        // Start apsv animation on start
        mArcProgressStackView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int i = 40;
                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels()) {
                    model.setProgress(i + 10);
                    i += 15;
                }
                mArcProgressStackView.animateProgress();
            }
        }, 500);


        GradientDrawable bgShape = (GradientDrawable)vDotItem1.getBackground();
        bgShape.setColor(mStartColors[0]);
        bgShape = (GradientDrawable)vDotItem2.getBackground();
        bgShape.setColor(mStartColors[1]);
        bgShape = (GradientDrawable)vDotItem3.getBackground();
        bgShape.setColor(mStartColors[2]);

        setupUserStats();

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

    private void setupUserStats() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        for (int i = 0; i < 3; i++) {
            LineDataSet set = setDataSet();
            set.setColor(mStartColors[i]);
            dataSets.add(set); // add the datasets
        }
        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        // set data
        lcUser.setData(data);
        lcUser.getXAxis().setEnabled(false);
        lcUser.getAxisLeft().setEnabled(false);
        lcUser.getAxisRight().setEnabled(false);
        lcUser.setDescription("");
        lcUser.setTouchEnabled(false);

    }

    private LineDataSet setDataSet(){
        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < 3; i++) {
            float val = (float) (Math.random() * 20) + 3;
            values.add(new Entry(i, val));
        }
        LineDataSet set1;

        set1 = new LineDataSet(values, "DataSet 1");

        set1.setMode(set1.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                ? LineDataSet.Mode.LINEAR
                :  LineDataSet.Mode.HORIZONTAL_BEZIER);
        set1.setColor(mStartColors[0]);
        set1.setLineWidth(3);
        set1.setCircleColor(getResources().getColor(R.color.transparentColor));
        set1.setDrawValues(false);
        return set1;


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

    public void setmArcProgressStackView(final int view_property, final int value) {
        switch (view_property) {
            case Constants.APSV_SIZE:
                mArcProgressStackView.getLayoutParams().height = value;
                mArcProgressStackView.requestLayout();
                break;
            case Constants.APSV_SHADOW_DISTANCE:
                mArcProgressStackView.setShadowDistance(value);
                mArcProgressStackView.postInvalidate();
                break;
            case Constants.APSV_SHADOW_ANGLE:
                mArcProgressStackView.setShadowAngle(value);
                mArcProgressStackView.postInvalidate();
                break;
            case Constants.APSV_SHADOW_RADIUS:
                mArcProgressStackView.setShadowRadius(value);
                mArcProgressStackView.postInvalidate();
                break;
            case Constants.APSV_ANIMATION_DURATION:
                mArcProgressStackView.setAnimationDuration(value);
                break;
            case Constants.APSV_DRAW_WIDTH:
                mArcProgressStackView.setDrawWidthFraction((float) value / 100.0f);
                break;
            case Constants.APSV_MODEL_OFFSET:
                mArcProgressStackView.setProgressModelOffset(value - 50);
                break;
            case Constants.APSV_START_ANGLE:
                mArcProgressStackView.setStartAngle(value);
                break;
            case Constants.APSV_SWEEP_ANGLE:
                mArcProgressStackView.setSweepAngle(value);
                break;
            default:
                break;
        }
    }


}