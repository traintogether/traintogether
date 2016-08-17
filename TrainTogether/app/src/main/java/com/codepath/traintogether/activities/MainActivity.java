package com.codepath.traintogether.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.traintogether.R;

/**
 * Created by ameyapandilwar on 8/17/16
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUsingFacebook();
    }

    private void loginUsingFacebook() {
        startActivity(new Intent(this, FacebookLoginActivity.class));
    }

}