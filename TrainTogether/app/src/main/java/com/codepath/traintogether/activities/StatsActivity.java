package com.codepath.traintogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codepath.traintogether.R;

import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

    }

    public void startActivityTracking(View view) {
        Intent intent = new Intent(this, TrackActivity.class);
        startActivity(intent);
    }

}