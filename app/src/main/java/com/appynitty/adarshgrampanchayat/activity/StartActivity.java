package com.appynitty.adarshgrampanchayat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import com.appynitty.adarshgrampanchayat.R;
import com.appynitty.ghantagaditracker.activity.SplashScreenActivity;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        Prefs.putString(AUtils.LANGUAGE_NAME, AUtils.LanguageConstants.MARATHI);

        Prefs.putString(AUtils.APP_ID, "1");
        Prefs.putString(AUtils.APP_ID_GG, "1");

        Prefs.putString(AUtils.LOCATION, "20.709423,80.469527");
//        Prefs.putString(AUtils.LOCATION, "20.386781,78.12306");
        Prefs.putInt(AUtils.VERSION_CODE, 20);

        Prefs.putBoolean(AUtils.PREFS.SKIP_REGISTRATION_PERMANENT, false);

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