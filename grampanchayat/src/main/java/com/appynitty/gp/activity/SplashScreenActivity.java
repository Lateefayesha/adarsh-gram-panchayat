package com.appynitty.gp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.appynitty.gp.R;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.LocaleHelper;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.SaveFcmIdAsyncTask;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 5/2/18.
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context base) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            super.attachBaseContext(LocaleHelper.onAttach(base));
        }else{
            super.attachBaseContext(base);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        AUtils.changeLanguage(this, Integer.parseInt(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, "1")));

        QuickUtils.prefs.save(AUtils.USER_ID, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        new SaveFcmIdAsyncTask(this).execute();

        versionCheck();
//        loadHomeScreen();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void loadHomeScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                finish();
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

}
