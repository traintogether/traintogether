package com.codepath.traintogether.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codepath.traintogether.R;
import com.plattysoft.leonids.ParticleSystem;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class FinisherActivity extends AppCompatActivity implements View.OnClickListener {

    Shimmer shimmer;
    ShimmerTextView tvCongratulations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finisher);
        tvCongratulations = (ShimmerTextView) findViewById(R.id.tvCongratulations);
        tvCongratulations.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        shimmer = new Shimmer();
        shimmer.setDuration(2500);
        shimmer.start(tvCongratulations);
        shimmer.start(tvCongratulations);
        tvCongratulations.performClick();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmer.cancel();
    }

    @Override
    public void onClick(View arg0) {
        new ParticleSystem(this, 80, R.drawable.confeti2, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.000017f, 90)
                .emit(findViewById(R.id.emiter_top_right), 10);

        new ParticleSystem(this, 80, R.drawable.confeti3, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.000017f, 90)
                .emit(findViewById(R.id.emiter_top_left), 10);

        new ParticleSystem(this, 80, R.drawable.star_pink, 10000)
                .setSpeedModuleAndAngleRange(0f, 0.1f, 0, 0)
                .setRotationSpeed(144)
                .setAcceleration(0.000017f, 90)
                .emit(findViewById(R.id.emiter_top_third), 10);
    }
}
