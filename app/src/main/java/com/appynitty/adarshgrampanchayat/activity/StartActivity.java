package com.appynitty.adarshgrampanchayat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.appynitty.adarshgrampanchayat.R;
import com.appynitty.ghantagaditracker.activity.SplashScreenActivity;
import com.appynitty.ghantagaditracker.activity.TrackerActivity;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        startActivity(new Intent(StartActivity.this, SmartSplashScreenActivity.class));
        startActivity(new Intent(StartActivity.this, SplashScreenActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}