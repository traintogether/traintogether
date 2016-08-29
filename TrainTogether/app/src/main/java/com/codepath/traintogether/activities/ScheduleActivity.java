package com.codepath.traintogether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.codepath.traintogether.R;
import com.codepath.traintogether.adapters.TrainingScheduleAdapter;
import com.codepath.traintogether.models.Days;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleActivity extends AppCompatActivity {

    private static final String TAG = ScheduleActivity.class.getSimpleName();

    TrainingScheduleAdapter trainingScheduleAdapter;
    List<Days> daysList = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.schedule_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        createData();
        initializeRecyclerView();
    }

    private void createData() {

        String dt = "09-07-2016";  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();
        Date date1= null;


        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        for(int i=0; i <= 21; i++) {
            dt = sdf.format(c.getTime());  // dt will be the new date after incrementing
            try {
                date1 = sdf.parse(dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DateFormat format2=new SimpleDateFormat("EEEE");
            String finalDay =format2.format(date1);

            daysList.add(i, new Days(finalDay, dt, "To-Do", i + " miles", "Workout " + i + " of 21"));
            c.add(Calendar.DATE, 1);  // number of days to add
        }
    }

    private void initializeRecyclerView() {

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        trainingScheduleAdapter =  new TrainingScheduleAdapter(this, daysList);
        recyclerView.setAdapter(trainingScheduleAdapter);
        recyclerView.setHasFixedSize(true);
    }

}
