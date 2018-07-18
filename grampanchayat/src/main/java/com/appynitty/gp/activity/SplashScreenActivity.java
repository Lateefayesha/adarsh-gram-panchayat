package com.appynitty.gp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 5/2/18.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        AUtils.changeLanguage(this, Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID)));
//        QuickUtils.prefs.save(AUtils.LOCATION, "21.1394374,79.0595457");
        lodeHomeScreen();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void lodeHomeScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            }
        }, 4000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
