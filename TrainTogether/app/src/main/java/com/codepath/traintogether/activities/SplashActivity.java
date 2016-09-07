package com.codepath.traintogether.activities;

import com.codepath.traintogether.R;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ameyapandilwar on 8/30/16 at 12:51 AM.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_splash);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        };
        timer.start();
    }

}