package com.appynitty.gp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.appynitty.gp.R;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.SaveFcmIdAsyncTask;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

/**
 * Created by MiTHUN on 5/2/18.
 */

public class SmartSplashScreenActivity extends AppCompatActivity {

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

        AUtils.changeLanguage(this, Prefs.getString(AUtils.LANGUAGE_ID, AUtils.LanguageConstants.ENGLISH));

//        loadHomeScreen();

        Prefs.getString(AUtils.LOCATION, "21.139452,79.059643");

        Prefs.putString(AUtils.USER_ID, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

        versionCheck();

        new SaveFcmIdAsyncTask(this).execute();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    private void loadHomeScreen() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SmartSplashScreenActivity.this, SelectPlaceActivity.class));
                finish();
            }
        }, 4000);
    }

    private void versionCheck(){
        new MyAsyncTask(SmartSplashScreenActivity.this, false, new MyAsyncTask.AsynTaskListener() {
            Boolean doUpdate = false;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                doUpdate = syncServer.checkVersionUpdate();
            }

            @Override
            public void onFinished() {
                if(doUpdate){
                    AUtils.showConfirmationDialog(SmartSplashScreenActivity.this, AUtils.VERSION_CODE, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            AUtils.rateApp(SmartSplashScreenActivity.this);
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
