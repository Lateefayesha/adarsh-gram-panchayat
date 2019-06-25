package com.appynitty.ghantagaditracker.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.appynitty.ghantagaditracker.BuildConfig;
import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;

import quickutils.core.QuickUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        QuickUtils.prefs.save(AUtils.APP_ID, "1");
        QuickUtils.prefs.save(AUtils.APP_ID_GG, "1");

        QuickUtils.prefs.save(AUtils.LOCATION, "20.709423,80.469527");
        QuickUtils.prefs.save(AUtils.VERSION_CODE, "5");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        versionCheck();
    }

    private void loadHomeScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(QuickUtils.prefs.getBoolean(AUtils.PREFS.IS_USER_LOGIN, true))
                    startActivity(new Intent(SplashScreenActivity.this, TrackerActivity.class));
                else
                    startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));

            }
        }, 4000);
    }

    private void versionCheck(){
        new MyAsyncTask(SplashScreenActivity.this, false, new MyAsyncTask.AsynTaskListener() {
            Boolean doUpdate = false;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                doUpdate = syncServer.checkVersionUpdate();
            }

            @Override
            public void onFinished() {
                if(doUpdate){
                    AUtils.showConfirmationDialog(SplashScreenActivity.this, AUtils.VERSION_CODE, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            AUtils.rateApp(SplashScreenActivity.this);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            finish();
                        }
                    });
                }else{
                    loadHomeScreen();
                }
            }
        }).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
