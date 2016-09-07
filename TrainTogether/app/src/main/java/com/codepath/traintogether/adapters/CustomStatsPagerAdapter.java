package com.codepath.traintogether.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.traintogether.R;
import com.codepath.traintogether.utils.Constants;
import com.gigamole.library.ArcProgressStackView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by santoshag on 9/3/16.
 */
public class CustomStatsPagerAdapter extends PagerAdapter {


    private static final String TAG = "CustomStatsPagerAdapter";
    String[] desc;
    int[] image;
    public final static int MODEL_COUNT = 3;
    Context mContext;


    // Parsed colors
    private int[] mStartColors = new int[MODEL_COUNT];
    private int[] mEndColors = new int[MODEL_COUNT];
    private int[] groupStatCharts = new int[]{ Constants.CHART_APSV,  Constants.CHART_APSV,  Constants.CHART_APSV};
    private int[] userStatsCharts = new int[]{ Constants.CHART_LINE,  Constants.CHART_LINE,  Constants.CHART_LINE};

    ArcProgressStackView mArcProgressStackView;

    CustomStatsPagerAdapter customStatsPagerAdapter;
    int viewPagerType;
    LayoutInflater inflater;

    public CustomStatsPagerAdapter(Context context, int viewPagerType) {

        super();
        mContext = context;
        this.viewPagerType = viewPagerType;

    }

    @SuppressLint("NewApi")
    @Override
    public void finishUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
        super.finishUpdate(container);

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View collection, Object object) {

        return collection == ((View) object);
    }

    @Override
    public Object instantiateItem(View collection, int position) {

        // Inflating layout service
        inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (viewPagerType) {
            case Constants.VIEW_PAGER_GROUP_STAT:
                return setupGroupStats(collection, position);
            case Constants.VIEW_PAGER_USER_STAT:
                return setupUserStats(collection, position);
        }

        return null;
    }


    private Object setupUserStats(View collection, int position) {

        Log.i(TAG, "position: " + position + " type: " + viewPagerType);
        switch (userStatsCharts[position]) {
            case Constants.CHART_LINE:
                return chartLine(collection);


        }

        return null;
    }

    private Object setupGroupStats(View collection, int position) {

        Log.i(TAG, "position: " + position + " type: " + viewPagerType);
        switch (groupStatCharts[position]) {
            case Constants.CHART_APSV:
                return chartAPSV(collection);


        }

        return null;

    }

    private Object chartLine(View collection) {
        // Setting view you want to display as a row element
        View view = inflater.inflate(R.layout.pager_stat_line_chart_item, null);
        setupUserStats(view);
        setupShareFAB(view);

        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    private Object chartAPSV(View collection){

        // Setting view you want to display as a row element
        View view = inflater.inflate(R.layout.pager_stat_apsv_item, null);


        setUpAPSVStats(view);
        setupShareFAB(view);

        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);

    }

    private void setUpAPSVStats(View view) {


        // Get APSV
        mArcProgressStackView = (ArcProgressStackView) view.findViewById(R.id.apsv);
        // Get colors
        final String[] startColors = mContext.getResources().getStringArray(R.array.colorful_waves);
        final String[] endColors = mContext.getResources().getStringArray(R.array.default_preview);
        final String[] bgColors = mContext.getResources().getStringArray(R.array.medical_express);

        // Parse colors
        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }

        // Set models
        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("", 30, Color.parseColor(bgColors[0]), mStartColors[0]));
        models.add(new ArcProgressStackView.Model("", 50, Color.parseColor(bgColors[1]), mStartColors[1]));
        models.add(new ArcProgressStackView.Model("", 60, Color.parseColor(bgColors[2]), mStartColors[2]));
        mArcProgressStackView.setModels(models);

        setmArcProgressStackView(Constants.APSV_SIZE, 800);
        setmArcProgressStackView(Constants.APSV_SHADOW_DISTANCE, 25);
        setmArcProgressStackView(Constants.APSV_DRAW_WIDTH, 50);
        setmArcProgressStackView(Constants.APSV_MODEL_OFFSET, 20);
        setmArcProgressStackView(Constants.APSV_START_ANGLE, 90);
        setmArcProgressStackView(Constants.APSV_SWEEP_ANGLE, 500);
        setmArcProgressStackView(Constants.APSV_ANIMATION_DURATION, 750);
        mArcProgressStackView.setTextColor(mContext.getResources().getColor(R.color.transparentColor));

        mArcProgressStackView.setVisibility(View.VISIBLE);

        // Start apsv animation on start
//        mArcProgressStackView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int i = 40;
//                for (ArcProgressStackView.Model model : mArcProgressStackView.getModels()) {
//                    model.setProgress(i + 10);
//                    i += 15;
//                }
//                mArcProgressStackView.animateProgress();
//                mArcProgressStackView.setVisibility(View.VISIBLE);
//            }
//        }, 200);

        View vDotItem1;
        View vDotItem2;
        View vDotItem3;
        vDotItem1 = (View) view.findViewById(R.id.vDotItem1);
        vDotItem2 = (View) view.findViewById(R.id.vDotItem2);
        vDotItem3 = (View) view.findViewById(R.id.vDotItem3);


        GradientDrawable bgShape = (GradientDrawable) vDotItem1.getBackground();
        bgShape.setColor(mStartColors[0]);
        bgShape = (GradientDrawable) vDotItem2.getBackground();
        bgShape.setColor(mStartColors[1]);
        bgShape = (GradientDrawable) vDotItem3.getBackground();
        bgShape.setColor(mStartColors[2]);


//        //TODO: replace hardcoded group key ""-KQe8VxYSJ19IAaCcAJ0" with user's only group
//        Query qry = mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child("-KQe8VxYSJ19IAaCcAJ0").child(Constants.GROUP_STAT_CHILD);
//
//        qry.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i(TAG, "called at: " + System.currentTimeMillis());
//                groupStat = dataSnapshot.getValue(GroupStat.class);
//                if(groupStat != null) {
//                    //No recorded runs yet
//                    Log.i(TAG, "groustat;: " + groupStat);
//                    tvStats.setText("Group statistics: \ncalories: " + groupStat.getCalories() + " \ndistance: " + groupStat.getDistance());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });


    }

    private void setupUserStats(View view) {


        // Get colors
        final String[] startColors = mContext.getResources().getStringArray(R.array.colorful_waves);
        final String[] endColors = mContext.getResources().getStringArray(R.array.default_preview);
        final String[] bgColors = mContext.getResources().getStringArray(R.array.medical_express);

        // Parse colors
        for (int i = 0; i < MODEL_COUNT; i++) {
            mStartColors[i] = Color.parseColor(startColors[i]);
            mEndColors[i] = Color.parseColor(endColors[i]);
        }

        LineChart lcUser = (LineChart) view.findViewById(R.id.lcUser);
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        LineDataSet set = setDataSet(10, 18, 14);
        set.setColor(Color.parseColor("#4885ed"));
        dataSets.add(set); // add the datasets

         set = setDataSet(14, 20, 21);
        set.setColor(Color.parseColor("#db3236"));
        dataSets.add(set); // add the datasets

         set = setDataSet(29, 26, 20);
        set.setColor(Color.parseColor("#f4c20d"));
        dataSets.add(set); // add the datasets

        set = setDataSet(23, 31, 24);
        set.setColor(Color.parseColor("#3cba54"));
        dataSets.add(set); // add the datasets

//        for (int i = 0; i < 3; i++) {
//            LineDataSet set = setDataSet(10, 20, 30);
//            set.setColor(mStartColors[i]);
//            dataSets.add(set); // add the datasets
//        }
        // create a data object with the datasets
        LineData data = new LineData(dataSets);
        // set data
        lcUser.setData(data);
        lcUser.getXAxis().setEnabled(false);
        lcUser.getAxisLeft().setEnabled(false);
        lcUser.getAxisRight().setEnabled(false);
        lcUser.setDescription("");
        lcUser.setTouchEnabled(false);
        lcUser.setVisibility(View.INVISIBLE);
        // Start apsv animation on start
        lcUser.postDelayed(new Runnable() {
            @Override
            public void run() {
                lcUser.animateY(1250); // animate vertical 3000 milliseconds
                lcUser.setVisibility(View.VISIBLE);

            }
        }, 950);

    }

    private LineDataSet setDataSet(int i1, int i2, int i3){
        ArrayList<Entry> values = new ArrayList<Entry>();

//        float val = (float) (i1 * 20) + 3;
//        values.add(new Entry(0, val));
//
//         val = (float) (i2 * 20) + 3;
//        values.add(new Entry(0, val));
//         val = (float) (i3 * 20) + 3;
//        values.add(new Entry(0, val));

//        for (int i = 0; i < 3; i++) {
//            float val = (float) (Math.random() * 20) + 3;
//            Log.i("SANTOSH", "i: "+i + " val:"+ val);
//            values.add(new Entry(i, val));
//        }

        values.add(new Entry(0, i1));
        values.add(new Entry(1, i2));
        values.add(new Entry(2, i3));


        LineDataSet set1;

        set1 = new LineDataSet(values, "DataSet 1");

        set1.setMode(set1.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                ? LineDataSet.Mode.LINEAR
                :  LineDataSet.Mode.HORIZONTAL_BEZIER);
        set1.setColor(mStartColors[0]);
        set1.setLineWidth(3);
        set1.setCircleColor(mContext.getResources().getColor(R.color.transparentColor));
        set1.setDrawValues(false);
        return set1;


    }

//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK && requestCode == 1) {
//                Run run = Parcels.unwrap(data.getParcelableExtra("run"));
//                if(run != null) {
//
//                    setUpStats();
//                    //first run in thr group
//                    if(groupStat == null){
//                        groupStat = new GroupStat();
//                    }
//
//                    //include current run in the stats
//                    groupStat.addRun(run);
//                    mFirebaseDatabaseReference.child(Constants.GROUPS_CHILD).child("-KQe8VxYSJ19IAaCcAJ0").child(Constants.GROUP_STAT_CHILD).setValue(groupStat);
////                    setUpStats();
//                }
//            } else if (resultCode == Constants.RESULT_NO_USER) {
//                Log.e("GoogleFit", "Current user in not logged in!");
//            }
//
//        }
//    }

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

    public void setupShareFAB(View view) {
        FabSpeedDial fabSpeedDial = (FabSpeedDial) view.findViewById(R.id.fabShare);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return true;
            }
        });
    }
}


